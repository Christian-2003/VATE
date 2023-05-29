package frontend.menus;

import backend.config.Config;
import frontend.frames.main.components.Tab;
import frontend.frames.main.components.TabbedPane;
import javax.swing.*;


/**
 * This class implements a popup menu that provides functionality for individual tabs within VATE.
 * This class is intended to be instantiated when the user right-clicks on a {@linkplain Tab}.
 *
 * @author  Christian-2003
 * @version 28 May 2023
 */
public class TabPopupMenu extends JPopupMenu {

    /**
     * TabbedPane in which the TabPopupMenu is located.
     */
    TabbedPane context;

    /**
     * Menu items for the popup menu.
     */
    JMenuItem closeActiveTab, saveActiveTab, showInExplorer;


    /**
     * Constructs a new TabPopupMenu within the passed TabbedPane.
     *
     * @param context   TabbedPane in which this TabPopupMenu is located.
     */
    public TabPopupMenu(TabbedPane context) {
        super();

        this.context = context;

        create();
    }


    /**
     * Constructs and instantiates the TabPopupMenu.
     */
    private void create() {
        //Construct closeActiveTab:
        closeActiveTab = new JMenuItem(Config.STRINGS.CLOSE_TAB);
        closeActiveTab.addActionListener(e -> context.disposeActiveTab());
        add(closeActiveTab);

        //Configure saveActiveTab:
        saveActiveTab = new JMenuItem(Config.STRINGS.SAVE_TAB);
        saveActiveTab.addActionListener(e -> context.saveActiveTab());
        add(saveActiveTab);

        //Construct showInExplorer:
        showInExplorer = new JMenuItem(Config.STRINGS.SHOW_TAB_IN_EXPLORER);
        showInExplorer.addActionListener(e -> context.showActiveTabInExplorer());
        add(showInExplorer);

    }

}
