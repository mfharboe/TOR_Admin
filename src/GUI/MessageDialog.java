package GUI;

import Observer.IObserver;
import javax.swing.JOptionPane;

public class MessageDialog implements IObserver {

    private static MessageDialog m_instance;

    private MessageDialog() {

    }

    public static MessageDialog getInstance() {
        if (m_instance == null) {
            m_instance = new MessageDialog();
        }
        return m_instance;
    }

    public String adminTitle() {
        return "TOR - Administrator";
    }

    public String firemenAdminTitle() {
        return "TOR - Mandskab";
    }

    public String vehicleAdminTitle() {
        return "TOR - Køretøjer";
    }

    public String materialAdminTitle() {
        return "TOR - Materiel";
    }

    public String emptyString() {
        return "";
    }

    public String alarmType() {
        return "Ingen valgt";
    }

    public String zipType() {
        return "Post Nummer..";
    }

    @Override
    public void update(String error) {
        JOptionPane.showMessageDialog(null, error);
    }

}
