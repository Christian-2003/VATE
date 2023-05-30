package backend.config;

import java.awt.*;
import java.util.ArrayList;

/**
 * Stores all settings for VATE.
 */
public class Settings {

    /**
     * Stores whether VATE shall automatically use the system's default LookAndFeel.
     */
    public static boolean USE_SYSTEM_LOOK_AND_FEEL = true;

    /**
     * Stores whether VATE shall automatically open all previous tabs when opened.
     */
    public static boolean LOAD_PREVIOUS_FILES_WHEN_OPENED = true;

    /**
     * Stores the paths to all previously opened files.
     */
    public static ArrayList<String> PREVIOUSLY_OPENED_FILES = new ArrayList<>();

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
