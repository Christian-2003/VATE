package frontend.frames.main;

import backend.config.Config;
import backend.files.File;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import frontend.frames.main.components.Tab;
import frontend.frames.main.components.TabbedPane;
import frontend.menus.EditMenu;
import frontend.menus.FileMenu;
import frontend.menus.HelpMenu;

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
     * Stores the help menu of this frame.
     */
    private HelpMenu helpMenu;

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
            if (Config.settings.useSystemLookAndFeel) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            else {
                //Use FlatLaf Light theme:
                UIManager.setLookAndFeel(new FlatLightLaf());
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
        helpMenu = new HelpMenu(this);
        menuBar.add(helpMenu);
        add(menuBar, BorderLayout.NORTH);
        tabs.updateTabbedPane(); //Enable / Disable menu items based on needs.

        //Open files if necessary:
        if (paths != null && paths.length != 0) {
            //Open passed files as tabs:
            for (String current : paths) {
                File newFile = new File(current);
                if (newFile.exists()) {
                    tabs.addTab(current);
                }
            }
        }
        else if (Config.settings.loadPreviousFilesWhenOpened) {
            //Open previously opened files as tabs:
            for (String current : Config.settings.previouslyOpenedFiles) {
                File newFile = new File(current);
                if (newFile.exists()) {
                    tabs.addTab(current);
                }
            }
        }

        //Set size:
        if (Config.settings.usePreviousDimensionWhenCreated && Config.settings.previousHeight != -1 && Config.settings.previousWidth != -1) {
            //Use previous dimensions:
            setSize(Config.settings.previousWidth, Config.settings.previousHeight);
        }
        else {
            //Use standard size:
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setSize((int)(screenSize.getWidth() * 0.6), (int)(screenSize.getHeight() * 0.6));
        }

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
        int option = fileChooser.showDialog(this, Config.strings.createFileButton);
        if (option == JFileChooser.APPROVE_OPTION) {
            //Create the selected file:
            File newFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            if (newFile.exists()) {
                //The entered file already exists:
                option = JOptionPane.showConfirmDialog(this, Config.strings.fileAlreadyExists);
                if (option == JOptionPane.NO_OPTION) {
                    //Do not create file:
                    return;
                }
            }
            //Create new file:
            try {
                newFile.save("");
                tabs.addTab(newFile.getAbsolutePath());
            }
            catch (IOException e) {
                //Error: Could not create file:
                JOptionPane.showMessageDialog(this, e.getMessage(), Config.strings.couldNotCreateFile, JOptionPane.ERROR_MESSAGE);
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
                option = JOptionPane.showConfirmDialog(this, Config.strings.fileAlreadyExists);
                if (option == JOptionPane.NO_OPTION) {
                    //Do not create file:
                    return;
                }
            }
            //Save the file:
            tabs.saveActiveTabAs(newFile.getAbsolutePath());
        }
    }


    /**
     * Disposes (Closes) this frame.
     */
    @Override
    public void dispose() {
        if (tabs.hasUnsavedChanges()) {
            int option = JOptionPane.showConfirmDialog(this, Config.strings.unsavedChanges);
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
        Config.settings.previouslyOpenedFiles.clear();
        for (int i = 0; i < tabs.getTabCount(); i++) {
            Config.settings.previouslyOpenedFiles.add(((Tab)tabs.getComponentAt(i)).getFile().getAbsolutePath());
        }

        //Save the current dimension:
        if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            //MainFrame is not in fullscreen, save current dimensions:
            Config.settings.previousWidth = getWidth();
            Config.settings.previousHeight = getHeight();
        }

        //Save the config:
        try {
            Config.saveConfig();
        }
        catch (IOException e) {
            //Could not save config:
            //What am I supposed to do now? Do nothing instead.
        }
        super.dispose();
    }

}
