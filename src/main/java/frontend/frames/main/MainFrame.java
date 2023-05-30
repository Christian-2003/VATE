package frontend.frames.main;

import backend.config.Config;
import backend.files.File;
import frontend.frames.main.components.EditorTab;
import frontend.frames.main.components.Tab;
import frontend.frames.main.components.TabbedPane;
import frontend.menus.EditMenu;
import frontend.menus.FileMenu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * This class resembles the main frame for VATE.
 *
 * @author  Christian-2003
 * @version 25 May 2023
 */
public class MainFrame extends JFrame {

    /**
     * Stores the TabbedPane which handles all individual tabs.
     */
    private TabbedPane tabs;

    /**
     * Stores the file menu of this frame.
     */
    private FileMenu fileMenu;

    /**
     * Stores the edit menu of this frame.
     */
    private EditMenu editMenu;

    /**
     * Stores the main menu bar.
     */
    private JMenuBar menuBar;


    /**
     * Constructs a new MainFrame.
     */
    public MainFrame() {
        super("VATE");
        create(null);
    }

    /**
     * Constructs a new MainFrame.
     *
     * @param paths Files to open when started.
     */
    public MainFrame(String[] paths) {
        super("VATE");
        create(paths);
    }


    /**
     * Constructs and instantiates the MainFrame.
     *
     * @param paths Files to open when started. Pass {@code null} if no file shall be opened.
     */
    private void create(String[] paths) {
        //Set LookAndFeel for this MainFrame:
        try {
            if (Config.SETTINGS.USE_SYSTEM_LOOK_AND_FEEL) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        }
        catch (Exception e) {
            //Could not set LookAndFeel: Do nothing...
        }

        //Construct tabs:
        tabs = new TabbedPane(this);
        add(tabs, BorderLayout.CENTER);

        //Construct menuBar:
        menuBar = new JMenuBar();
        fileMenu = new FileMenu(this);
        menuBar.add(fileMenu);
        editMenu = new EditMenu(this);
        menuBar.add(editMenu);
        add(menuBar, BorderLayout.NORTH);
        tabs.updateTabbedPane(); //Enable / Disable menu items based on needs.

        //Open files if necessary:
        if (paths != null) {
            //Open passed files as tabs:
            for (String current : paths) {
                File newFile = new File(current);
                if (newFile.exists()) {
                    tabs.addTab(current);
                }
            }
        }
        else if (Config.SETTINGS.LOAD_PREVIOUS_FILES_WHEN_OPENED) {
            //Open previously opened files as tabs:
            for (String current : Config.SETTINGS.PREVIOUSLY_OPENED_FILES) {
                File newFile = new File(current);
                if (newFile.exists()) {
                    tabs.addTab(current);
                }
            }
        }

        setSize(1024, 512);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    public TabbedPane getTabs() {
        return tabs;
    }

    public FileMenu getFileMenu() {
        return fileMenu;
    }

    public EditMenu getEditMenu() {
        return editMenu;
    }


    /**
     * Requests the user to open a new file.
     */
    public void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            //Open the selected file:
            tabs.addTab(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * Requests the user to create a new file.
     */
    public void newFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showDialog(this, Config.STRINGS.CREATE_FILE_BUTTON);
        if (option == JFileChooser.APPROVE_OPTION) {
            //Create the selected file:
            File newFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            if (newFile.exists()) {
                //The entered file already exists:
                option = JOptionPane.showConfirmDialog(this, Config.STRINGS.FILE_ALREADY_EXISTS);
                if (option == JOptionPane.NO_OPTION) {
                    //Do not create file:
                    return;
                }
            }
            //Create new file:
            try {
                newFile.save("");
                tabs.addTab(newFile.getPath());
            }
            catch (IOException e) {
                //Error: Could not create file:
                JOptionPane.showMessageDialog(this, e.getMessage(), Config.STRINGS.COULD_NOT_CREATE_FILE, JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    /**
     * Requests the user to save a file as a new file.
     */
    public void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            //Create the selected file:
            File newFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            if (newFile.exists()) {
                //The entered file already exists:
                option = JOptionPane.showConfirmDialog(this, Config.STRINGS.FILE_ALREADY_EXISTS);
                if (option == JOptionPane.NO_OPTION) {
                    //Do not create file:
                    return;
                }
            }
            //Save the file:
            tabs.saveActiveTabAs(newFile.getPath());
        }
    }


    /**
     * Disposes (Closes) this frame.
     */
    @Override
    public void dispose() {
        if (tabs.hasUnsavedChanges()) {
            int option = JOptionPane.showConfirmDialog(this, Config.STRINGS.UNSAVED_CHANGES);
            if (option == JOptionPane.YES_OPTION) {
                //Save changes:
                tabs.saveAllTabs();
            }
            else if (option == JOptionPane.CANCEL_OPTION) {
                //Do not dispose this window:
                return;
            }
        }
        //Save currently opened tabs to config:
        Config.SETTINGS.PREVIOUSLY_OPENED_FILES.clear();
        for (int i = 0; i < tabs.getTabCount(); i++) {
            Config.SETTINGS.PREVIOUSLY_OPENED_FILES.add(((Tab)tabs.getComponentAt(i)).getFile().getPath());
        }
        super.dispose();
    }

}
