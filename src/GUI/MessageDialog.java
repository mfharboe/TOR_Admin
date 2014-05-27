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
        return "Vælg Type";
    }

    public String zipType() {
        return "Post Nummer..";
    }
    
    public String txtIntChecker() {
        return "[0-9]+";
    }

    public void noTextHere() {
        JOptionPane.showMessageDialog(null, "Skriv kun tal i dette felt");
    }

    public boolean deleteFireman() {
        int reply = JOptionPane.showConfirmDialog(null, "Er du sikker?", "Slet Brandmand", JOptionPane.YES_NO_OPTION);
        return reply == JOptionPane.YES_OPTION;
    }

    public boolean deleteVehicle() {
        int reply = JOptionPane.showConfirmDialog(null, "Er du sikker?", "Slet Køretøj", JOptionPane.YES_NO_OPTION);
        return reply == JOptionPane.YES_OPTION;
    }

    public boolean deleteMaterial() {
        int reply = JOptionPane.showConfirmDialog(null, "Er du sikker?", "Slet Materiel", JOptionPane.YES_NO_OPTION);
        return reply == JOptionPane.YES_OPTION;
    }

    @Override
    public void update(String error) {
        JOptionPane.showMessageDialog(null, error);
    }

}
