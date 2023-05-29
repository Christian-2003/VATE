package ui.windows.main.components;

import ui.windows.main.MainFrame;
import ui.windows.main.components.editorTabs.EditorTab;
import ui.windows.main.components.editorTabs.TextEditorTab;

import javax.swing.*;
import java.util.ArrayList;


/**
 * This class can handle multiple EditorTabs.
 *
 * @author  Christian-2003
 * @version 24 May 2023
 */
public class EditorTabbedPane extends JTabbedPane {

    /**
     * Stores the MainWindow in which the EditorTabbedPane is located.
     */
    private MainFrame context;

    /**
     * Stores the individual tabs that are handled by the EditorTabbedPane.
     */
    private ArrayList<EditorTab> tabs;


    /**
     * Constructs a new EditorTabbedPane that can handle the EditorTabs of the editor.
     *
     * @param context   MainWindow in which the EditorTabbedPane is located.
     */
    public EditorTabbedPane(MainFrame context) {
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        this.context = context;
        tabs = new ArrayList<EditorTab>();
    }


    /**
     * Returns the tab at the specified index.
     *
     * @param i                             Index of the tab that shall be returned.
     * @return                              EditorTab at the specified index.
     * @throws IndexOutOfBoundsException    The passed index is out of bounds.
     */
    public EditorTab getTab(int i) throws IndexOutOfBoundsException{
        return tabs.get(i);
    }

    /**
     * Adds a new tab to the EditorTabbedPane.
     *
     * @param tab   Tab to be added to the EditorTabbedPane.
     */
    public void addTab(EditorTab tab) {
        tabs.add(tab);
    }


    /**
     * Returns the selected component.
     * @return
     */
    public EditorTab getActiveTab() {
        return (EditorTab)getSelectedComponent();
    }

}
