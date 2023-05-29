package ui.windows.main;

import backend.config.ConfigWriter;
import backend.html.ExportToHTML;
import backend.singleton.Singleton;
import ui.windows.main.components.EditorTabbedPane;
import ui.handles.MainWindowTitleHandle;
import ui.menu.MainMenu;
import ui.windows.main.components.editorTabs.TextEditorTab;

import javax.swing.*;
import java.awt.*;
import java.io.*;


/**
 * Implements the MainWindow for the VATE.
 *
 * @author  Christian-2003
 * @version 24 May 2023
 */
public class MainFrame extends JFrame {

    /**
     * Handle for the title of the MainWindow.
     */
    private final MainWindowTitleHandle titleHandle;

    /**
     * Main menu for this window.
     */
    private MainMenu menu;

    /**
     * TabbedPane contains the EditorTabs of the text editor.
     */
    private EditorTabbedPane editorTabs;


    /**
     * Constructs a new TextFrame instance that displays the contents of the file of the passed argument. The size of
     * the TextFrame will be determined by the specified arguments.
     *
     * @param loadPreviousFile  Whether the previously opened file (retrieved from config) shall be automatically opened.
     * @param path              Filepath to load when called. This overrides the previous parameter. Pass {@code null} if
     *                          no file shall be opened and use previous parameter instead.
     */
    public MainFrame(boolean loadPreviousFile, String path) {
        super();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setFont(new Font("Segoe UI", Font.PLAIN, 16));
        }
        catch (Exception e) {
            //Do nothing...
        }
        titleHandle = new MainWindowTitleHandle("VATE");

        //Instantiate editorTabs:
        editorTabs = new EditorTabbedPane(this);
        editorTabs.addTab(new TextEditorTab(this, "Text file 1"));
        editorTabs.addTab(new TextEditorTab(this, "Text file 2"));
        editorTabs.addTab(new TextEditorTab(this, "Text file 3"));
        add(editorTabs, BorderLayout.CENTER);

        //Configure style for the tabs:
        requestTextEditorStyleUpdate();

        //Configure window with config data:
        create(loadPreviousFile);
        if (path != null && !path.equals("")) {
            //Path to open was provided:
            loadFile(path);
        }

        //Configure main menu bar:
        menu = new MainMenu(this);
        add(menu, BorderLayout.NORTH);
        setVisible(true);
    }


    /**
     * Requests a new file to be created within the main window.
     * This method is intended to be called from the "File > New File"-menu.
     */
    public void requestNewFileCreate() {
        //Show JFileChooser:
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showDialog(this, "Create");
        if (option != JFileChooser.APPROVE_OPTION) {
            //No file selected: Do not create file:
            return;
        }
        String path = fileChooser.getSelectedFile().getAbsolutePath(); //Stores the path of the file that shall be created.
        if (path == null || path.equals("")) {
            //No file entered: Do not create file:
            return;
        }
        //Apply new file path to the window:
        titleHandle.setFileName(path);
        titleHandle.hasUnsavedChanges(false);
        setTitle(titleHandle.getTitle());
        if (editorTabs.getActiveTab() instanceof TextEditorTab) {
            TextEditorTab current = (TextEditorTab)editorTabs.getActiveTab();
            current.setText("");
        }
        //Find the file extension:
        String[] pathDivided = path.split("\\.");
        if (pathDivided.length >= 2) {
            //File extension available:
            editorTabs.getActiveTab().getSubline().setFileExtension(pathDivided[pathDivided.length - 1]);
        }
        else {
            //No file extension available:
            editorTabs.getActiveTab().getSubline().setFileExtension("");
        }
    }

    /**
     * Requests a file to be opened within the main window.
     * This method is intended to be called from the "File > Open"-menu.
     */
    public void requestFileOpen() {
        //Show JFileChooser:
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option != JFileChooser.APPROVE_OPTION) {
            //No file selected: Do not open file:
            return;
        }
        String path = fileChooser.getSelectedFile().getAbsolutePath(); //Stores the path for the file that shall be opened.
        if (path == null || path.equals("")) {
            //No file entered: Do not open file:
            return;
        }
        //Load the file:
        loadFile(path);
    }

    /**
     * Requests the currently opened file to be saved with a new file path.
     * This method is intended to be called from the "File > Save As"-menu.
     */
    public void requestSaveAs() {
        //Show JFileChooser:
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option != JFileChooser.APPROVE_OPTION) {
            //No location selected: Do not save file:
            return;
        }
        String path = fileChooser.getSelectedFile().getAbsolutePath(); //Stores the path for the file that shall be opened.
        if (path == null || path.equals("")) {
            //No location entered: Do not save file:
            return;
        }
        //Apply the filepath to this window:
        titleHandle.setFileName(path);
        titleHandle.hasUnsavedChanges(false);
        setTitle(titleHandle.getTitle());
        saveFile(path);
    }

    /**
     * Requests the currently opened file to be saved.
     * This method is intended to be called from the "File > Save"-menu.
     */
    public void requestSave() {
        if (titleHandle.getFileName().equals("")) {
            //No filename was entered before: Request the user to "Save As" the file:
            requestSaveAs();
            return;
        }
        saveFile(titleHandle.getFileName());
    }

    /**
     * Requests the currently opened file to be exported to an HTML file.
     * This method is intended to be called from the "File > Export to HTML"-menu.
     */
    public void requestExportToHTML() {
        //Show JFileChooser:
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option != JFileChooser.APPROVE_OPTION) {
            //No location selected: Do not save file:
            return;
        }
        String path = fileChooser.getSelectedFile().getAbsolutePath(); //Stores the path for the file that shall be opened.
        if (path == null || path.equals("")) {
            //No location entered: Do not save file:
            return;
        }
        exportToHTML(path);
    }

    /**
     * Requests the currently opened file to be searched for a regex.
     * This method is intended to be called from the "Edit > Search"-menu.
     */
    public void requestSearch() {
        String regex = JOptionPane.showInputDialog(this, "What to search?");
        if (regex == null) {
            //Input cancelled: Do not search a regex:
            return;
        }
        int index = 0;
        if (editorTabs.getActiveTab() instanceof TextEditorTab) {
            TextEditorTab current = (TextEditorTab)editorTabs.getActiveTab();
            index = current.getText().indexOf(regex);
        }
        if (index == -1) {
            //No match for regex found:
            JOptionPane.showMessageDialog(this, "Did not find any matches for \"" + regex + "\".");
            return;
        }
        moveCursor(index);
    }

    /**
     * Requests the currently opened file to move the cursor to a specified line.
     * This method is intended to be called from the "Edit > Goto"-menu.
     */
    public void requestGoto() {
        String line = JOptionPane.showInputDialog(this, "Which line to go to?");
        if (line == null || line.equals("")) {
            //Nothing entered:
            return;
        }
        try {
            moveCursorToLine(Integer.parseInt(line));
        }
        catch (NumberFormatException e) {
            //Could not go to the specified line:
            JOptionPane.showMessageDialog(this, e.getMessage(), "Could not move to line.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Requests the line numbers to be shown or hidden.
     * This method is intended to be called from the "Settings > Show Line Numbers"-menu.
     *
     * @param lineNumbers   Whether the line numbers shall be shown or not.
     */
    public void requestToggleLineNumbers(boolean lineNumbers) {
        //editorTabs.requestToggleLineNumbers(lineNumbers);
    }


    /**
     * Requests an update to the style of the text editor.
     * This method is intended to be called from the Settings window.
     */
    public void requestTextEditorStyleUpdate() {
        //editorTabs.requestTextEditorStyleUpdate();
    }


    /**
     * Loads the contents of the passed file. The content of the file will be displayed with the textArea.
     * If the file could not be opened, false will be returned.
     *
     * @param path  Path of the file whose content shall be displayed.
     * @return      Whether the file could be opened or not.
     */
    private boolean loadFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            StringBuilder content = new StringBuilder();
            while (reader.ready()) {
                content.append(reader.readLine() + '\n');
            }
            //editorTabs.getActiveTab().setText(content.toString());
            titleHandle.setFileName(path);
            titleHandle.hasUnsavedChanges(false);
            setTitle(titleHandle.getTitle());
            String[] pathDivided = path.split("\\.");
            if (pathDivided.length >= 2) {
                editorTabs.getActiveTab().getSubline().setFileExtension(pathDivided[pathDivided.length - 1]);
            }
            else {
                editorTabs.getActiveTab().getSubline().setFileExtension("none");
            }
            return true;
        }
        catch (IOException e) {
            //Error: Could not open file:
            JOptionPane.showMessageDialog(this, e.getMessage(), "Could not open file.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Saves the specified content to the file.
     *
     * @param path      Path to which the file shall be saved.
     * @param content   Content to save to the file.
     * @return          Whether the file was saved successfully.
     */
    private boolean saveFile(String path, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(content);
            titleHandle.hasUnsavedChanges(false);
            setTitle(titleHandle.getTitle());
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    /**
     * Saves the content of the JTextArea to the file.
     *
     * @param path      Path to which the file shall be saved.
     * @return          Whether the file was saved successfully.
     */
    private boolean saveFile(String path) {
        return false;
        //return saveFile(path, editorTabs.getActiveTab().getText());
    }


    /**
     * Exports the contents to HTML.
     *
     * @param path  Path for the HTML document.
     */
    private void exportToHTML(String path) {
        String[] filename = path.split("\\.");
        if (editorTabs.getActiveTab().getSubline().getFileExtension().equals("html")) {
            //The document is already HTML:
            //saveFile(path, editorTabs.getActiveTab().getText());
            return;
        }
        if (filename.length >= 1 && filename[0] != null && !filename[0].equals("")) {
            //Set the filename of the HTML document to the
            ExportToHTML.HTML_TITLE = filename[0];
        }
        else {
            ExportToHTML.HTML_TITLE = path;
        }
        //saveFile(path, ExportToHTML.GENERATE_HTML(editorTabs.getActiveTab().getText()));
    }


    /**
     * Moves the cursor to the passed position within the text area. If the passed position is out of bounds, nothing
     * happens.
     *
     * @param position  Position to move the cursor to.
     */
    private void moveCursor(int position) {
        if (position >= 0 && editorTabs.getActiveTab().getTextArea().getDocument().getLength() > position) {
            //textArea.moveCaretPosition(position);
            editorTabs.getActiveTab().getTextArea().setCaretPosition(position);
        }
    }


    /**
     * Moves the cursor to the specified line.
     *
     * @param position  Line to which the cursor shall be moved.
     */
    private void moveCursorToLine(int position) {
        String content = editorTabs.getActiveTab().getText();
        int index = 0;
        int currentLine = 1;
        //Iterate through the lines:
        while (index < content.length()) {
            if (currentLine == position) {
                break;
            }
            index = content.indexOf(Singleton.CONFIG.lineSeparator, index);
            currentLine++;
        }
        moveCursor(index);
    }


    /**
     * Instantiates the window with all data from the config file.
     *
     * @param loadPreviousFile  Whether the previously opened file shall be opened.
     */
    public void create(boolean loadPreviousFile) {
        editorTabs.getActiveTab().getSubline().setVisible(Singleton.CONFIG.showLineNumbers);
        if (loadPreviousFile && !Singleton.CONFIG.lastOpenedFile.equals("")) {
            //File was previously opened:
            loadFile(Singleton.CONFIG.lastOpenedFile);
        }
        if (Singleton.CONFIG.lastWindowDimension == null) {
            Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
            displaySize.width = (int)(displaySize.width / 1.5);
            displaySize.height = (int)(displaySize.height / 1.5);
            Singleton.CONFIG.lastWindowDimension = displaySize;
        }
        setSize(Singleton.CONFIG.lastWindowDimension);
    }


    /**
     * Saves all relevant data from this window and stores them in the config file.
     * Afterwards, the {@code JFrame.dispose()}-method is called to close the window.
     */
    @Override
    public void dispose() {
        Singleton.CONFIG.lastWindowDimension = getSize(); //Stores the current dimensions of the window.
        Singleton.CONFIG.lastOpenedFile = titleHandle.getFileName(); //Stores the currently opened file.

        try{
            ConfigWriter.SAVE_CONFIG(); //Saves the config.
        }
        catch (IOException e) {
            //Config file could not be saved:
            JOptionPane.showMessageDialog(this, e.getMessage(), "Could not save config.", JOptionPane.ERROR_MESSAGE);
        }

        super.dispose();
    }

}
