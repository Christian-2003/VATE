package frontend.dialogs;

import backend.config.Config;
import frontend.frames.main.MainFrame;
import frontend.frames.main.components.EditorTab;

import javax.swing.*;
import java.awt.*;
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
    private JButton closeButton, searchButton, nextButton, previousButton;

    /**
     * Radio buttons for the dialog.
     */
    private JRadioButton searchFile, searchAllFiles;

    /**
     * Text inputs for the dialog.
     */
    private JTextField regexInput;

    /**
     * Labels for the dialog.
     */
    private JLabel numberOfMatches, activeMatch, noMatches;

    /**
     * JPanel contains the display of the number of matches.
     */
    JPanel matchesContainer;

    /**
     * Stores the number of matches and the active match as integers.
     */
    private int numberOfMatchesInt, currentMatch, currentTab, currentIndexInCurrentTab;

    /**
     * Stores the indices for the matches (inner ArrayList) for the individual tabs (outer ArrayList).
     * If only the active tab shall be searched, the outer ArrayList contains only one element.
     */
    private ArrayList<ArrayList<Integer>> indices;

    /**
     * Stores the regex that was searched by the dialog.
     */
    String searchedRegex;


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

        //Construct buttonContainer:
        JPanel buttonContainer = new JPanel(new GridLayout(1, 2));
        JPanel searchButtonContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonContainer.add(searchButtonContainer);
        JPanel closeButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.add(closeButtonContainer);

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

        //Construct closeButton:
        closeButton = new JButton(Config.strings.CLOSE_BUTTON);
        closeButton.addActionListener(e -> dispose());
        closeButtonContainer.add(closeButton);

        //Construct searchButton:
        searchButton = new JButton(Config.strings.SEARCH);
        searchButton.addActionListener(e -> search());
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
        setSize(512, 256);
        setVisible(true);
    }


    /**
     * Searches the regex in VATE.
     */
    private void search() {
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
        currentIndexInCurrentTab = 0;
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
        currentMatch = -1;
        forwardIterateThroughMatches();
    }


    private void forwardIterateThroughMatches() {
        //Set focus to textArea:
        if (++currentMatch > indices.get(0).size() - 1) {
            //Start all over again:
            currentMatch = 0;
        }
        ((EditorTab)context.getTabs().getSelectedComponent()).markText(indices.get(currentTab).get(currentMatch), searchedRegex.length());
        activeMatch.setText("" + (currentMatch + 1));
    }

    private void backwardsIterateThroughMatches() {
        if (--currentMatch < 0) {
            //Start all over again:
            currentMatch = indices.get(0).size() - 1;
        }
        ((EditorTab)context.getTabs().getSelectedComponent()).markText(indices.get(currentTab).get(currentMatch), searchedRegex.length());
        activeMatch.setText("" + (currentMatch + 1));
    }


    /**
     * Disposes (Closes) this dialog.
     */
    @Override
    public void dispose() {
        super.dispose();
    }

}
