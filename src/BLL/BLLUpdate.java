package BLL;

import BE.BEIncidentDetails;
import DAL.DALUpdate;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BLLUpdate {

    private static BLLUpdate m_instance;

    private BLLUpdate() {

    }

    /**
     * Creates or returns the current instance of BLLUpdate
     *
     * @return
     */
    public static BLLUpdate getInstance() {
        if (m_instance == null) {
            m_instance = new BLLUpdate();
        }
        return m_instance;
    }

    /**
     * Updates values for a given incidentDetails
     *
     * @param updatedDetails
     */
    public void updateDetails(BEIncidentDetails updatedDetails) {
        try {
            DALUpdate.getInstance().updateIncidentDetails(updatedDetails);
        } catch (SQLException ex) {
            Logger.getLogger(BLLUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
