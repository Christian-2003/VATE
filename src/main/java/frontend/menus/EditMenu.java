package frontend.menus;

import backend.config.Config;
import frontend.frames.main.MainFrame;

import javax.swing.*;
import java.awt.event.InputEvent;

public class EditMenu extends JMenu {

    /**
     * MainFrame in which this EditMenu is located.
     */
    private MainFrame context;

    /**
     * Menu items for the EditMenu.
     */
    private JMenuItem search;


    /**
     * Constructs a new EditMenu.
     *
     * @param context   MainFrame in which this menu is located.
     */
    public EditMenu(MainFrame context) {
        super(Config.strings.EDIT_MENU);

        this.context = context;

        create();
    }


    /**
     * Constructs and instantiates the EditMenu.
     */
    private void create() {
        //Configure search:
        search = new JMenuItem(Config.strings.SEARCH);
        search.addActionListener(e -> context.getTabs().search());
        search.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_DOWN_MASK)); //Menu item is triggered with CTRL + F.
        add(search);
    }


    /**
     * Toggles whether the {@link #search} menu item is enabled.
     *
     * @param enabled   Whether the menu item shall be enabled.
     */
    public void toggleSearch(boolean enabled) {
        search.setEnabled(enabled);
    }


}
