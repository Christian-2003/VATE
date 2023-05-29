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

public class TextEditorTab extends EditorTab {

    /**
     * Stores the main text area in which the file's content is displayed.
     */
    private TextArea textArea;

    /**
     * Stores the line numbers for the main text area.
     */
    private LineNumbers lineNumbers;


    /**
     * Constructs a new TextEditorTab with the passed context.
     *
     * @param context   MainWindow in which the constructed TextEditorTab is located.
     */
    public TextEditorTab(MainFrame context) {
        super(context);

        init();
    }

    /**
     * Constructs a new TextEditorTab with the passed context and title
     *
     * @param context   MainWindow in which the constructed TextEditorTab is located.
     * @param title     Title for the TextEditorTab.
     */
    public TextEditorTab(MainFrame context, String title) {
        super(context, title);

        init();
    }


    /**
     * Instantiates and configures the TextEditorTab.
     */
    private void init() {
        //Instantiate textArea:
        textArea = new ui.windows.main.components.TextArea();
        textArea.setText("");

        //Instantiate lineNumbers:
        lineNumbers = new LineNumbers(textArea);

        //Configure textArea:
        textArea.getDocument().addDocumentListener(new DocumentListener() {

            /**
             * Stores the number of lines that existed when the document was previously updated.
             */
            private int previousLineNumbers = countLineSeparatorOccurrences();


            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
                updateSubline();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
                updateSubline();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
                updateSubline();
            }

            /**
             * Updates the {@linkplain Subline} of the EditorTab.
             */
            private void updateSubline() {
                subline.setFileLength(textArea.getDocument().getLength());
                subline.setFileLines(previousLineNumbers + 1);
            }

            /**
             * Updates the {@linkplain LineNumbers} of the textArea.
             */
            private void updateLineNumbers() {
                int currentLineNumbers = countLineSeparatorOccurrences();
                if (previousLineNumbers != currentLineNumbers) {
                    //New line numbers needed:
                    lineNumbers.updateLineNumbers();
                    previousLineNumbers = currentLineNumbers;
                }
            }

            /**
             * Counts the occurrences of the line separator, i.e. the number of lines.
             *
             * @return  Number of line separator occurrences.
             */
            private int countLineSeparatorOccurrences() {
                Pattern pattern = Pattern.compile("\n");
                Matcher matcher = pattern.matcher(textArea.getText());
                return (int)(matcher.results().count());
            }
        });
        textArea.setText("\n");
        textArea.setText("");

        //Configure lineNumbers:
        lineNumbers.setVisible(Singleton.CONFIG.showLineNumbers);

        //Instantiate and configure scrollPane which contains the textArea and lineNumbers:
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setRowHeaderView(lineNumbers);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }


    public TextArea getTextArea() {
        return textArea;
    }


    /**
     * Returns the text that is displayed within the EditorTab.
     *
     * @return  Text that is displayed within the EditorTab.
     */
    public String getText() {
        return textArea.getText();
    }

    /**
     * Changes the text of the textArea within this EditorTab to the passed String.
     *
     * @param s Text which shall be displayed on the textPane.
     */
    public void setText(String s) {
        textArea.setText(s);
    }


    /**
     * Requests the line numbers to be shown or hidden.
     * This method is intended to be called from the "Settings > Show Line Numbers"-menu.
     *
     * @param lineNumbers   Whether the line numbers shall be shown or not.
     */
    public void requestToggleLineNumbers(boolean lineNumbers) {
        if (lineNumbers) {
            //Show line numbers:
            this.lineNumbers.setVisible(true);
        }
        else {
            //Hide line numbers:
            this.lineNumbers.setVisible(false);
        }
    }

    /**
     * Requests an update to the style of the text editor.
     * This method is intended to be called from the Settings window.
     */
    public void requestTextEditorStyleUpdate() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        boolean fontAvailable = false;
        for (int i = 0; i < fonts.length; i++) {
            if (fonts[i].equals(Singleton.CONFIG.textEditorFont)) {
                fontAvailable = true;
                break;
            }
        }
        textArea.setFont(new Font(Singleton.CONFIG.textEditorFont, Font.PLAIN, Singleton.CONFIG.textSize));
        lineNumbers.setFont(new Font(Singleton.CONFIG.textEditorFont, Font.PLAIN, Singleton.CONFIG.textSize));
    }

}
