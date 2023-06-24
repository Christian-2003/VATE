package backend.files;

import backend.config.Config;
import java.io.*;


/**
 * This class implements a file. It contains all the necessary information to handle file operations and provides
 * useful functionalities.
 * Instances of this class are always fully consistent. No matter which attribute you change, all attributes will always
 * be correct, much like a Java Bean.
 * <br><b><i>IMPORTANT:</i> These instances do not store the actual content of the respective files!</b>
 *
 * @author  Christian-2003
 * @version 01 June 2023
 */
public class File {

    /**
     * Stores the full path to the file.
     */
    protected String fullPath;

    /**
     * Stores the path's segments.
     */
    protected String[] path;

    /**
     * Stores the name of the file. This name is generated through the {@link #fullPath}.
     */
    protected String name;

    /**
     * Stores the extension of the file. This extension is generated through {@link #fullPath}.
     */
    protected String extension;


    /**
     * Constructs a new file with the passed path.
     *
     * @param path  Path for the file.
     */
    public File(String path) {
        this.fullPath = path;
        generateFileProperties();
    }


    public String[] getPathParts() {
        return path;
    }

    public void setPathParts(String[] parts) {
        this.path = parts;
        generateFullPath();
    }

    public String getAbsolutePath() {
        return fullPath;
    }

    public void setAbsolutePath(String fullPath) {
        this.fullPath = fullPath;
        generateFileProperties(); //New file properties needed!
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        generateFullPath();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
        generateFullPath();
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
     * Returns the paths parts as a String. This is basically the absolute path without the name and extension.
     *
     * @return  Path's parts.
     */
    public String getPathPartsAsString() {
        StringBuilder pathBuilder = new StringBuilder();

        for (int i = 0; i < path.length; i++) {
            pathBuilder.append(path[i]);
            if (i < path.length - 2) {
                pathBuilder.append("\\\\");
            }
        }

        return pathBuilder.toString();
    }


    /**
     * This method loads the contents of this file and returns them as String. If the file does not exist, a
     * {@linkplain FileNotFoundException} will be thrown.
     *
     * @return                          Content of the loaded file.
     * @throws FileNotFoundException    This file does not exist.
     */
    public String load() throws FileNotFoundException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fullPath))) {
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
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fullPath))) {
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
        return new java.io.File(fullPath).exists();
    }


    /**
     * Generates the file's {@link #name} and {@link #extension} based on the {@link #fullPath}.
     * After the method is called, the generated properties will be stored within their respective attributes. If a
     * specific property could not be generated, the respective attribute will have the value {@code ""} (empty String).
     */
    private void generateFileProperties() {
        if (fullPath == null || fullPath.equals("")) {
            name = "";
            extension = "";
            fullPath = ""; //To make sure the path is not null!
        }

        String[] pathParts = fullPath.split("\\\\");
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

        path = new String[pathParts.length - 1]; //Exclude the name.
        for (int i = 0; i < path.length; i++) {
            path[i] = pathParts[i];
        }
    }

    /**
     * Generates a new full path based on the path parts, name and extension.
     */
    private void generateFullPath() {
        StringBuilder newFullPath = new StringBuilder();

        //Append file path:
        for (String current : path) {
            newFullPath.append(current);
            newFullPath.append("\\\\");
        }

        //Append name and extension:
        newFullPath.append(name);
        newFullPath.append(".");
        newFullPath.append(extension);

        fullPath = newFullPath.toString();
    }

}
