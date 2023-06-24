package backend.config;

import java.util.ArrayList;

/**
 * Stores all settings for VATE.
 */
public class Settings {

    /**
     * Stores whether VATE shall automatically use the system's default LookAndFeel.
     */
    public static boolean useSystemLookAndFeel = true;

    /**
     * Stores whether VATE shall automatically open all previous tabs when opened.
     */
    public static boolean loadPreviousFilesWhenOpened = true;

    /**
     * Stores the paths to all previously opened files.
     */
    public static ArrayList<String> previouslyOpenedFiles = new ArrayList<>();

    /**
     * Stores whether VATE shall be instantiated with the last dimensions.
     */
    public static boolean usePreviousDimensionWhenCreated = true;

    /**
     * Stores the last width of the main frame.
     */
    public static int previousWidth = -1;

    /**
     * Stores the last height of the main frame.
     */
    public static int previousHeight = -1;

}
