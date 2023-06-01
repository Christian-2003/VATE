package frontend.dialogs;

import backend.config.Config;
import frontend.frames.main.MainFrame;
import frontend.frames.main.components.EditorTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Implements a dialog that allows the user search a regex within VATE.
 *
 * @author  Christian-2003
 * @version 29 May 2023
 */
public class SearchDialog extends JDialog {

    /**
     * MainFrame in which the dialog is located.
     */
    private MainFrame context;

    /**
     * Buttons for the dialog.
     */
    private JButton closeButton, searchButton, nextButton, previousButton, replaceButton, replaceAllButton, swapReplaceWithRegex;

    /**
     * Radio buttons for the dialog.
     */
    private JRadioButton searchFile, searchAllFiles;

    /**
     * Text inputs for the dialog.
     */
    private JTextField regexInput, replacementInput;

    /**
     * Labels for the dialog.
     */
    private JLabel numberOfMatches, activeMatch, noMatches, replacementHintLabel;

    /**
     * Checkboxes for the dialog.
     */
    private JCheckBox enableReplacementCheckbox;

    /**
     * JPanel contains the display of the number of matches.
     */
    private JPanel matchesContainer;

    /**
     * Stores the number of matches and the active match as integers.
     */
    private int numberOfMatchesInt, currentMatch, currentTab, currentPositionInCurrentTab;

    /**
     * Stores the indices for the matches (inner ArrayList) for the individual tabs (outer ArrayList).
     * If only the active tab shall be searched, the outer ArrayList contains only one element.
     */
    private ArrayList<ArrayList<Integer>> indices;

    /**
     * Stores the regex that was searched by the dialog.
     */
    private String searchedRegex;


    /**
     * Constructs a new non-modal SearchDialog.
     *
     * @param context   MainFrame in which the dialog is located.
     */
    public SearchDialog(MainFrame context) {
        super(context, false); //Non modal dialog!

        this.context = context;

        create();
    }


    /**
     * Constructs and instantiates the SearchDialog.
     */
    private void create() {
        indices = new ArrayList<ArrayList<Integer>>();

        setLayout(new BorderLayout());

        //Construct mainContainer:
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.PAGE_AXIS));

        //Construct configurationContainer:
        JPanel configurationContainer = new JPanel(new GridLayout(3, 2));
        mainContainer.add(configurationContainer);

        //Construct regexInput:
        regexInput = new JTextField();
        configurationContainer.add(new JLabel(Config.strings.SEARCH_WHAT));
        configurationContainer.add(regexInput);

        //Construct radioButtons:
        searchFile = new JRadioButton(Config.strings.SEARCH_ACTIVE_TAB);
        searchFile.setSelected(true);
        searchAllFiles = new JRadioButton(Config.strings.SEARCH_ALL_TABS);
        searchAllFiles.setEnabled(false);
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(searchFile);
        radioButtonGroup.add(searchAllFiles);
        configurationContainer.add(searchFile);
        configurationContainer.add(new JPanel()); //No component in second column.
        configurationContainer.add(searchAllFiles);

        //Construct replaceContainer:
        JPanel replaceContainer = new JPanel(new GridLayout(4, 2));
        mainContainer.add(new JSeparator());
        mainContainer.add(replaceContainer);

        //Configure enableReplacementCheckbox:
        enableReplacementCheckbox = new JCheckBox(Config.strings.enableSearchReplacement);
        enableReplacementCheckbox.addActionListener(e -> enableReplacement(enableReplacementCheckbox.isSelected()));
        replaceContainer.add(enableReplacementCheckbox);

        //Configure replacementInput
        replaceContainer.add(new JLabel()); //Nothing in second column.
        replacementHintLabel = new JLabel(Config.strings.searchReplacementHint);
        replacementHintLabel.setEnabled(false);
        replaceContainer.add(replacementHintLabel);
        replacementInput = new JTextField();
        replacementInput.setEnabled(false);
        replaceContainer.add(replacementInput);

        //Configure replaceButton:
        JPanel replaceButtonContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        replaceButton = new JButton(Config.strings.replaceButton);
        replaceButton.addActionListener(e -> replace());
        replaceButton.setEnabled(false);
        replaceButtonContainer.add(replaceButton);
        replaceContainer.add(replaceButtonContainer);

        //Configure swapReplaceWithRegex:
        JPanel swapReplaceWithRegexContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        swapReplaceWithRegex = new JButton(Config.strings.swapReplaceAndRegexButton);
        swapReplaceWithRegex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String replacement = replacementInput.getText();
                String regex = regexInput.getText();
                regexInput.setText(replacement);
                replacementInput.setText(regex);
            }
        });
        swapReplaceWithRegex.setEnabled(false);
        swapReplaceWithRegexContainer.add(swapReplaceWithRegex);
        replaceContainer.add(swapReplaceWithRegexContainer);

        //Configure replaceAllButton:
        JPanel replaceAllButtonContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        replaceAllButton = new JButton(Config.strings.replaceAllButton);
        replaceAllButton.addActionListener(e -> replaceAll());
        replaceAllButton.setEnabled(false);
        replaceAllButtonContainer.add(replaceAllButton);
        replaceContainer.add(replaceAllButtonContainer);

        //Construct outputContainer:
        JPanel outputContainer = new JPanel();
        outputContainer.setLayout(new BoxLayout(outputContainer, BoxLayout.PAGE_AXIS));
        mainContainer.add(new JSeparator());
        mainContainer.add(outputContainer);

        //Configure output:
        noMatches = new JLabel(Config.strings.NO_MATCHES);
        noMatches.setVisible(false);
        JPanel noMatchesContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        noMatchesContainer.add(noMatches);
        outputContainer.add(noMatchesContainer);
        matchesContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        outputContainer.add(matchesContainer);
        activeMatch = new JLabel("" + currentMatch);
        matchesContainer.add(activeMatch);
        matchesContainer.add(new JLabel(" / "));
        numberOfMatches = new JLabel("" + numberOfMatchesInt);
        matchesContainer.add(numberOfMatches);
        matchesContainer.setVisible(false);

        //Construct buttonContainer:
        JPanel buttonContainer = new JPanel(new GridLayout(1, 2));
        JPanel searchButtonContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonContainer.add(searchButtonContainer);
        JPanel closeButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.add(closeButtonContainer);

        //Construct closeButton:
        closeButton = new JButton(Config.strings.CLOSE_BUTTON);
        closeButton.addActionListener(e -> dispose());
        closeButtonContainer.add(closeButton);

        //Construct searchButton:
        searchButton = new JButton(Config.strings.SEARCH);
        searchButton.addActionListener(e -> search(0));
        searchButtonContainer.add(searchButton);

        //Construct previousButton:
        previousButton = new JButton(Config.strings.PREVIOUS_BUTTON);
        previousButton.addActionListener(e -> backwardsIterateThroughMatches());
        previousButton.setEnabled(false);
        searchButtonContainer.add(previousButton);

        //Construct nextButton:
        nextButton = new JButton(Config.strings.NEXT_BUTTON);
        nextButton.addActionListener(e -> forwardIterateThroughMatches());
        nextButton.setEnabled(false);
        searchButtonContainer.add(nextButton);

        add(buttonContainer, BorderLayout.SOUTH);
        add(mainContainer, BorderLayout.NORTH);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle(Config.strings.SEARCH_DIALOG_TITLE);
        setSize(512, 300);
        setVisible(true);
    }


    /**
     * Searches the regex in VATE.
     *
     * @param firstMatchToHighlight First match that shall be highlighted in the editor.
     */
    private void search(int firstMatchToHighlight) {
        indices.clear();
        searchedRegex = regexInput.getText();
        if (searchAllFiles.isSelected()) {
            //All files shall be searched:
            indices = context.getTabs().searchAllTabs(searchedRegex);
        }
        else {
            //Only the active tab shall be searched:
            indices.add(context.getTabs().searchInActiveTab(searchedRegex));
        }

        //Count the occurrences:
        numberOfMatchesInt = 0;
        for (ArrayList<Integer> current : indices) {
            if (current != null) {
                numberOfMatchesInt += current.size();
            }
        }

        //Update the dialog:
        currentMatch = 0;
        currentTab = 0;
        if (numberOfMatchesInt > 1) {
            nextButton.setEnabled(true);
            previousButton.setEnabled(true);
        }
        else {
            nextButton.setEnabled(false);
            previousButton.setEnabled(false);
        }

        if (numberOfMatchesInt == 0) {
            //No matches found:
            noMatches.setVisible(true);
            matchesContainer.setVisible(false);
            return;
        }
        else {
            noMatches.setVisible(false);
            matchesContainer.setVisible(true);
        }
        numberOfMatches.setText("" + numberOfMatchesInt);
        activeMatch.setText("" + (currentMatch + 1));

        //Mark the first match:
        currentMatch = firstMatchToHighlight - 1;
        forwardIterateThroughMatches();
    }


    /**
     * Iterates forwards through the matches.
     */
    private void forwardIterateThroughMatches() {
        //Set focus to textArea:
        if (++currentMatch > indices.get(0).size() - 1) {
            //Start all over again:
            currentMatch = 0;
        }
        ((EditorTab)context.getTabs().getSelectedComponent()).markText(indices.get(currentTab).get(currentMatch), searchedRegex.length());
        activeMatch.setText("" + (currentMatch + 1));
    }

    /**
     * Iterates backwards through the matches.
     */
    private void backwardsIterateThroughMatches() {
        if (--currentMatch < 0) {
            //Start all over again:
            currentMatch = indices.get(0).size() - 1;
        }
        ((EditorTab)context.getTabs().getSelectedComponent()).markText(indices.get(currentTab).get(currentMatch), searchedRegex.length());
        activeMatch.setText("" + (currentMatch + 1));
    }


    /**
     * Enables / Disables the replacement options in this dialog.
     *
     * @param enabled   Whether replacement shall be enabled / disabled.
     */
    public void enableReplacement(boolean enabled) {
        enableReplacementCheckbox.setSelected(enabled);
        replacementHintLabel.setEnabled(enabled);
        replacementInput.setEnabled(enabled);
        replaceButton.setEnabled(enabled);
        replaceAllButton.setEnabled(enabled);
        swapReplaceWithRegex.setEnabled(enabled);
    }


    /**
     * Replaces the found regex with the entered replacement.
     */
    private void replace() {
        if (indices == null || indices.size() < 1 || indices.get(0).size() < 1) {
            //Cannot replace:
            return;
        }
        search(currentMatch);

        String replacement = replacementInput.getText();
        int length = regexInput.getText().length();
        int currentPosition = indices.get(0).get(currentMatch);
        Component currentComponent = context.getTabs().getSelectedComponent();
        if (currentComponent instanceof EditorTab) {
            //Can replace:
            EditorTab tab = (EditorTab)currentComponent;
            tab.replace(currentPosition, length, replacement);
        }

        //Adapt the other indices:
        search(currentMatch);
    }

    /**
     * Replaces all regex occurrences with the passed replacement.
     */
    private void replaceAll() {
        if (indices == null || indices.size() < 1 || indices.get(0).size() < 1) {
            //Cannot replace:
            return;
        }
        search(currentMatch);

        String replacement = replacementInput.getText();
        int length = regexInput.getText().length();
        ArrayList<Integer> positions = new ArrayList<Integer>(indices.get(0));
        //Change indices to match new indices after each replacement:
        int positionDifference = replacement.length() - length;
        for (int i = 0; i < positions.size(); i++) {
            positions.set(i, positions.get(i) + i * positionDifference);
        }

        Component currentComponent = context.getTabs().getSelectedComponent();
        if (currentComponent instanceof EditorTab) {
            //Can replace:
            EditorTab tab = (EditorTab)currentComponent;
            tab.replaceAll(positions, length, replacement);
        }

        //Adapt the other indices:
        search(currentMatch);
    }


    /**
     * Disposes (Closes) this dialog.
     */
    @Override
    public void dispose() {
        super.dispose();
    }

}
