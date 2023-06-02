package backend.licenses;

import backend.files.csv.CSVFile;
import backend.files.csv.InvalidCSVException;

import java.io.IOException;
import java.util.ArrayList;


/**
 * This class implements a handle that can handle used software.
 *
 * @author  Christian-2003
 * @version 01 June 2023
 */
public class UsedSoftwareHandle {

    /**
     * Stores the used software that shall be handled.
     */
    private UsedSoftware[] usedSoftware;

    /**
     * Stores the CSV file that contains the used software.
     */
    private CSVFile<UsedSoftware> softwareArchive;


    /**
     * Constructs a new UsedSoftwareHandle that handles the passed ArrayList of used software.
     * This automatically loads the used licensed software from the respective file.
     */
    public UsedSoftwareHandle() throws InvalidCSVException {
        String[][] csvTable = new String[0][0];
        softwareArchive = new CSVFile<UsedSoftware>("licensedSoftware.csv");
        try {
            csvTable = softwareArchive.loadCSV();
        }
        catch (IOException e) {
            System.out.println("IOException caught:\n" + e.getMessage());
            return;
        }
        catch (InvalidCSVException e) {
            System.out.println("InvalidCSVException caught:\n" + e.getMessage());
            return;
        }
        catch (Exception e) {
            System.out.println("Exception caught:\n" + e.getMessage());
            return;
        }
        finally {
            if (csvTable == null || csvTable.length == 0) {
                usedSoftware = new UsedSoftware[0];
            }
        }

        //Generate the instances:
        usedSoftware = new UsedSoftware[csvTable.length - 1]; //Ignore the first line in the CSV table as it's a headline.
        for (int i = 0; i < usedSoftware.length; i++) {
            usedSoftware[i] = new UsedSoftware();
            try {
                usedSoftware[i].fromCSV(csvTable[i + 1]);
            }
            catch (InvalidCSVException e) {
                //Cannot read CSV:
                throw e;
            }
        }

        //Debug output:
        System.out.println("Used Software");
        for (int i = 0; i < usedSoftware.length; i++) {
            System.out.println(usedSoftware[i].toString());
        }
    }


    /**
     * Tests whether there is any used software handled.
     *
     * @return  Whether any used software is handled.
     */
    public boolean isEmpty() {
        if (usedSoftware.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of UsedSoftware that is handled by this instance.
     *
     * @return  Number of handled UsedSoftware-instances.
     */
    public int size() {
        return usedSoftware.length;
    }

    /**
     * Returns the used software at the specified index.
     *
     * @param i                             Index whose UsedSoftware shall be returned.
     * @return                              The UsedSoftware at the specified index.
     * @throws IndexOutOfBoundsException    The passed index does not exist.
     */
    public UsedSoftware get(int i) throws IndexOutOfBoundsException {
        return usedSoftware[i];
    }

}
