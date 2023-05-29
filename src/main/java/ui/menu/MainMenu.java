package ui.menu;

import backend.singleton.Singleton;
import ui.windows.licenses.LicensesFrame;
import ui.windows.main.MainFrame;
import ui.windows.settings.SettingsFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;


/**
 * This class implements the main menu for the MainWindow.
 *
 * @author  Christian-2003
 * @version 11 May 2023
 */
public class MainMenu extends JMenuBar {

    /**
     * Reference to the MainWindow to which this menu is applied.
     */
    private final MainFrame context;

    /**
     * Menu items for the menu "File".
     */
    JMenuItem newFile, openFile, newWindow, saveAs, save, exit, exportToHTML;

    /**
     * Menu items for the menu "Edit".
     */
    JMenuItem search, goTo;

    /**
     * Checkbox menu items for the menu "Settings".
     */
    JCheckBoxMenuItem showLineNumbers;

    /**
     * Menu items for the menu "Settings".
     */
    JMenuItem settings;

    /**
     * Menu items for the menu "Help".
     */
    JMenuItem license;


    /**
     * Constructs a new main menu.
     *
     * @param context   MainWindow to which this menu is applied.
     */
    public MainMenu(MainFrame context) {
        super();
        this.context = context;

        initFileMenu(); //Configure "File"-menu.
        initEditMenu(); //Configure "Edit"-menu.
        initSettingsMenu(); //Configure "Settings"-menu.
        initHelpMenu(); //Configure "Help"-menu.
    }


    /**
     * Configures the "File"-menu.
     */
    private void initFileMenu() {
        JMenu fileMenu = new JMenu("File");

        //Configure "newFile" menu item:
        newFile = new JMenuItem("New File");
        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.requestNewFileCreate();
            }
        });
        fileMenu.add(newFile);

        //Configure "openFile" menu item:
        openFile = new JMenuItem("Open");
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.requestFileOpen();
            }
        });
        fileMenu.add(openFile);

        //Configure "newWindow" menu item:
        newWindow = new JMenuItem("New Window");
        newWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame(false, null); //Create a new main window without previous file opened.
            }
        });
        fileMenu.add(newWindow);

        fileMenu.add(new JSeparator());

        //Configure "saveAs" menu item:
        saveAs = new JMenuItem("Save As");
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.requestSaveAs();
            }
        });
        fileMenu.add(saveAs);

        //Configure "save" menu item:
        save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.requestSave();
            }
        });
        save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK)); //Action of the menu item is triggered with CTRL+S.
        fileMenu.add(save);

        //Configure "exportToHTML" menu item:
        exportToHTML = new JMenuItem("Export to HTML");
        exportToHTML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.requestExportToHTML();
            }
        });
        fileMenu.add(exportToHTML);

        fileMenu.add(new JSeparator());

        //Configure "exit" menu item:
        exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.dispose(); //Close the window.
            }
        });
        exit.setAccelerator(KeyStroke.getKeyStroke('s', InputEvent.ALT_DOWN_MASK)); //Action of the menu item is triggered with ALT+F4.
        fileMenu.add(exit);

        add(fileMenu);
    }

    /**
     * Configures the "Edit"-menu.
     */
    private void initEditMenu() {
        JMenu editMenu = new JMenu("Edit");

        //Configure "search" menu item:
        search = new JMenuItem("Search");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.requestSearch();
            }
        });
        search.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_DOWN_MASK)); //Action of the menu item is triggered with CTRL+F.
        editMenu.add(search);

        //Configure "goTo" menu item:
        goTo = new JMenuItem("Goto");
        goTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.requestGoto();
            }
        });
        goTo.setAccelerator(KeyStroke.getKeyStroke('G', InputEvent.CTRL_DOWN_MASK)); //Action of the menu item is triggered with CTRL+S.
        goTo.setEnabled(false); //TODO: Enable item once the code works!
        editMenu.add(goTo);

        add(editMenu);
    }

    /**
     * Configures the "Settings"-menu.
     */
    private void initSettingsMenu() {
        JMenu settingsMenu = new JMenu("Settings");

        //Configure "showLineNumbers" menu item:
        showLineNumbers = new JCheckBoxMenuItem("Show Line Numbers", Singleton.CONFIG.showLineNumbers);
        showLineNumbers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.requestToggleLineNumbers(showLineNumbers.getState());
            }
        });
        settingsMenu.add(showLineNumbers);

        //Configure "settings" menu item:
        settings = new JMenuItem("Settings");
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsFrame(context);
            }
        });
        settingsMenu.add(settings);

        add(settingsMenu);
    }

    /**
     * Configures the "Help"-menu.
     */
    private void initHelpMenu() {
        JMenu helpMenu = new JMenu("Help");

        //Configure "license" menu item:
        license = new JMenuItem("License");
        license.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LicensesFrame(); //Create new Licenses Window.
            }
        });
        helpMenu.add(license);

        add(helpMenu);
    }

}
