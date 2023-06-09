import backend.config.Config;
import frontend.frames.main.MainFrame;

import java.io.IOException;


/**
 * This class resembles the starting point for VATE. It contains the main-method.
 *
 * @author  Christian-2003
 * @version 29 May 2023
 */
public class VATE {

    public static void main(String[] args) {
        try {
            Config.loadConfig();
        }
        catch (IOException e) {
            //Could not load config: Do nothing as new config is created...
        }

        MainFrame mainFrame = new MainFrame(args);
    }

}
