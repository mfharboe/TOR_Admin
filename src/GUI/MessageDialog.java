package GUI;

import BE.BEFireman;
import Observer.IObserver;
import javax.security.auth.callback.ConfirmationCallback;
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

    public String dateType() {
        return "YYYY-MM-DD";
    }
    
    public String txtIntChecker() {
        return "[0-9]+";
    }
    
    public String txtStringChecker() {
        return "[A-Å]+";
    }

    public void filloutAllFields() {
        JOptionPane.showMessageDialog(null, "Udfyld alle felterne");
    }

    public void noTextHere() {
        JOptionPane.showMessageDialog(null, "Skriv kun tal i dette felt");
    }
    
    public void noIntHere() {
        JOptionPane.showMessageDialog(null, "Skriv kun bogstaver i dette felt");
    }

    public void pdfCreated() {
        JOptionPane.showMessageDialog(null, "Dokumentet blev oprettet");
    }

    public void incidentApproved() {
        JOptionPane.showMessageDialog(null, "Meldingen blev gemt og godkendt");
    }
    
    public void fillOutAllDates() {
        JOptionPane.showMessageDialog(null, "Udfyld begge datofelter");
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
