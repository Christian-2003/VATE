package backend.files;


import backend.config.Config;

import java.io.*;

/**
 * This class implements a resources file. It contains all the necessary information to handle resource file operations and provides
 * useful functionalities.
 * Instances of this class are always fully consistent. No matter which attribute you change, all attributes will always
 * be correct, much like a Java Bean.
 * <br><b><i>IMPORTANT:</i> These instances do not store the actual content of the respective resource files!</b>
 * <br><b><i>MORE IMPORTANT:</i> These instances can only READ resource files!</b>
 *
 * @author  Christian-2003
 * @version 01 June 2023
 */
public class ResourcesFile extends File {

    /**
     * Constructs a new resource file with the passed path.
     *
     * @param path  Path for the resource file.
     */
    public ResourcesFile(String path) {
        super(path);
    }


    /**
     * This method loads the contents of this resource file and returns them as String. If the resource file does not exist, a
     * {@linkplain FileNotFoundException} will be thrown.
     *
     * @return                          Content of the loaded resource file.
     * @throws FileNotFoundException    This resource file does not exist.
     */
    @Override
    public String load() throws FileNotFoundException {
        StringBuilder resourcesPathBuilder = new StringBuilder();
        resourcesPathBuilder.append("/");
        resourcesPathBuilder.append(getNameWithExtension());
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resourcesPathBuilder.toString())))) {
            StringBuilder fileContent = new StringBuilder();
            //Read the file line by line:
            while (fileReader.ready()) {
                fileContent.append(fileReader.readLine());
                fileContent.append(Config.formats.lineSeparator);
            }
            fileReader.close();
            return fileContent.toString();
        }
        catch (IOException e) {
            //Could not open the resources file:
            throw new FileNotFoundException(e.getMessage());
        }
    }

}
