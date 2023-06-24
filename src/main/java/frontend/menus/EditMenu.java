package frontend.menus;

import backend.config.Config;
import frontend.frames.main.MainFrame;
import frontend.frames.main.components.EditorTab;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class EditMenu extends JMenu {

    /**
     * MainFrame in which this EditMenu is located.
     */
    private MainFrame context;

    /**
     * Menu items for the EditMenu.
     */
    private JMenuItem undo, redo, search, searchAndReplace;


    /**
     * Constructs a new EditMenu.
     *
     * @param context   MainFrame in which this menu is located.
     */
    public EditMenu(MainFrame context) {
        super(Config.strings.editMenu);

        this.context = context;

        create();
    }


    /**
     * Constructs and instantiates the EditMenu.
     */
    private void create() {
        //Configure undo:
        undo = new JMenuItem(Config.strings.undo);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (context.getTabs().getSelectedComponent() instanceof EditorTab) {
                    ((EditorTab)context.getTabs().getSelectedComponent()).undo();;
                }
            }
        });
        undo.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_DOWN_MASK)); //Menu item is triggered with CTRL + Z.
        add(undo);

        //Configure redo:
        redo = new JMenuItem(Config.strings.redo);
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (context.getTabs().getSelectedComponent() instanceof EditorTab) {
                    ((EditorTab)context.getTabs().getSelectedComponent()).redo();;
                }
            }
        });
        redo.setAccelerator(KeyStroke.getKeyStroke('Y', InputEvent.CTRL_DOWN_MASK)); //Menu item is triggered with CTRL + Y.
        add(redo);

        add(new JSeparator());

        //Configure search:
        search = new JMenuItem(Config.strings.search);
        search.addActionListener(e -> context.getTabs().search());
        search.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_DOWN_MASK)); //Menu item is triggered with CTRL + F.
        add(search);

        //Configure searchAndReplace:
        searchAndReplace = new JMenuItem(Config.strings.searchAndReplace);
        searchAndReplace.addActionListener(e -> context.getTabs().searchAndReplace());
        add(searchAndReplace);
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
