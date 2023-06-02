package backend.files.csv;


/**
 * Implements an exception that can be thrown whenever invalid CSV is encountered.
 *
 * @author  Christian-2003
 * @version 01 June 2023
 */
public class InvalidCSVException extends Exception {

    public InvalidCSVException() {
        super();
    }

    public InvalidCSVException(String message) {
        super(message);
    }

    public InvalidCSVException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCSVException(Throwable cause) {
        super(cause);
    }

}
