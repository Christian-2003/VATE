package frontend.frames.main.components;

import backend.config.Config;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class implements an editable text area with line numbers that automatically scroll when needed.
 * Instances of this class are intended to be used as the main text editor for VATE.
 *
 * @author  Christian-2003
 * @version 25 May 2023
 */
public class TextArea extends JScrollPane {

    /**
     * This class implements the line numbers for the {@linkplain TextArea}-class.
     *
     * @author  Christian-2003
     * @version 25 May 2023
     */
    private class LineNumbers extends JTextPane {

        /**
         * Constructs new line numbers for the text editor.
         */
        public LineNumbers() {
            super();
            setEditable(false);
<<<<<<< Updated upstream
            setBackground(Config.colors.LINE_NUMBERS_BACKGROUND);
            setForeground(Config.colors.LINE_NUMBERS_FOREGROUND);
=======
            //setBackground(Config.colors.lineNumbersBackground);
            //setForeground(Config.colors.lineNumbersForeground);
>>>>>>> Stashed changes
        }


        /**
         * Updates the line numbers of the component.
         */
        public void updateLineNumbers() {
            //Generate the line numbers for the component:
            Document document = TextArea.this.textPane.getDocument();
            int documentLength = document.getLength();
            Element root = document.getDefaultRootElement();
            StringBuilder lineNumbers = new StringBuilder();
            for (int i = 1; i < root.getElementIndex(documentLength) + 2; i++) {
                lineNumbers.append(i);
                lineNumbers.append(Config.formats.LINE_SEPARATOR);
            }

            //Set the component's text to the generated line numbers:
            setText(lineNumbers.toString());
        }

    }


    /**
     * This class implements the main text editor for the {@linkplain TextArea}-class.
     *
     * @author  Christian-2003
     * @version 25 May 2023
     */
    private class TextPane extends JTextPane {

        /**
         * This Java class implements a handler that handles undoable changes.
         */
        private class UndoHandler implements UndoableEditListener {

            /**
             * Messaged when the document has created an edit, the edit is added to the
             * {@code unoManager}.
             *
             * @param e an {@code UndoableEditEvent} object
             */
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
                undoAction.update();
                redoAction.update();
            }

        }

        private class UndoAction extends AbstractAction {

            public UndoAction() {
                super("Undo");
                setEnabled(false);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.undo();
                }
                catch (CannotUndoException exc) {
                    //Do nothing...
                }
                update();
                redoAction.update();
            }

            public void update() {
                if (undoManager.canUndo()) {
                    setEnabled(true);
                    putValue(Action.NAME, undoManager.getUndoPresentationName());
                }
                else {
                    setEnabled(false);
                    putValue(Action.NAME, "Undo");
                }
            }
        }

        private class RedoAction extends AbstractAction {
            public RedoAction() {
                super("Redo");
                setEnabled(false);
            }

            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.redo();
                }
                catch (CannotRedoException exc) {
                    //Do nothing...
                }
                update();
                undoAction.update();
            }

            public void update() {
                if (undoManager.canRedo()) {
                    setEnabled(true);
                    putValue(Action.NAME, undoManager.getRedoPresentationName());
                }
                else {
                    setEnabled(false);
                    putValue(Action.NAME, "Redo");
                }
            }
        }


        /**
         * Stores the number of lines within this TextPane.
         */
        private int lineNumbers;

        private UndoManager undoManager;

        private UndoHandler undoHandler;

        private UndoAction undoAction;

        private RedoAction redoAction;


        /**
         * Constructs a new TextPane.
         */
        public TextPane() {
            super();

            lineNumbers = countLineSeparators();
            undoAction = new UndoAction();
            redoAction = new RedoAction();
            undoHandler = new UndoHandler();
            undoManager = new UndoManager();

<<<<<<< Updated upstream
            setBackground(Config.colors.TEXT_EDITOR_BACKGROUND);
            setForeground(Config.colors.TEXT_EDITOR_FOREGROUND);
=======
            //setBackground(Config.colors.textEditorBackground);
            //setForeground(Config.colors.textEditorForeground);
>>>>>>> Stashed changes

            //Add a document listener that can change the line numbers when needed:
            setText("");
            getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateLineNumbers();
                    updateContext();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateLineNumbers();
                    updateContext();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateLineNumbers();
                    updateContext();
                }
            });

            //Add undoEditListener:
            getDocument().addUndoableEditListener(undoHandler);
        }


        public void undo() {
            undoAction.actionPerformed(null);
        }

        public void redo() {
            redoAction.actionPerformed(null);
        }


        /**
         * Updates the context of the TextArea.
         */
        private void updateContext() {
            TextArea.this.context.updateLength(getDocument().getLength());
            TextArea.this.context.updateLineNumbers(lineNumbers + 1);
            TextArea.this.context.unsavedChanges = true;
        }


        /**
         * Updates the line numbers of the TextPane.
         */
        private void updateLineNumbers() {
            int newLineNumbers = countLineSeparators();
            if (lineNumbers != newLineNumbers) {
                //Updated line numbers needed:
                if (TextArea.this.lineNumbers != null) {
                    TextArea.this.lineNumbers.updateLineNumbers();
                }
                lineNumbers = newLineNumbers;
            }
        }

        /**
         * Counts the number of line separators that occur within the displayed text.
         *
         * @return  Number of line separators.
         */
        private int countLineSeparators() {
            Pattern pattern = Pattern.compile(Config.formats.LINE_SEPARATOR);
            Matcher matcher = pattern.matcher(getText());
            return (int)(matcher.results().count());
        }

    }


    /**
     * Stores the editable text pane that can actually be edited.
     */
    private TextPane textPane;

    /**
     * Stores the line numbers for the textArea.
     */
    private LineNumbers lineNumbers;

    /**
     * Context (the {@linkplain Tab}) in which the TextArea is located.
     */
    private Tab context;


    /**
     * Constructs a new TextArea which contains an editable text pane and line numbers that automatically update.
     * Furthermore, the entire TextArea automatically scrolls when needed.
     *
     * @param context   Tab in which this TextArea is located.
     */
    public TextArea(Tab context) {
        this.context = context;
        textPane = new TextPane();
        lineNumbers = new LineNumbers();

        updateFont();

        setText(Config.formats.LINE_SEPARATOR);
        setText("");

        setViewportView(textPane);
        setRowHeaderView(lineNumbers);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }


    /**
     * Changes the text of this TextArea to the passed argument.
     *
     * @param s New text for this TextArea.
     */
    public void setText(String s) {
        textPane.setText(s);
    }

    /**
     * Returns the text of this TextArea.
     *
     * @return  Text of this TextArea.
     */
    public String getText() {
        return textPane.getText();
    }


    /**
     * Returns the number of lines of the edited text.
     *
     * @return  Number of lines of the edited text.
     */
    public int getLineNumbers() {
        return textPane.lineNumbers;
    }


    /**
     * Returns the length (number of characters) of the edited text.
     *
     * @return  Length of the edited text.
     */
    public int getLength() {
        return textPane.getDocument().getLength();
    }


    /**
     * Updates the font of the TextArea to the attributes (Font-family and size) that are stored within the config.
     */
    public void updateFont() {
        Font font = new Font(Config.fonts.TEXT_EDITOR_FONT, Font.PLAIN, Config.fonts.TEXT_EDITOR_FONT_SIZE);
        lineNumbers.setFont(font);
        textPane.setFont(font);
    }


    /**
     * Moves the cursor to the passed position. If the passed position is out of bounds, nothing happens.
     *
     * @param position  Position to which the cursor shall be moved.
     * @return          Whether the cursor could be moved.
     */
    public boolean moveCursor(int position) {
        if (position >= 0 && textPane.getDocument().getLength() > position) {
            textPane.setCaretPosition(position);
            return true;
        }
        return false;
    }

    /**
     * Marks text at the specified position for the specified length.
     *
     * @param position  Index of the first character to be marked.
     * @param length    Number of characters to be marked.
     * @return          Whether the text could be marked.
     */
    public boolean markText(int position, int length) {
        if (moveCursor(position) && textPane.getDocument().getLength() > position + length) {
            textPane.moveCaretPosition(position + length);
            return true;
        }
        return false;
    }

    /**
     * Replaces part of the displayed text with the passed replacement.
     * Beginning at the passed index position, the specified number of characters (passed as length) will be removed.
     * In their place, the replacement is then inserted.
     *
     * @param position      Position at which the replacement shall be inserted.
     * @param length        Number of characters to be removed.
     * @param replacement   Replacement to be inserted.
     */
    public void replace(int position, int length, String replacement) {
        int currentCaretPosition = textPane.getCaretPosition();
        try {
            textPane.getDocument().remove(position, length);
            textPane.getDocument().insertString(position, replacement, null);
        }
        catch (BadLocationException e) {
            //Could not remove the text:
            return;
        }

    }


    /**
     * Undoes changes made to the TextArea.
     */
    public void undo() {
        textPane.undo();
    }

    /**
     * Redoes changes to the TextArea that were undone.
     */
    public void redo() {
        textPane.redo();
    }

}
