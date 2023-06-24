package frontend.frames.licenses.components;

import backend.config.Config;
import backend.licenses.UsedSoftware;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;


/**
 * This class implements a single panel displaying the necessary information for a single {@linkplain UsedSoftware}.
 *
 * @author  Christian-2003
 * @version 02 June 2023
 */
public class LicensePanel extends JPanel {


    /**
     * Constructs a new LicensePanel displaying the passed UsedSoftware.
     *
     * @param usedSoftware  UsedSoftware to be displayed.
     */
    public LicensePanel(UsedSoftware usedSoftware) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(new JSeparator());

        //Configure name:
        JPanel nameVersionContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(nameVersionContainer);
        JLabel name = new JLabel(usedSoftware.getName());
        name.setFont(name.getFont().deriveFont(Font.BOLD));
        JPanel nameContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameContainer.add(name);
        nameVersionContainer.add(nameContainer);
        JLabel version = new JLabel(usedSoftware.getVersion());
        version.setFont(version.getFont().deriveFont(Font.BOLD));
        JPanel versionContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        versionContainer.add(version);
        nameVersionContainer.add(versionContainer);

        //Configure description:
        JTextPane description = new JTextPane();
        description.setText(usedSoftware.getDescription());
        description.setEditable(false);
        description.setFocusable(false);
        add(description);

        //Configure License:
        JLabel license = new JLabel(usedSoftware.getLicense());
        add(license);

        //Configure linkToLicense:
        JButton linkToLicense = new JButton(usedSoftware.getLinkToLicense());
        linkToLicense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Desktop.getDesktop().browse(new URL(usedSoftware.getLinkToLicense()).toURI());
                }
                catch (Exception e) {
                    //Do nothing...
                }
            }
        });
        add(linkToLicense);
    }

}
