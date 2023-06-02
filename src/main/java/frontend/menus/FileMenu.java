package frontend.menus;

import backend.config.Config;
import frontend.frames.main.MainFrame;

import javax.swing.*;
import java.awt.event.InputEvent;


/**
 * This class implements the "File"-menu for the menu bar of the main window.
 *
 * @author  Christian-2003
 * @version 28 May 2023
 */
public class FileMenu extends JMenu {

    /**
     * MainFrame in which this FileMenu is located.
     */
    private MainFrame context;

    /**
     * MenuItems for the FileMenu.
     */
    private JMenuItem open, newFile, save, saveAs, exportToHTML, exit;


    /**
     * Constructs a new FileMenu within the passed MainFrame.
     *
     * @param context   MainFrame in which the FileMenu is located.
     */
    public FileMenu(MainFrame context) {
        super(Config.strings.fileMenu);

        this.context = context;


        create();
    }


    /**
     * Configures and instantiates the FileMenu.
     */
    public void create() {
        //Configure open:
        open = new JMenuItem(Config.strings.openFile);
        open.addActionListener(e -> context.openFile());
        open.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK)); //Menu item is triggered with CTRL + O.
        add(open);

        //Configure newFile:
        newFile = new JMenuItem(Config.strings.newFile);
        newFile.addActionListener(e -> context.newFile());
        newFile.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK)); //Menu item is triggered with CTRL + N.
        add(newFile);

        add(new JSeparator());

        //Configure save:
        save = new JMenuItem(Config.strings.save);
        save.addActionListener(e -> context.getTabs().saveActiveTab());
        save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK)); //Menu item is triggered with CTRL + S.
        add(save);

        //Configure saveAs:
        saveAs = new JMenuItem(Config.strings.saveAs);
        saveAs.addActionListener(e -> context.saveFileAs());
        add(saveAs);

        //Configure exportToHTML:
        exportToHTML = new JMenuItem(Config.strings.exportToHtml);
        exportToHTML.addActionListener(e -> context.getTabs().exportActiveTabToHTML());
        add(exportToHTML);

        add(new JSeparator());

        //Configure exit:
        exit = new JMenuItem(Config.strings.exit);
        exit.addActionListener(e -> context.dispose());
        exit.setAccelerator(KeyStroke.getKeyStroke('s', InputEvent.ALT_DOWN_MASK)); //Menu item is triggered with ALT + F4.
        add(exit);
    }


    /**
     * Toggles whether the {@link #save} menu item is enabled.
     *
     * @param enabled   Whether the menu item shall be enabled.
     */
    public void toggleSave(boolean enabled) {
        save.setEnabled(enabled);
    }

    /**
     * Toggles whether the {@link #saveAs} menu item is enabled.
     *
     * @param enabled   Whether the menu item shall be enabled.
     */
    public void toggleSaveAs(boolean enabled) {
        saveAs.setEnabled(enabled);
    }

    /**
     * Toggles whether the {@link #exportToHTML} menu item is enabled.
     *
     * @param enabled   Whether the menu item shall be enabled.
     */
    public void toggleExportToHtml(boolean enabled) {
        exportToHTML.setEnabled(enabled);
    }

}
