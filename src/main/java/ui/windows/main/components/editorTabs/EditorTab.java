package ui.windows.main.components.editorTabs;

import backend.singleton.Singleton;
import ui.windows.main.MainFrame;
import ui.windows.main.components.LineNumbers;
import ui.windows.main.components.Subline;
import ui.windows.main.components.TextArea;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class implements a tab for the main window. Each tab displays ta file's content, and its additional information.
 *
 * @author  Christian-2003
 * @version 23 May 2023
 */
public class EditorTab extends JPanel {

    /**
     * Stores the main window in which this editor tab is a part of.
     */
    protected MainFrame context;

    /**
     * Stores the subline which displays additional information about the file.
     */
    protected Subline subline;

    /**
     * Title of the EditorTab.
     */
    protected String title;


    /**
     * Constructs a new EditorTab with the passed context.
     *
     * @param context   MainWindow in which the constructed EditorTab is located.
     */
    public EditorTab(MainFrame context) {
        super();
        setLayout(new BorderLayout());
        this.context = context;
        title = "";

        init();
    }

    /**
     * Constructs a new EditorTab with the passed context and title
     *
     * @param context   MainWindow in which the constructed EditorTab is located.
     * @param title     Title for the EditorTab.
     */
    public EditorTab(MainFrame context, String title) {
        super();
        setLayout(new BorderLayout());
        this.context = context;
        this.title = title;

        init();
    }


    /**
     * Instantiates and configures the EditorTab.
     */
    private void init() {
        //Instantiate and configure the subline:
        subline = new Subline("none", 0, 1);
        add(subline, BorderLayout.SOUTH);
    }


    public Subline getSubline() {
        return subline;
    }

    public void setSubline(Subline subline) {
        this.subline = subline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
