package frontend.menus;

import backend.config.Config;
import frontend.frames.licenses.LicensesFrame;
import frontend.frames.main.MainFrame;

import javax.swing.*;


/**
 * Implements the help menu for the MainFrame.
 *
 * @author  Christian-2003
 * @version 01 June 2023
 */
public class HelpMenu extends JMenu {

    /**
     * Stores the menu items for the HelpMenu.
     */
    private JMenuItem license;


    /**
     * Constructs a new HelpMenu.
     *
     * @param context   MainFrame in which the menu is located.
     */
    public HelpMenu(MainFrame context) {
        super(Config.strings.helpMenu);

        //Configure license:
        license = new JMenuItem(Config.strings.license);
        license.addActionListener(e -> new LicensesFrame(context));
        add(license);
    }

}
