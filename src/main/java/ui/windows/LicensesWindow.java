package ui.windows;

import javax.swing.*;
import java.awt.*;


/**
 * This class implements a window that is used to display VATE's license and lists all programs that are used by VATE.
 *
 * @author  Christian-2003
 * @version 11 May 2023
 */
public class LicensesWindow extends JFrame {

    /**
     * Constructs a new LicensesWindow.
     */
    public LicensesWindow() {
        super("Licenses");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)(displaySize.width / 2), (int)(displaySize.height / 2));
        setLayout(new GridLayout(1, 2));


        initVATELicense(); //Init VATE License.

        setVisible(true);
    }


    private void initVATELicense() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        //Configure VATE Headline:
        JLabel VATE = new JLabel("VATE");
        container.add(VATE);

        //Configure veryAdvancedTextEditor:
        JLabel veryAdvancedTextEditor = new JLabel("Very Advanced Text Editor");
        container.add(veryAdvancedTextEditor);

        //Configure licenseText:
        JTextArea license = new JTextArea("Copyright 2023 Christian-2003\n" +
                "\n" +
                "Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:\n" +
                "\n" +
                "The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n" +
                "\n" +
                "THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.");
        license.setLineWrap(true);
        license.setWrapStyleWord(true);
        license.setEditable(false);
        JScrollPane licenseContainer = new JScrollPane(license);
        licenseContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        container.add(licenseContainer);

        add(container);
    }

}
