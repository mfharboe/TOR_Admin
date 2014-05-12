package BLL;

import BE.BEIncident;
import DAL.DALRead;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BLLRead {

    private static BLLRead m_instance;
    ArrayList<BEIncident> recentIncidents;
    ArrayList<BEIncident> incidentsByDate;

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
        } catch (SQLException ex) {
            Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recentIncidents;
    }

    public ArrayList<BEIncident> readAllIncidentsByDate(Date searchDate) {
        try {
            incidentsByDate = DALRead.getInstance().readIncidentsByDate(searchDate);
        } catch (SQLException ex) {
            Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
        }
        return incidentsByDate;
    }
}
