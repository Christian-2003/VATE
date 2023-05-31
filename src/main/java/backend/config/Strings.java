package backend.config;


/**
 * Stores all Strings that are used by VATE.
 */
public class Strings {

    /**
     * Stores the String indicating a file extension in a {@linkplain frontend.frames.main.components.Subline}.
     */
    public static String FILE_EXTENSION = "File Extension: ";

    /**
     * Stores the String indicating a file's length in a {@linkplain frontend.frames.main.components.Subline}.
     */
    public static String FILE_LENGTH = "Length: ";

    /**
     * Stores the String indicating a file's line number in a {@linkplain frontend.frames.main.components.Subline}.
     */
    public static String FILE_LINES = "Lines: ";

    /**
     * Stores the String asking the user to save unsaved changes (i.e. in q {@linkplain frontend.frames.main.components.EditorTab}).
     */
    public static String ASK_FOR_CHANGES_TO_BE_SAVED = "Save changes?";

    /**
     * Stores the String for the menu item to close the active tab in a {@linkplain frontend.menus.TabPopupMenu}.
     */
    public static String CLOSE_TAB = "Close";

    /**
     * Stores the String for the menu item to save the active tab in a {@linkplain frontend.menus.TabPopupMenu}.
     */
    public static String SAVE_TAB = "Save";

    /**
     * Stores the String for the menu item to show the active tab in the explorer in a {@linkplain frontend.menus.TabPopupMenu}.
     */
    public static String SHOW_TAB_IN_EXPLORER = "Show in Explorer";

    /**
     * Stores the information message for when a file is already opened (i.e. in {@linkplain frontend.frames.main.components.TabbedPane}).
     */
    public static String FILE_ALREADY_OPENED = "File already opened.";

    /**
     * Stores the information message for when a newly created file already existed (i.e. in {@linkplain frontend.frames.main.MainFrame}).
     */
    public static String FILE_ALREADY_EXISTS = "The file already exists. Do you want to override the file?";

    /**
     * Stores the information message for when a file cannot be created (i.e. in {@linkplain frontend.frames.main.MainFrame}).
     */
    public static String COULD_NOT_CREATE_FILE = "Could not create file.";

    /**
     * Indicates that a file cannot be exported to the desired format in {@linkplain frontend.frames.main.components.TabbedPane}).
     */
    public static String FILE_NOT_SUITABLE_FOR_EXPORT = "File cannot be exported to the desired format.";

    /**
     * Stores the information message for when there are unsaved changes (i.e. in {@linkplain frontend.frames.main.MainFrame}).
     */
    public static String UNSAVED_CHANGES = "Save unsaved changes?";

    /**
     * Stores the information message for the file menu {@linkplain frontend.menus.FileMenu}.
     */
    public static String FILE_MENU = "File";

    /**
     * Stores the information message for the edit menu {@linkplain frontend.menus.EditMenu}.
     */
    public static String EDIT_MENU = "Edit";

    /**
     * Stores the information message for the menu to open a file {@linkplain frontend.menus.FileMenu}.
     */
    public static String OPEN_FILE = "Open";

    /**
     * Stores the information message for the menu to create a new file {@linkplain frontend.menus.FileMenu}.
     */
    public static String NEW_FILE = "New";

    /**
     * Stores the String to allow the user to create a new file in {@linkplain frontend.frames.main.MainFrame}.
     */
    public static String CREATE_FILE_BUTTON = "Create";

    /**
     * Stores the String for the menu item to save the file in a {@linkplain frontend.frames.main.MainFrame}.
     */
    public static String SAVE = "Save";

    /**
     * Stores the String for the menu item to save the file as in a {@linkplain frontend.frames.main.MainFrame}.
     */
    public static String SAVE_AS = "Save As";

    /**
     * Stores the String for the menu item to export a file to HTML in a {@linkplain frontend.frames.main.MainFrame}.
     */
    public static String EXPORT_TO_HTML = "Export to HTML";

    /**
     * Stores the String for the menu item to exit the frame in a {@linkplain frontend.frames.main.MainFrame}.
     */
    public static String EXIT = "Exit";

    /**
     * Stores the String for the menu item to search a regex in a {@linkplain frontend.menus.EditMenu}.
     */
    public static String SEARCH = "Search";

    /**
     * Stores the title for the dialog to configure the export to HTML in a {@linkplain frontend.dialogs.ExportToHtmlDialog}.
     */
    public static String EXPORT_TO_HTML_TITLE = "Configure Export to HTML";

    /**
     * Stores the text for the button to export content (e.g. in {@linkplain frontend.dialogs.ExportToHtmlDialog}).
     */
    public static String EXPORT_BUTTON = "Export";

    /**
     * Stores the text for the button to cancel an action (e.g. in {@linkplain frontend.dialogs.ExportToHtmlDialog}).
     */
    public static String CANCEL_BUTTON = "Cancel";

    /**
     * Stores the text for the button to browse through the file system (e.g. in {@linkplain frontend.dialogs.ExportToHtmlDialog}).
     */
    public static String BROWSE_BUTTON = "Cancel";

    /**
     * Stores the text for the button to close a dialog (e.g. in {@linkplain frontend.dialogs.SearchDialog}).
     */
    public static String CLOSE_BUTTON = "Close";

    /**
     * Stores the text for the button to highlight the previous match in a search (e.g. in {@linkplain frontend.dialogs.SearchDialog}).
     */
    public static String PREVIOUS_BUTTON = "Previous";

    /**
     * Stores the text for the button to highlight the next match in a search (e.g. in {@linkplain frontend.dialogs.SearchDialog}).
     */
    public static String NEXT_BUTTON = "Next";

    /**
     * Stores the text asking the user to enter a directory. (e.g. in {@linkplain frontend.dialogs.ExportToHtmlDialog}).
     */
    public static String ENTER_EXPORT_DIRECTORY = "Export Directory";

    /**
     * Stores the title for the search dialog {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String SEARCH_DIALOG_TITLE = "Search";

    /**
     * Asks the user to enter a regex to search in VATE in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String SEARCH_WHAT = "Search what:";

    /**
     * Asks the user to search the active tab for a regex in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String SEARCH_ACTIVE_TAB = "Search active tab";

    /**
     * Asks the user to search all tabs for a regex in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String SEARCH_ALL_TABS = "Search all tabs";

    /**
     * Allows the user to replace the searched contents with another String in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String enableSearchReplacement = "Enable Replacement";

    /**
     * Allows the user to replace the searched contents with another String in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String searchReplacementHint = "Replace with";

    /**
     * Allows the user to replace the searched contents with another String in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String replaceButton = "Replace";

    /**
     * Allows the user to replace all the searched contents with another String in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String replaceAllButton = "Replace All";

    /**
     * Allows the user to swap the replacement with the searched regex in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String swapReplaceAndRegexButton = "Swap Search and Replace";

    /**
     * Shows the user that there are no matches for a regex in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String NO_MATCHES = "No matches";

}
