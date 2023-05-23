package backend.config;

import backend.singleton.Singleton;
import com.google.gson.Gson;
import java.io.*;


/**
 * Saves the config file from instance of the {@linkplain Config} within the Singleton class.
 *
 * @author  Christian-2003
 * @version 12 May 2023
 */
public class ConfigWriter {

    /**
     * Saves the config file from instance of the {@linkplain Config} within the Singleton class.
     *
     * @throws IOException  Some error with the config file occurred.
     */
    public static void SAVE_CONFIG() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Singleton.CONFIG.configFilePath))) {
            Gson gson = new Gson();
            writer.write(gson.toJson(Singleton.CONFIG, Config.class));
        }
        catch (IOException e) {
            //Error: Some IO Error occurred:
            throw e;
        }
    }

}
