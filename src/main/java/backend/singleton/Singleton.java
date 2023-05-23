package backend.singleton;

import backend.config.Config;


/**
 * This singleton class contains all global variables.
 *
 * @author  Christian-2003
 * @version 12 May 2023
 */
public class Singleton {

    /**
     * Stores the instance of the config file.
     */
    public static Config CONFIG = new Config();

}
