package BLL;

import BE.BEIncident;
import BE.BEIncidentDetails;
import BE.BERoleTime;
import BE.BEUsage;
import DAL.DALRead;
import GUI.MessageDialog;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BLLRead {

    private static BLLRead m_instance;
    ArrayList<BEIncident> recentIncidents;
    ArrayList<BEIncident> incidentsByDate;
    ArrayList<BEIncident> allIncidents;
    ArrayList<BEIncidentDetails> incidentDetails;
    ArrayList<BEUsage> incidentUsage;
    ArrayList<BERoleTime> incidentRoleTime;

    private BLLRead() {

    }

    public static BLLRead getInstance() {
        if (m_instance == null) {
            m_instance = new BLLRead();
        }
        return m_instance;
    }

    public ArrayList<BEIncident> readAllRecentIncidents() {
        try {
            recentIncidents = DALRead.getInstance().readRecentIncidents();
            if (recentIncidents.isEmpty()) {
                MessageDialog.getInstance().dialogNoRecentIncidents(); //MÅ IKKE VÆRE HER
            } else {
                readIncidentDetails();
                readIncidentUsage();
                readIncidentRoleTime();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recentIncidents;
    }

    public ArrayList<BEIncidentDetails> readIncidentDetails() {
        if (incidentDetails == null) {
            try {
                incidentDetails = DALRead.getInstance().readIncidentDetails();

            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return incidentDetails;
    }

    public ArrayList<BEUsage> readIncidentUsage() {
        if (incidentUsage == null) {
            try {
                incidentUsage = DALRead.getInstance().readUsage();
            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return incidentUsage;
    }

    public ArrayList<BERoleTime> readIncidentRoleTime() {
        if (incidentRoleTime == null) {
            try {
                incidentRoleTime = DALRead.getInstance().readRoleTime();
            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return incidentRoleTime;

    }

    public void clearDetailsArray() {
        incidentDetails = null;
        incidentUsage = null;
        incidentRoleTime = null;
    }
}
