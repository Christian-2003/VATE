package frontend.frames.main.components;

import backend.config.Config;

import javax.swing.*;


/**
 * This class implements a Subline that displays basic information about a file within a {@linkplain Tab}. This class
 * shall be extended to create an individual subline for each Tab-type. However, extending this class may not always
 * be necessary.
 *
 * @author  Christian-2003
 * @version 25 May 2023
 */
public class Subline extends JPanel {

    /**
     * Stores the extension of the file.
     */
    private String extension;

    /**
     * Stores the number of lines of the file.
     */
    private int lines;

    /**
     * Stores the length of the file.
     */
    private int length;

    /**
     * Stores the JLabel displaying info about the file extension.
     */
    private JLabel extensionLabel;

    /**
     * Stores the JLabel displaying info about the file's line number.
     */
    private JLabel linesLabel;

    /**
     * Stores the JLabel displaying info about the file's length.
     */
    private JLabel lengthLabel;


    /**
     * Constructs a new Subline with the passed arguments as it's displayed information.
     *
     * @param extension Extension of the file.
     * @param lines     Number of lines of the file.
     * @param length    Length of the file.
     */
    public Subline(String extension, int lines, int length) {
        extensionLabel = new JLabel();
        linesLabel = new JLabel();
        lengthLabel = new JLabel();

        setExtension(extension);
        setLines(lines);
        setLength(length);

        //Configure the subline:
        add(new JLabel(Config.strings.FILE_EXTENSION));
        add(extensionLabel);
        add(new JLabel("  |  "));
        add(new JLabel(Config.strings.FILE_LENGTH));
        add(lengthLabel);
        add(new JLabel("  |  "));
        add(new JLabel(Config.strings.FILE_LINES));
        add(linesLabel);
    }


    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
        extensionLabel.setText(this.extension);
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
        linesLabel.setText("" + this.lines);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
        lengthLabel.setText("" + length);
    }

}
