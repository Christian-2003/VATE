package backend.files;

import backend.config.Config;
import java.io.*;


/**
 * This class implements a file. It contains all the necessary information to handle file operations and provides
 * useful functionalities.
 * <br><b>IMPORTANT: These instances do not store the actual content of the respective files!</b>
 *
 * @author  Christian-2003
 * @version 25 May 2023
 */
public class File {

    /**
     * Stores the path to the file.
     */
    private String path;

    /**
     * Stores the name of the file. This name is generated through the {@link #path}.
     */
    private String name;

    /**
     * Stores the extension of the file. This extension is generated through {@link #path}.
     */
    private String extension;


    /**
     * Constructs a new file with the passed path.
     *
     * @param path  Path for the file.
     */
    public File(String path) {
        this.path = path;
        generateFileProperties();
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        generateFileProperties(); //New file properties needed!
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    /**
     * Returns the file's name with its extension in the following format:
     *  "<name>.<extension>"
     *
     * @return  Name with the extension.
     */
    public String getNameWithExtension() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(".");
        builder.append(extension);
        return builder.toString();
    }


    /**
     * This method loads the contents of this file and returns them as String. If the file does not exist, a
     * {@linkplain FileNotFoundException} will be thrown.
     *
     * @return                          Content of the loaded file.
     * @throws FileNotFoundException    This file does not exist.
     */
    public String load() throws FileNotFoundException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {
            StringBuilder fileContent = new StringBuilder();
            //Read the file line by line:
            while (fileReader.ready()) {
                fileContent.append(fileReader.readLine());
                fileContent.append(Config.formats.LINE_SEPARATOR);
            }
            fileReader.close();
            return fileContent.toString();
        }
        catch (IOException e) {
            //Could not open the file:
            throw new FileNotFoundException(e.getMessage());
        }
    }


    /**
     * Saves the passed String to this file. If some error occurs, an IOException will be thrown.
     *
     * @param s             Content that shall be saved to this file.
     * @throws IOException  Something went wrong.
     */
    public void save(String s) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path))) {
            //Write the file:
            fileWriter.write(s);
            fileWriter.close();
        }
        catch (IOException e) {
            //Could not save file:
            throw e;
        }
    }


    /**
     * Tests whether this file exists. This method uses the {@code exists(String)}-method of the
     * {@linkplain java.io.File}-class to determine whether this file exists.
     *
     * @return  Whether this file exists.
     */
    public boolean exists() {
        return new java.io.File(path).exists();
    }


    /**
     * Generates the file's {@link #name} and {@link #extension} based on the {@link #path}.
     * After the method is called, the generated properties will be stored within their respective attributes. If a
     * specific property could not be generated, the respective attribute will have the value {@code ""} (empty String).
     */
    private void generateFileProperties() {
        if (path == null || path.equals("")) {
            name = "";
            extension = "";
            path = ""; //To make sure the path is not null!
        }

        String[] pathParts = path.split("\\\\");
        String[] nameParts = pathParts[pathParts.length - 1].split("\\.");

        if (nameParts.length >= 2) {
            //Set extension:
            extension = nameParts[nameParts.length - 1];
            //Set name:
            name = "";
            for (int i = 0; i < nameParts.length - 1; i++) {
                name += nameParts[i];
                if (i < nameParts.length - 2) {
                    name += ".";
                }
            }
        }
        else if (nameParts.length == 1) {
            //Only set name, as there is no file extension available:
            name = nameParts[0];
            extension = "";
        }
        else {
            //No name and extension available:
            extension = "";
            name = "";
        }
    }

}
