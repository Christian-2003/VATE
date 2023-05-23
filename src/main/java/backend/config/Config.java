package backend.config;

import java.awt.*;
import java.io.*;


/**
 * Contains all attributes that are required for the config file.
 * The values that are assigned to the attributes are the default variables in case there is no config file.
 *
 * @author  Christian-2003
 * @version 23 May 2023
 */
public class Config implements Serializable {

    /**
     * Path for the config file.
     */
    public final String configFilePath = "config.conf";

    /**
     * Default line separator to be used with the application.
     */
    public String lineSeparator = System.lineSeparator();

    /**
     * Stores the file that was opened when the editor was closed.
     */
    public String lastOpenedFile = "";

    /**
     * Stores the dimensions for the window the last time it was opened.
     */
    public Dimension lastWindowDimension;

    /**
     * Stores whether the line numbers shall be shown or not.
     */
    public boolean showLineNumbers = true;

    /**
     * Stores the font family for the font of the text editor.
     */
    public String textEditorFont = "Consolas";

    /**
     * Stores the font size for the text editor.
     */
    public int textSize = 16;

}
