package frontend.frames.main.components;

import backend.config.Config;
import backend.files.File;
import backend.html.ExportToHTML;
import frontend.dialogs.SearchDialog;
import frontend.frames.main.MainFrame;
import frontend.menus.TabPopupMenu;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class implements a component which contains and handles all tabs dor VATE.
 *
 * @author  Christian-2003
 * @version 26 May 2023
 */
public class TabbedPane extends JTabbedPane implements MouseListener {

    /**
     * Stores the MainFrame in which this TabbedPane is located.
     */
    private MainFrame context;

    /**
     * Popup menu shows whenever the user right-clicks onto a tab.
     */
    private TabPopupMenu popupMenu;


    /**
     * Constructs a new TabbedPane.
     *
     * @param context   MainFrame in which this TabbedPane is located.
     */
    public TabbedPane(MainFrame context) {
        super();

        this.context = context;

        create();
    }


    /**
     * Constructs and instantiates the TabbedPane.
     */
    private void create() {
        setTabPlacement(JTabbedPane.TOP);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        setFont(getFont().deriveFont((float)Config.fonts.PROGRAM_FONT_SIZE));

        //Add MouseListener to open a Popup menu on right-click:
        popupMenu = new TabPopupMenu(this);


        //Add changeListener to disable menu items when needed:
        addChangeListener(e -> updateTabbedPane());
    }

    /**
     * Updates all components that are dependent on different tabs, such as menus. Some menu items are enabled or disabled
     * whenever specific tabs are selected.
     */
    public void updateTabbedPane() {
        if (getTabCount() == 0) {
            //No tabs shown:
            context.getFileMenu().toggleSave(false);
            context.getFileMenu().toggleSaveAs(false);
            context.getFileMenu().toggleExportToHtml(false);
            context.getEditMenu().toggleSearch(false);
            //Remove mouse listener for right-click when tabs are shown:
            removeMouseListener(this);
        }
        else {
            //Tabs shown:
            context.getFileMenu().toggleSave(true);
            context.getFileMenu().toggleSaveAs(true);
            context.getFileMenu().toggleExportToHtml(true);
            context.getEditMenu().toggleSearch(true);
            //Add mouse listener for right-click when tabs are shown:
            addMouseListener(this);
        }
    }


    /**
     * Opens the file at the passed path to the handled tabs. If the file is already opened, the file will not be opened
     * again, instead an information message will be shown and the respective tab will be selected.
     *
     * @param path  Path of the file to be opened.
     * @return      Whether the file was opened and added to the handled tabs or not.
     */
    public boolean addTab(String path) {
        for (int i = 0; i < getTabCount(); i++) {
            if (getComponentAt(i) instanceof Tab && ((Tab)getComponentAt(i)).getFile().getPath().equals(path)) {
                //Path already opened:
                setSelectedIndex(i); //Show opened file.
                JOptionPane.showMessageDialog(this, Config.strings.FILE_ALREADY_OPENED);
                return false;
            }
        }
        //Open file:
        EditorTab newTab = new EditorTab(path);
        addTab(newTab.getFile().getNameWithExtension(), newTab);
        setSelectedComponent(newTab); //Show opened tab.

        return true;
    }


    /**
     * This method lets the user search a String.
     */
    public void search() {
        SearchDialog dialog = new SearchDialog(context);
    }

    /**
     * This method lets the user search and replace a String.
     */
    public void searchAndReplace() {
        SearchDialog dialog = new SearchDialog(context);
        dialog.enableReplacement(true);
    }

    /**
     * Searches the active tab for the regex. This only works in an {@linkplain EditorTab}. If the active tab is no
     * EditorTab, null will be returned.
     *
     * @param regex Regex to search in the active tab.
     * @return      ArrayList containing the indices of the matches in the active tab.
     */
    public ArrayList<Integer> searchInActiveTab(String regex) {
        if (getSelectedComponent() instanceof EditorTab) {
            return ((EditorTab) getSelectedComponent()).search(regex);
        }
        return null;
    }

    /**
     * Searches all {@linkplain EditorTab}s for the passed regex. The tabs are stored within the 'outer' ArrayList while
     * the individual indices of the matches for the respective tabs are stored in the 'inner' ArrayList. If a Tab is
     * encountered, that is no EditorTab, the 'outer' ArrayList contains {@code null}.
     *
     * @param regex Regex to search in al tabs.
     * @return      ArrayList containing an ArrayList of the indices for each match in the respective tab.
     */
    public ArrayList<ArrayList<Integer>> searchAllTabs(String regex) {
        ArrayList<ArrayList<Integer>> tabs = new ArrayList<ArrayList<Integer>>(getTabCount());
        for (int i = 0; i < getTabCount(); i++) {
            if (getComponentAt(i) instanceof EditorTab) {
                tabs.add(((EditorTab) getComponentAt(i)).search(regex));
            }
            else {
                //Current tab is no EditorTab:
                tabs.add(null);
            }
        }
        return tabs;
    }


    /**
     * This method closes the active tab.
     *
     * @return  Whether the active tab was (disposed) closed properly.
     */
    public boolean disposeActiveTab() {
        if (getSelectedComponent() instanceof Tab) {
            boolean disposed = ((Tab)getSelectedComponent()).dispose();
            if (disposed) {
                //Tab was successfully disposed:
                remove(getSelectedComponent());
            }
            return disposed;
        }
        return false;
    }

    /**
     * Shows the active tab within the system explorer.
     */
    public void showActiveTabInExplorer() {
        if (getSelectedComponent() instanceof Tab) {
            String path = ((Tab)getSelectedComponent()).getFile().getPath();
            try {
                Runtime.getRuntime().exec("explorer.exe /select," + path);
            }
            catch (IOException e) {
                //Could not show in explorer: Do nothing...
            }
        }
    }


    /**
     * Saves the contents of the active tab to the file.
     */
    public void saveActiveTab() {
        if (getSelectedComponent() instanceof Tab) {
            ((Tab)getSelectedComponent()).save();
        }
    }

    /**
     * Saves the contents of the active tab to the file of the new path.
     *
     * @param path  Path for the new file.
     */
    public void saveActiveTabAs(String path) {
        if (getSelectedComponent() instanceof Tab) {
            ((Tab)getSelectedComponent()).getFile().setPath(path);
            ((Tab)getSelectedComponent()).save();
        }
    }


    /**
     * Saves the contents of all tabs.
     */
    public void saveAllTabs() {
        for (int i = 0; i < getTabCount(); i++) {
            ((Tab)getComponentAt(i)).save();
        }
    }

    /**
     * Tests whether any tab has unsaved changes. If so, {@code true} is returned, otherwise {@code false} will be
     * returned.
     *
     * @return  Whether any tab has unsaved changes.
     */
    public boolean hasUnsavedChanges() {
        for (int i = 0; i < getTabCount(); i++) {
            if (((Tab)getComponentAt(i)).hasUnsavedChanges()) {
                //There are unsaved changes:
                return true;
            }
        }
        //There are no unsaved changes:
        return false;
    }


    /**
     * Exports the active tab to HTML.
     */
    public void exportActiveTabToHTML() {
        //Ask the user to enter a file to which to save the HTML:
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showDialog(this, Config.strings.CREATE_FILE_BUTTON);
        if (option == JFileChooser.APPROVE_OPTION) {
            //Create the selected file:
            File newFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            if (newFile.exists()) {
                //The entered file already exists:
                option = JOptionPane.showConfirmDialog(this, Config.strings.FILE_ALREADY_EXISTS);
                if (option == JOptionPane.NO_OPTION) {
                    //Do not create file:
                    return;
                }
            }
            //Create new file:
            try {
                if (getSelectedComponent() instanceof EditorTab) {
                    newFile.save(ExportToHTML.GENERATE_HTML(((EditorTab)getSelectedComponent()).getText()));
                }
                else {
                    //Cannot export opened file:
                    JOptionPane.showMessageDialog(this, Config.strings.FILE_NOT_SUITABLE_FOR_EXPORT, Config.strings.COULD_NOT_CREATE_FILE, JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (IOException e) {
                //Error: Could not create file:
                JOptionPane.showMessageDialog(this, e.getMessage(), Config.strings.COULD_NOT_CREATE_FILE, JOptionPane.ERROR_MESSAGE);
            }
        }
    }





    //====================================================================================================
    //                      Implemented methods of the MouseListener below
    //====================================================================================================

    /**
     * This method is called whenever the user right-clicks onto a tab.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            //Right click detected:
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Do nothing, as this mouse method of the MouseListener is not needed...
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Do nothing, as this mouse method of the MouseListener is not needed...
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Do nothing, as this mouse method of the MouseListener is not needed...
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Do nothing, as this mouse method of the MouseListener is not needed...
    }

}
