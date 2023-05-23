import backend.config.ConfigReader;
import ui.windows.MainWindow;

import java.io.IOException;

/**
 * This class implements the VATE (Very Advanced Text Editor).
 *
 * @author  Christian-2003
 * @version 10 May 2023
 */
public class VATE {

    public static void main(String[] args) {
        //Load the config file before anything happens:
        try {
            ConfigReader.LOAD_CONFIG();
        }
        catch (IOException e) {
            //Error: Could not open config file:
            System.err.println(e.getMessage());
        }

        if (args.length == 1) {
            //Load passed file name when launched:
            MainWindow window = new MainWindow(true, args[0]);
        }
        else {
            MainWindow window = new MainWindow(true, null);
        }

    }

}
