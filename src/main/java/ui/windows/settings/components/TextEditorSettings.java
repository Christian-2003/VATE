package ui.windows.settings.components;

import backend.singleton.Singleton;
import ui.windows.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This class implements a component that can configure the Text Editor of VATE.
 * This class is intended to be used with the Settings menu.
 *
 * @author  Christian-2003
 * @version 12 May 2023
 */
public class TextEditorSettings extends JPanel {

    private final MainFrame context;

    /**
     * Checkbox for showing or disabling the line numbers.
     */
    private JCheckBox showLineNumbers;

    /**
     * Allows the user to select a font for the text editor.
     */
    private JComboBox<String> font;


    /**
     * Constructs a new TextEditorSettings instance.
     */
    public TextEditorSettings(MainFrame context) {
        super();
        this.context = context;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Configure showLineNumbers:
        showLineNumbers = new JCheckBox("Show Line numbers", Singleton.CONFIG.showLineNumbers);
        showLineNumbers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.requestToggleLineNumbers(showLineNumbers.isSelected());
            }
        });
        add(showLineNumbers);

        //Configure font:
        JPanel fontContainer = new JPanel();
        fontContainer.add(new JLabel("Select font"));
        String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        font = new JComboBox<>(availableFonts);
        //Select currently used font:
        int selected = 0;
        for (int i = 0; i < availableFonts.length; i++) {
            if (availableFonts[i].equals(Singleton.CONFIG.textEditorFont)) {
                selected = i;
                break;
            }
        }
        font.setSelectedIndex(selected);
        font.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Singleton.CONFIG.textEditorFont = font.getSelectedItem().toString();
                context.requestTextEditorStyleUpdate();
            }
        });
        fontContainer.add(font);
        add(fontContainer);

    }

}
