package frontend.frames.licenses;


import backend.config.Config;
import backend.files.csv.InvalidCSVException;
import backend.licenses.UsedSoftwareHandle;
import frontend.frames.licenses.components.LicensePanel;
import frontend.frames.main.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * This class implements a frame to display VATE's license as well as used software.
 *
 * @author  Christian-2003
 * @version 01 June 2023
 */
public class LicensesFrame extends JFrame {

    /**
     * Constructs a new LicensesFrame.
     *
     * @param context  MainFrame from which this Frame was created.
     */
    public LicensesFrame(MainFrame context) {
        super(Config.strings.license);

        create();

        setSize(1024, 512);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private void create() {
        JPanel mainContainer = new JPanel(new GridLayout(1, 2));
        add(mainContainer, BorderLayout.CENTER);

        //Configure vateLicenseContainer:
        JPanel vateLicenseContainer = new JPanel(new GridLayout(2, 1));
        mainContainer.add(vateLicenseContainer);

        //Configure vateName:
        JPanel vateNameContainer = new JPanel();
        vateNameContainer.setLayout(new BoxLayout(vateNameContainer, BoxLayout.PAGE_AXIS));
        vateLicenseContainer.add(vateNameContainer);
        JLabel vateName = new JLabel(Config.strings.vate);
        vateName.setFont(vateName.getFont().deriveFont((float)(Config.fonts.programFontSize * 3)));
        JLabel vateFullName = new JLabel(Config.strings.vateFullName);
        vateFullName.setFont(vateFullName.getFont().deriveFont((float)(Config.fonts.programFontSize * 2)));
        vateNameContainer.add(vateName);
        vateNameContainer.add(vateFullName);

        //Configure vateLicense:
        JTextPane vateLicense = new JTextPane();
        vateLicense.setText(Config.strings.MITLicense);
        vateLicense.setEditable(false);
        vateLicense.setFocusable(false);
        JScrollPane vateLicenseScrollPane = new JScrollPane(vateLicense);
        vateLicenseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        vateLicenseScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        vateLicenseContainer.add(vateLicenseScrollPane);

        //Configure used software:
        UsedSoftwareHandle usedSoftwares;
        try {
            usedSoftwares = new UsedSoftwareHandle();
        }
        catch (InvalidCSVException e) {
            //Could not read CSV:
            return;
        }
        JScrollPane usedSoftwareContainer = new JScrollPane();
        usedSoftwareContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        usedSoftwareContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JLabel usedSoftwareLabel = new JLabel(Config.strings.usedSoftware);
        usedSoftwareLabel.setFont(usedSoftwareLabel.getFont().deriveFont((float)(Config.fonts.programFontSize * 2)));
        JPanel softwareContainer = new JPanel();
        softwareContainer.setLayout(new BoxLayout(softwareContainer, BoxLayout.PAGE_AXIS));
        softwareContainer.add(usedSoftwareLabel);
        for (int i = 0; i < usedSoftwares.size(); i++) {
            softwareContainer.add(new LicensePanel(usedSoftwares.get(i)));
        }
        usedSoftwareContainer.setViewportView(softwareContainer);
        mainContainer.add(usedSoftwareContainer);

    }

}
