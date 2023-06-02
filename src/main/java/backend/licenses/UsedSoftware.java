package backend.licenses;

import backend.files.csv.CSVFileConvertible;
import backend.files.csv.InvalidCSVException;
import java.util.Objects;


/**
 * Instances of this class can handle a software that was used within VATE.
 * This is done for legal purposes, as VATE has to communicate other used software and their licenses.
 *
 * @author  Christian-2003
 * @version 01 June 2023
 */
public class UsedSoftware implements Comparable<UsedSoftware>, CSVFileConvertible {

    /**
     * Stores the name of the software.
     */
    private String name;

    /**
     * Stores a short description to the software.
     */
    private String description;

    /**
     * Stores the version number of the software.
     */
    private String version;

    /**
     * Stores the name of the license under which the used software was licensed.
     */
    private String license;

    /**
     * Stores a link to the software.
     */
    private String linkToSoftware;

    /**
     * Stores a link to the software's license.
     */
    private String linkToLicense;


    /**
     * Default constructor instantiates a new empty UsedSoftware-instance.
     * <b>It is highly advised to NOT use this constructor</b>, but it is needed to construct new instances of this class
     * from a {@linkplain backend.files.csv.CSVFile<UsedSoftware>} instance.
     */
    public UsedSoftware() {
        name = "";
        description = "";
        version = "";
        license = "";
        linkToSoftware = "";
        linkToLicense = "";
    }


    /**
     * Constructs a new UsedSoftware-instance with the passed arguments.
     *
     * @param name              Name of the used software.
     * @param description       Description of the used software.
     * @param version           Version number of the used software.
     * @param linkToSoftware    Link to the software's website.
     * @param license           Name of the license under which the used software is licensed.
     * @param linkToLicense     Link to the software's license.
     */
    public UsedSoftware(String name, String description, String version, String linkToSoftware, String license, String linkToLicense) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.linkToSoftware = linkToSoftware;
        this.license = license;
        this.linkToLicense = linkToLicense;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLinkToSoftware() {
        return linkToSoftware;
    }

    public void setLinkToSoftware(String linkToSoftware) {
        this.linkToSoftware = linkToSoftware;
    }

    public String getLinkToLicense() {
        return linkToLicense;
    }

    public void setLinkToLicense(String linkToLicense) {
        this.linkToLicense = linkToLicense;
    }


    /**
     * Compares the name of this instance with the name of the passed instance lexicographically, using
     * {@code String.compareTo(String)}.
     *
     * @param usedSoftware  the object to be compared.
     * @return              Lexicographical order of the used software.
     */
    @Override
    public int compareTo(UsedSoftware usedSoftware) {
        return name.compareTo(usedSoftware.getName());
    }

    /**
     * Tests whether all attributes of both instances are identical.
     *
     * @param obj   Used software to compare to this instance.
     * @return      Whether both instances are identical.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UsedSoftware) {
            UsedSoftware usedSoftware = (UsedSoftware)obj;
            if (name.equals(usedSoftware.getName())) {
                if (description.equals(usedSoftware.getDescription())) {
                    if (version.equals(usedSoftware.getVersion())) {
                        if (linkToSoftware.equals(usedSoftware.getLinkToSoftware())) {
                            if (license.equals(usedSoftware.getLicense())) {
                                if (linkToLicense.equals(usedSoftware.getLinkToLicense())) {
                                    //All attributes match:
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        //One or more attribute(s) do not match:
        return false;
    }

    /**
     * Generates a unique hash for this instance.
     *
     * @return  Hash for this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description, version, linkToSoftware, license, linkToLicense);
    }

    /**
     * Generates a String representing this instance in CSV-format:
     * <i>{@code "<name>","<description>","<version>","<linkToSoftware>","<license>","<linkToLicense>"}</i>.
     *
     * @return  String containing the CSV representation of this instance.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("\"");
        builder.append(name);
        builder.append("\";\"");
        builder.append(description);
        builder.append("\";\"");
        builder.append(version);
        builder.append("\";\"");
        builder.append(linkToSoftware);
        builder.append("\";\"");
        builder.append(license);
        builder.append("\";\"");
        builder.append(linkToLicense);
        builder.append("\"");

        return builder.toString();
    }

    /**
     * This method generates a String representing this instance in CSV-format. The output is identical to the output
     * of this class' {@link #toString()}-method.
     *
     * @return  String containing the CSV representation of this instance.
     * @see     #toString()
     */
    @Override
    public String toCSV() {
        return toString();
    }

    /**
     * This method applies the CSV cells to this instance's attributes.
     *
     * @param csv                   String-array containing the CSV cells that shall be applied to this instance.
     * @throws InvalidCSVException  The passed CSV is incorrect.
     */
    @Override
    public void fromCSV(String[] csv) throws InvalidCSVException {
        if (csv.length != 6) {
            //The number of required CSV cells is incorrect:
            throw new InvalidCSVException("Incorrect number of CSV cells (" + csv.length + "/6) for class '" + getClass().getSimpleName() + "' of type UsedSoftware.");
        }
        setName(csv[0]);
        setDescription(csv[1]);
        setVersion(csv[2]);
        setLinkToSoftware(csv[3]);
        setLicense(csv[4]);
        setLinkToLicense(csv[5]);
    }

}
