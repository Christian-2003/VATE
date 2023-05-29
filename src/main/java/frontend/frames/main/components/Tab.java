package frontend.frames.main.components;

import backend.config.Config;
import backend.files.File;
import frontend.menus.TabPopupMenu;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * This class implements a Tab for VATE. Individual tabs should extend this class for optimal use.
 *
 * @author  Christian-2003
 * @version 25 May 2023
 */
public abstract class Tab extends JPanel {

    /**
     * Stores the title for the tab.
     */
    protected String title;

    /**
     * Stores the file that is displayed by this Tab.
     */
    protected File file;

    /**
     * Stores whether there are unsaved changes to the file.
     * This shall be set to {@code true} whenever the file is edited. It's changed to {@code false} whenever
     * {@link #save()} is called.
     */
    protected boolean unsavedChanges;


    /**
     * Constructs a new tab that displays the passed file.
     *
     * @param path  Path for the file.
     */
    public Tab(String path) {
        file = new File(path);
        create();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFilepath(String path) {
        file.setPath(path);
    }

    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }


    /**
     * Converts this tab into a String of the following format:
     *  {"Title": "<title>", "Path": "<path>"}
     *
     * @return  String representation of this class.
     */
    @Override
    public String toString() {
        return "{\"Title\": \"" + title + "\", \"Path\":\"" + file.getPath() + "\"}";
    }


    /**
     * Instantiates and constructs the Tab.
     */
    private void create() {
        title = file.getName();
        unsavedChanges = false;

        setBackground(Config.COLORS.TEXT_EDITOR_BACKGROUND);
        setForeground(Config.COLORS.TEXT_EDITOR_FOREGROUND);
    }


    /**
     * Saves the contents of the edited file.
     *
     * @return  Whether the file was saved successfully or not.
     */
    public abstract boolean save();

    /**
     * Loads the file that is edited by this tab. Returns {@code false} if the file cannot be opened.
     *
     * @return  Whether the file was loaded successfully.
     */
    public abstract boolean load();

    /**
     * Updates the line numbers to the passed argument.
     *
     * @param lineNumbers   New line numbers of the tab's content.
     */
    public abstract void updateLineNumbers(int lineNumbers);

    /**
     * Updates the file's length to the passed argument.
     *
     * @param length    New length for the tab's content.
     */
    public abstract void updateLength(int length);

    /**
     * Requests the tab to be closed. This method should do everything that is needed to properly close the tab and
     * save pending changes.
     *
     * @return  Whether the tab was successfully disposed (closed) or not.
     */
    public abstract boolean dispose();

}
