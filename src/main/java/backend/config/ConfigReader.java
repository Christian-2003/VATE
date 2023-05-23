package backend.config;

import backend.singleton.Singleton;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Reads the config file and stores it within the instance of the {@linkplain Config} within the Singleton class.
 *
 * @author  Christian-2003
 * @version 12 May 2023
 */
public class ConfigReader {

    /**
     * Reads the config file and stores it within the instance of the {@linkplain Config} within the Singleton class.
     *
     * @throws IOException  Somme IO error occurred.
     */
    public static void LOAD_CONFIG() throws IOException {
        if (!Files.exists(Paths.get(Singleton.CONFIG.configFilePath))) {
            //Config file does not exist yet:
            ConfigWriter.SAVE_CONFIG(); //Create a new config file.
            return;
        }
        //Read config file if available:
        try (BufferedReader reader = new BufferedReader(new FileReader(Singleton.CONFIG.configFilePath))) {
            Gson gson = new Gson();
            StringBuilder content = new StringBuilder();
            while (reader.ready()) {
                content.append(reader.readLine());
            }
            Singleton.CONFIG = gson.fromJson(content.toString(), Config.class);
        }
        catch (IOException e) {
            //Error: Some IO Error occurred:
            throw e;
        }
    }

}
