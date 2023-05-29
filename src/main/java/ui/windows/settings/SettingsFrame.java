package ui.windows.settings;

import ui.windows.settings.components.TextEditorSettings;
import ui.windows.main.MainFrame;

import javax.swing.*;
import java.awt.*;


/**
 * This class implements a window that allows the user to configure the program.
 *
 * @author  Christian-2003
 * @version 12 May 2023
 */
public class SettingsFrame extends JFrame {

    /**
     * Stores the options for the settings.
     */
    private JList<String> options;

    private final String[] settingsOptions = {"Window", "Text Editor"};


    public SettingsFrame(MainFrame context) {
        super("Options");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)(displaySize.width / 2), (int)(displaySize.height / 2));

        options = new JList<String>(settingsOptions);
        options.setPreferredSize(new Dimension((int)((displaySize.width / 2) / 5), (int)(displaySize.height / 2)));
        options.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(options, BorderLayout.WEST);
        add(new TextEditorSettings(context), BorderLayout.CENTER);

        setVisible(true);
    }




}
