package backend.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * Stores all relevant configurable information for VATE.
 *
 * @author  Christian-2003
 * @version 25 May 2023
 */
public class Config {

    /**
     * Stores the filepath for the config.
     */
    private static String configFilePath = "vate.config";

    /**
     * Stores all colors for VATE.
     * (i.e. background colors, text colors, ...)
     */
    public static Colors colors = new Colors();

    /**
     * Stores all formats for VATE.
     * (i.e. line separators, date formats, ...)
     */
    public static Formats formats = new Formats();

    /**
     * Stores all Strings for VATE.
     * (i.e. "Open", "Save", "Cancel", "No file type", ...)
     */
    public static Strings strings = new Strings();

    /**
     * Stores all Font-related information for VATE.
     * (i.e. Font families, font sizes, ...)
     */
    public static Fonts fonts = new Fonts();

    /**
     * Stores all settings for VATE.
     * (i.e. whether the default LookAndFeel shall be used, ...)
     */
    public static Settings settings = new Settings();


    /**
     * Saves the config to a configuration file.
     *
     * @throws IOException  Some error with the config file.
     */
    public static void saveConfig() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFilePath))) {
            GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
            Gson gson = builder.create();
            //writer.write(gson.toJson(COLORS, Colors.class));
            //writer.write("\n");
            writer.write(gson.toJson(formats));
            writer.write("\n");
            writer.write(gson.toJson(fonts, Fonts.class));
            writer.write("\n");
            writer.write(gson.toJson(settings, Settings.class));
        }
        catch (IOException e) {
            //Error: Some IO Error occurred:
            throw e;
        }
    }

    /**
     * Loads the config from a configuration file. If no configuration file exists or cannot be loaded, a new one
     * is created containing default values.
     *
     * @throws IOException  Some error with the config file.
     */
    public static void loadConfig() throws IOException {
        if (!Files.exists(Paths.get(configFilePath))) {
            //Config file does not exist yet:
            saveConfig(); //Create a new config file.
            return;
        }

        //Read config file if available:
        ArrayList<String> lines = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) {
            while (reader.ready()) {
                lines.add(reader.readLine());
            }
        }
        catch (IOException e) {
            //Error: Some IO Error occurred:
            saveConfig(); //Save config with default values.
            return;
        }

        //Extract data from Json:
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
        Gson gson = builder.create();
        try {
            for (int i = 0; i < lines.size(); i++) {
                switch (i) {
                    case 0:
                        formats = gson.fromJson(lines.get(i), Formats.class);
                        break;
                    case 1:
                        fonts = gson.fromJson(lines.get(i), Fonts.class);
                        break;
                    case 2:
                        settings = gson.fromJson(lines.get(i), Settings.class);
                        break;
                    default:
                        return;
                }
            }
        }
        catch (JsonSyntaxException e) {
            //Error: Incorrect JSON:
            saveConfig(); //Create a new config.
        }
    }

}
