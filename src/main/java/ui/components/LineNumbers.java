package ui.components;

import javax.swing.*;
import javax.swing.text.Element;
import java.awt.*;


/**
 * Displays line numbers for a JTextArea.
 *
 * @author  Christian-2003
 * @version 10 May 2023
 */
public class LineNumbers extends JTextArea {

    /**
     * Stores the textArea which contains the text for which these line numbers shall be generated.
     */
    private TextArea textArea;


    /**
     * Constructs a new LineNumbers instance.
     *
     * @param textArea  TextArea for which the line numbers shall be displayed.
     */
    public LineNumbers(TextArea textArea) {
        super();
        this.textArea = textArea;
        setEditable(false);
        setBackground(Color.LIGHT_GRAY);
        setForeground(Color.GRAY);
    }


    /**
     * Updates the line numbers.
     */
    public void updateLineNumbers() {
        setText(generateLineNumbers());
    }


    /**
     * Generates the line numbers as a String.
     *
     * @return  Line numbers as a String.
     */
    private String generateLineNumbers() {
        int caretPosition = textArea.getDocument().getLength();
        Element root = textArea.getDocument().getDefaultRootElement();
        StringBuilder lineNumbersBuilder = new StringBuilder();
        for (int i = 1; i < root.getElementIndex(caretPosition) + 2; i++) {
            lineNumbersBuilder.append(i);
            lineNumbersBuilder.append(System.lineSeparator());
        }
        return lineNumbersBuilder.toString();
    }

}
