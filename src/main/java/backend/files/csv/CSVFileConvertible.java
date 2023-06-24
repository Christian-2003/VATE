package backend.files.csv;


/**
 * This interface must be implemented by all classes that need to be able to be stored in CSV files.
 *
 * @author  Christian-2003
 * @version 01 June 2023
 */
public interface CSVFileConvertible {

    /**
     * This method must generate a String containing the CSV-representation of the instance. The CSV <b>MUST</b> be of
     * the following format:
     * <br><i>{@code "<attribute1>";"<attribute2>";"<attribute3>"...}</i><br>
     * Example: <i>{@code "John";"Doe";"25";"male"}</i>.
     *
     * @return  String containing the CSV representation of this instance.
     */
    public abstract String toCSV();

    /**
     * This method must instantiate the respective instance from a String-array containing the individual CSV cells.
     *
     * @param csv                   String-array containing the individual CSV cells.
     * @throws InvalidCSVException  The passed CSV is incorrect.
     */
    public abstract void fromCSV(String[] csv) throws InvalidCSVException;

}
