package ui.handles;


/**
 * This class implements a handle for the title of VATE's main window. Instances of this class do only generate a
 * suitable title, however the title still has to be manually assigned to the title of the respective JFrame.
 *
 * @author  Christian-2003
 * @version 10 May 2023
 */
public class MainWindowTitleHandle {

    /**
     * Stores the name for the application.
     */
    private String applicationName;

    /**
     * Stores the Name of the file, that is currently edited.
     */
    private String fileName;

    /**
     * Stores whether there are unsaved changes with this file.
     */
    private boolean unsavedChanges;

    /**
     * Stores the title that was generated based on the {@link #applicationName}, {@link #fileName} and
     * {@link #unsavedChanges}.
     */
    private String title;


    /**
     * Constructs a new MainWindowTitleHandle with the passed arguments as applicationName and fileName.
     *
     * @param applicationName   Name of the application.
     */
    public MainWindowTitleHandle(String applicationName) {
        this.applicationName = applicationName;
        fileName = "";
        unsavedChanges = false;
        updateTitle();
    }


    /**
     * Constructs a new MainWindowTitleHandle with the passed arguments as applicationName and fileName.
     *
     * @param applicationName   Name of the application.
     * @param fileName          Name of the file that is currently edited.
     */
    public MainWindowTitleHandle(String applicationName, String fileName) {
        this.applicationName = applicationName;
        this.fileName = fileName;
        unsavedChanges = false;
        updateTitle();
    }


    public String getTitle() {
        return title;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    public boolean hasUnsavedChanges(boolean unsavedChanges) {
        if (this.unsavedChanges != unsavedChanges) {
            //Only change (and update title), if there were no unsaved changes before:
            this.unsavedChanges = unsavedChanges;
            updateTitle();
        }
        return this.unsavedChanges;
    }


    /**
     * Updates the title based on the {@link #applicationName}, {@link #fileName} and {@link #unsavedChanges}. The
     * title will have the following format:
     *      <{@link #applicationName}>: <{@link #fileName}>(*)
     * @return
     */
    public String updateTitle() {
        StringBuilder titleBuilder = new StringBuilder();
        titleBuilder.append(applicationName);
        if (fileName != "") {
            //Append name of the file since file is currently edited:
            titleBuilder.append(": ");
            titleBuilder.append(fileName);
            if (unsavedChanges) {
                //Append '*' to indicate unsaved changes:
                titleBuilder.append("*");
            }
        }
        title = titleBuilder.toString();
        return title;
    }

}
