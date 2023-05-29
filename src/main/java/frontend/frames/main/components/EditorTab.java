package frontend.frames.main.components;

import backend.config.Config;
import frontend.menus.TabPopupMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class implements a Tab that can be used to edit text.
 *
 * @author  Christian-2003
 * @version 25 May 2023
 */
public class EditorTab extends Tab {

    /**
     * Stores the main text area that displays the text.
     */
    private TextArea textArea;

    /**
     * Stores the subline that displays further information about the edited text.
     */
    private Subline subline;


    /**
     * Constructs a new EditorTab which allows text to be edited.
     *
     * @param path  Path of the file to be edited.
     */
    public EditorTab(String path) {
        super(path);

        create();
    }


    /**
     * Instantiates and constructs the EditorTab.
     */
    private void create() {
        setLayout(new BorderLayout());

        subline = new Subline(file.getExtension(), 0, 0);
        textArea = new TextArea(this);
        load();

        add(textArea, BorderLayout.CENTER);
        add(subline, BorderLayout.SOUTH);
    }


    public String getText() {
        return textArea.getText();
    }


    /**
     * Searches the displayed text for the passed regex. The method returns and ArrayList containing the indices of
     * the first character of all matches for the passed regex.
     *
     * @param regex Regex to be searched in the displayed text.
     * @return      ArrayList of the indices for all found matches.
     */
    public ArrayList<Integer> search(String regex) {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        String text = getText();
        int currentIndex = -1;
        while (++currentIndex < text.length() - 1) {
            currentIndex = text.indexOf(regex, currentIndex);
            if (currentIndex == -1) {
                //Did not find any other matches:
                break;
            }
            indices.add(currentIndex);
        }
        //Return all indices:
        return indices;
    }

    /**
     * Marks text at the specified position for the specified length.
     *
     * @param position  Index of the first character to be marked.
     * @param length    Number of characters to be marked.
     * @return          Whether the text could be marked.
     */
    public boolean markText(int position, int length) {
        return textArea.markText(position, length);
    }


    /**
     * Saves the content of the text editor to the edited file.
     *
     * @return  Whether the file was saved successfully or not.
     */
    public boolean save() {
        try {
            file.save(textArea.getText());
        }
        catch (IOException e) {
            //Error: Could not save file:
            return false;
        }
        return true;
    }

    /**
     * Loads the contents of the file and displays them within the text editor.
     *
     * @return  Whether the file was successfully loaded.
     */
    public boolean load() {
        try {
            textArea.setText(file.load());
            unsavedChanges = false; //No unsaved changes when the file was loaded!
        }
        catch (FileNotFoundException e) {
            //Error: Could not open file:
            return false;
        }
        return true;
    }

    /**
     * Updates the line numbers to the passed argument.
     *
     * @param lineNumbers   New line numbers of the tab's content.
     */
    public void updateLineNumbers(int lineNumbers) {
        subline.setLines(lineNumbers);
    }

    /**
     * Updates the file's length to the passed argument.
     *
     * @param length    New length for the tab's content.
     */
    public void updateLength(int length) {
        subline.setLength(length);
    }

    /**
     * Requests the tab to be closed. This method should do everything that is needed to properly close the tab and
     * save pending changes.
     *
     * @return  Whether the tab was successfully disposed (closed) or not.
     */
    public boolean dispose() {
        if (unsavedChanges) {
            //There are unsaved changes:
            int option = JOptionPane.showConfirmDialog(this, Config.STRINGS.ASK_FOR_CHANGES_TO_BE_SAVED);
            if (option == JOptionPane.NO_OPTION) {
                //Discard unsaved changes:
                unsavedChanges = false;
                return true;
            }
            else if (option == JOptionPane.YES_OPTION) {
                //Save unsaved changes:
                if (save()) {
                    //Changes were saved successfully:
                    unsavedChanges = false;
                    return true;
                }
            }
            else {
                //Do not close this EditorTab:
                return false;
            }
        }
        //There are no unsaved changes:
        return true;
    }

}
