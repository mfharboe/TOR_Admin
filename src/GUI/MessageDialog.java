package GUI;

import javax.swing.JOptionPane;

public class MessageDialog {

    private static MessageDialog m_instance;

    private MessageDialog() {

    }

    public static MessageDialog getInstance() {
        if (m_instance == null) {
            m_instance = new MessageDialog();
        }
        return m_instance;
    }
    
    public String adminTitle(){
        return "TOR - Admin";
    }
    
    public String emptyString(){
        return "";
    }
    
    public String alarmType(){
        return "Ingen valgt";
    }
    
    public void dialogNoRecentIncidents(){
        JOptionPane.showMessageDialog(null, "Der er ingen nye meldinger");
    }

}
