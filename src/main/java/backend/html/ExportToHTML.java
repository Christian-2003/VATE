package backend.html;

import backend.singleton.Singleton;


/**
 * This class allows to export a String to HTML format.
 *
 * @author  Christian-2003
 * @version 12 May 2023
 */
public class ExportToHTML {

    /**
     * Stores the line seperator that shall be used when making the HTML file.
     * This is the {@code System.lineSeparator()} by default but can be changed manually.
     */
    public static String LINE_SEPERATOR = Singleton.CONFIG.lineSeparator;

    /**
     * Stores the tabulator-character that shall be used when formatting the HTML file.
     * This is '\t' by default but can be changed manually.
     */
    public static String TABULATOR = "\t";


    /**
     * Stores the value for the font family of the HTML document.
     * This is "{@code monospace}" by default, but can be changed manually.
     */
    public static String HTML_FONT_FAMILY = "monospace";

    /**
     * Stores the value for the title of the HTML document.
     * This is "{@code title}" by default, but can be changed manually.
     */
    public static String HTML_TITLE = "title";

    /**
     * Stores the value for the author  of the HTML document.
     * This is "{@code VATE HTML Exporter}" by default, but can be changed manually.
     */
    public static String HTML_AUTHOR = "VATE HTML Exporter";

    /**
     * Stores the value for the charset of the HTML document.
     * This is "{@code UTF-8}" by default, but can be changed manually.
     */
    public static String HTML_CHARSET = "UTF-8";

    /**
     * Stores the value for the tag that shall be used for line breaks.
     * This is "{@code <br>}" by default, but can be changed manually.
     */
    public static String HTML_LINE_BREAK = "<br>";

    /**
     * Stores the value for tabulators within the HTML format.
     * This is "{@code &nbsp;&nbsp;&nbsp;&nbsp;}" by default, but can be changed manually.
     */
    public static String HTML_TABULATOR = "&nbsp;&nbsp;&nbsp;&nbsp;";


    /**
     * Generates an HTML document which contains the passed string. The HTML document can be configured through the
     * public attributes of this class.
     *
     * @param s Content for the HTML document.
     * @return  String containing the content with HTML syntax.
     */
    public static String GENERATE_HTML(String s) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append(LINE_SEPERATOR);
        html.append(LINE_SEPERATOR);

        html.append("<html>");
        html.append(LINE_SEPERATOR);

        html.append(GENERATE_HEAD(1));
        html.append(LINE_SEPERATOR);
        html.append(LINE_SEPERATOR);

        html.append(GENERATE_BODY(1, s));
        html.append(LINE_SEPERATOR);

        html.append("</html>");
        html.append(LINE_SEPERATOR);

        return html.toString();
    }


    /**
     * Generates the body for the HTML document.
     *
     * @param indentation   Indentation for the body.
     * @param s             Content that shall be displayed within the body.
     * @return              Body for the HTML document.
     */
    private static String GENERATE_BODY(int indentation, String s) {
        String indent = GENERATE_INDENTATION(indentation);
        String contentIndent = GENERATE_INDENTATION(indentation + 1);
        StringBuilder body = new StringBuilder();

        body.append(indent);
        body.append("<body>");
        body.append(LINE_SEPERATOR);

        body.append(contentIndent);
        body.append("<p>");
        body.append(LINE_SEPERATOR);

        body.append(PREPARE_CONTENT(indentation + 2, s));
        body.append(LINE_SEPERATOR);

        body.append(contentIndent);
        body.append("</p>");
        body.append(LINE_SEPERATOR);

        body.append(indent);
        body.append("</body>");

        return body.toString();
    }


    /**
     * Generates the head for the HTML document.
     *
     * @param indentation   Indentation for the HTML head.
     * @return              Head for the HTML document.
     */
    private static String GENERATE_HEAD(int indentation) {
        String indent = GENERATE_INDENTATION(indentation);
        String contentIndent = GENERATE_INDENTATION(indentation + 1);
        StringBuilder head = new StringBuilder();

        head.append(indent);
        head.append("<head>");
        head.append(LINE_SEPERATOR);

        head.append(contentIndent);
        head.append("<title>");
        head.append(HTML_TITLE);
        head.append("</title>");
        head.append(LINE_SEPERATOR);

        head.append(contentIndent);
        head.append("<meta charset=\"");
        head.append(HTML_CHARSET);
        head.append("\"/>");
        head.append(LINE_SEPERATOR);

        head.append(contentIndent);
        head.append("<meta name=\"author\" content=\"");
        head.append(HTML_AUTHOR);
        head.append("\"/>");
        head.append(LINE_SEPERATOR);

        head.append(GENERATE_STYLE(indentation + 1));
        head.append(LINE_SEPERATOR);

        head.append(indent);
        head.append("</head>");

        return head.toString();
    }


    /**
     * Generates the style for the HTML document.
     *
     * @param indentation   Indentation for the style.
     * @return              Style in HTML syntax.
     */
    private static String GENERATE_STYLE(int indentation) {
        String indent = GENERATE_INDENTATION(indentation);
        String contentIndent = GENERATE_INDENTATION(indentation + 1);
        StringBuilder style = new StringBuilder();

        style.append(indent);
        style.append("<style>");
        style.append(LINE_SEPERATOR);

        style.append(contentIndent);
        style.append("body {font-family: ");
        style.append(HTML_FONT_FAMILY);
        style.append("}");
        style.append(LINE_SEPERATOR);

        style.append(indent);
        style.append("</style>");

        return style.toString();
    }


    /**
     * Prepares the content that shall be displayed in the HTML document.
     *
     * @param indentation   Indentation for the content.
     * @param s             Content to be prepared.
     * @return              Prepared content.
     */
    private static String PREPARE_CONTENT(int indentation, String s) {
        String indent = GENERATE_INDENTATION(indentation);
        StringBuilder content = new StringBuilder();
        String[] contentRows = s.split(LINE_SEPERATOR); //Separate the content into individual lines.

        //Prepare each individual line:
        for (int i = 0; i < contentRows.length; i++) {
            String current = contentRows[i];
            //Replace angled brackets:
            current = current.replaceAll("<", "&lt;");
            current = current.replaceAll(">", "&gt;");

            //Replace the tabulator or spaces at the beginning of the line:
            current = current.replaceAll(TABULATOR, HTML_TABULATOR);

            content.append(indent);
            content.append(current);

            //Append line break at the end if necessary:
            if (i < contentRows.length - 1) {
                content.append(HTML_LINE_BREAK);
                content.append(LINE_SEPERATOR);
            }
        }

        //Return prepared content:
        return content.toString();
    }


    /**
     * Generates a String containing the respective number of tabulators based on the passed indentation.
     *
     * @param indentation   Level of indentation to be generated.
     * @return              String containing the indentation.
     */
    private static String GENERATE_INDENTATION(int indentation) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            indent.append(TABULATOR);
        }
        return indent.toString();
    }

}
