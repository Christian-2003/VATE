package ui.windows.main.components;

import javax.swing.*;
import java.awt.*;


/**
 * This class implements the subline for the main window which displays basic information about the currently opened file.
 *
 * @author  Christian-2003
 * @version 23 May 2023
 */
public class Subline extends JPanel {

    /**
     * TextPanel displays the file extension.
     */
    private JLabel fileExtension;

    /**
     * TextPanel displays the length of the file.
     */
    private JLabel fileLength;

    /**
     * TextPanel displays the number of lines in the file.
     */
    private JLabel fileLines;


    /**
     * Constructs a new Subline with the passed arguments.
     *
     * @param fileExtension File extension.
     * @param fileLength    File length.
     * @param fileLines     Number of lines.
     */
    public Subline(String fileExtension, int fileLength, int fileLines) {
        super();
        this.fileExtension = new JLabel(fileExtension);
        this.fileLength = new JLabel(String.valueOf(fileLength));
        this.fileLines = new JLabel(String.valueOf(fileLines));
        init();
    }


    public void setFileExtension(String fileExtension) {
        this.fileExtension.setText(fileExtension);
    }

    public String getFileExtension() {
        return fileExtension.getText();
    }

    public void setFileLength(int fileLength) {
        this.fileLength.setText(String.valueOf(fileLength));
    }

    public void setFileLines(int fileLines) {
        this.fileLines.setText((String.valueOf(fileLines)));
    }


    private void init() {
        add(new JLabel("File Extension:"));

        add(this.fileExtension);
        add(new JLabel(" | "));
        add(new JLabel("Length:"));

        add(this.fileLength);
        add(new JLabel(" | "));
        add(new JLabel("Lines:"));

        add(this.fileLines);
    }

}
