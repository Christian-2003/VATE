package backend.config;


/**
 * Stores all Strings that are used by VATE.
 */
public class Strings {

    /**
     * Stores the abbreviated name for VATE.
     */
    public static String vate  = "VATE";

    /**
     * Stores the full name for VATE.
     */
    public static String vateFullName = "Very Advanced Text Editor";


    /**
     * Stores the String indicating a file extension in a {@linkplain frontend.frames.main.components.Subline}.
     */
    public static String fileExtension = "File Extension: ";

    /**
     * Stores the String indicating a file's length in a {@linkplain frontend.frames.main.components.Subline}.
     */
    public static String fileLength = "Length: ";

    /**
     * Stores the String indicating a file's line number in a {@linkplain frontend.frames.main.components.Subline}.
     */
    public static String fileLines = "Lines: ";

    /**
     * Stores the String asking the user to save unsaved changes (i.e. in q {@linkplain frontend.frames.main.components.EditorTab}).
     */
    public static String askForChangesToBeSaved = "Save changes?";

    /**
     * Stores the String for the menu item to close the active tab in a {@linkplain frontend.menus.TabPopupMenu}.
     */
    public static String closeTab = "Close";

    /**
     * Stores the String for the menu item to save the active tab in a {@linkplain frontend.menus.TabPopupMenu}.
     */
    public static String saveTab = "Save";

    /**
     * Stores the String for the menu item to show the active tab in the explorer in a {@linkplain frontend.menus.TabPopupMenu}.
     */
    public static String showTabInExplorer = "Show in Explorer";

    /**
     * Stores the information message for when a file is already opened (i.e. in {@linkplain frontend.frames.main.components.TabbedPane}).
     */
    public static String fileAlreadyOpened = "File already opened.";

    /**
     * Stores the information message for when a newly created file already existed (i.e. in {@linkplain frontend.frames.main.MainFrame}).
     */
    public static String fileAlreadyExists = "The file already exists. Do you want to override the file?";

    /**
     * Stores the information message for when a file cannot be created (i.e. in {@linkplain frontend.frames.main.MainFrame}).
     */
    public static String couldNotCreateFile = "Could not create file.";

    /**
     * Indicates that a file cannot be exported to the desired format in {@linkplain frontend.frames.main.components.TabbedPane}).
     */
    public static String fileNotSuitableForExport = "File cannot be exported to the desired format.";

    /**
     * Stores the information message for when there are unsaved changes (i.e. in {@linkplain frontend.frames.main.MainFrame}).
     */
    public static String unsavedChanges = "Save unsaved changes?";

    /**
     * Stores the information message for the file menu {@linkplain frontend.menus.FileMenu}.
     */
    public static String fileMenu = "File";

    /**
     * Stores the information message for the edit menu {@linkplain frontend.menus.EditMenu}.
     */
    public static String editMenu = "Edit";

    /**
     * Stores the information message for the edit menu {@linkplain frontend.menus.HelpMenu}.
     */
    public static String helpMenu = "Help";

    /**
     * Stores the information message for the menu to open a file {@linkplain frontend.menus.FileMenu}.
     */
    public static String openFile = "Open";

    /**
     * Stores the information message for the menu to create a new file {@linkplain frontend.menus.FileMenu}.
     */
    public static String newFile = "New";

    /**
     * Stores the String to allow the user to create a new file in {@linkplain frontend.frames.main.MainFrame}.
     */
    public static String createFileButton = "Create";

    /**
     * Stores the String for the menu item to save the file in a {@linkplain frontend.frames.main.MainFrame}.
     */
    public static String save = "Save";

    /**
     * Stores the String for the menu item to save the file as in a {@linkplain frontend.frames.main.MainFrame}.
     */
    public static String saveAs = "Save As";

    /**
     * Stores the String for the menu item to export a file to HTML in a {@linkplain frontend.frames.main.MainFrame}.
     */
    public static String exportToHtml = "Export to HTML";

    /**
     * Stores the String for the menu item to exit the frame in a {@linkplain frontend.frames.main.MainFrame}.
     */
    public static String exit = "Exit";

    /**
     * Stores the String for the menu item to search a regex in a {@linkplain frontend.menus.EditMenu}.
     */
    public static String search = "Search";

    /**
     * Stores the String for the menu item to search and replace a regex in a {@linkplain frontend.menus.EditMenu}.
     */
    public static String searchAndReplace = "Search and Replace";

    /**
     * Stores the String that allows the user to undo changes.
     */
    public static String undo = "Undo";

    /**
     * Stores the String that allows the user to redo undone changes.
     */
    public static String redo = "Redo";

    /**
     * Stores the title for the dialog to configure the export to HTML in a {@linkplain frontend.dialogs.ExportToHtmlDialog}.
     */
    public static String exportToHtmlTitle = "Configure Export to HTML";

    /**
     * Stores the text for the button to export content (e.g. in {@linkplain frontend.dialogs.ExportToHtmlDialog}).
     */
    public static String exportButton = "Export";

    /**
     * Stores the text for the button to cancel an action (e.g. in {@linkplain frontend.dialogs.ExportToHtmlDialog}).
     */
    public static String cancelButton = "Cancel";

    /**
     * Stores the text for the button to browse through the file system (e.g. in {@linkplain frontend.dialogs.ExportToHtmlDialog}).
     */
    public static String browseButton = "Browse";

    /**
     * Stores the text for the button to close a dialog (e.g. in {@linkplain frontend.dialogs.SearchDialog}).
     */
    public static String closeButton = "Close";

    /**
     * Stores the text for the button to highlight the previous match in a search (e.g. in {@linkplain frontend.dialogs.SearchDialog}).
     */
    public static String previousButton = "Previous";

    /**
     * Stores the text for the button to highlight the next match in a search (e.g. in {@linkplain frontend.dialogs.SearchDialog}).
     */
    public static String nextButton = "Next";

    /**
     * Stores the text asking the user to enter a directory. (e.g. in {@linkplain frontend.dialogs.ExportToHtmlDialog}).
     */
    public static String enterExportDirectory = "Export Directory";

    /**
     * Stores the title for the search dialog {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String searchDialogTitle = "Search";

    /**
     * Asks the user to enter a regex to search in VATE in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String searchWhat = "Search what:";

    /**
     * Asks the user to search the active tab for a regex in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String searchActiveTab = "Search active tab";

    /**
     * Asks the user to search all tabs for a regex in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String searchAllTabs = "Search all tabs";

    /**
     * Allows the user to replace the searched contents with another String in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String enableSearchReplacement = "Enable Replacement";

    /**
     * Allows the user to replace the searched contents with another String in {@linkplain frontend.dialogs.SearchDialog}.
     */
    public static String searchReplacementHint = "Replace with:";

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
    public static String noMatches = "No matches";

    /**
     * Allows the user to see the license of VATE in {@linkplain frontend.menus.HelpMenu}.
     */
    public static String license = "License";

    /**
     * Allows the user to see the licensed use of software in {@linkplain frontend.menus.HelpMenu}.
     */
    public static String usedSoftware = "Used Software";

    /**
     * Stores the text of the MIT License for VATE, as used in {@linkplain frontend.frames.licenses.LicensesFrame}.
     */
    public static String MITLicense = "Copyright 2023 Christian-2003\n\nPermission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the \"Software\"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:\n\nThe above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n\nTHE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.\n";

}
