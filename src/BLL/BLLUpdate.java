package BLL;

import BE.BEFireman;
import BE.BEIncidentDetails;
import BE.BEMaterial;
import BE.BEVehicle;
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
        try {
            DALUpdate.getInstance().updateIncidentDone(updatedDetails.getM_incident());
        } catch (SQLException ex) {
            Logger.getLogger(BLLUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateFireman(BEFireman fireman) {
        try {
            DALUpdate.getInstance().updateFireman(fireman);
        } catch (SQLException ex) {
            Logger.getLogger(BLLUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (BEFireman allFiremen : BLLRead.getInstance().readAllFiremen()) {
            if (allFiremen.getM_id() == fireman.getM_id()) {
                allFiremen.setM_recruited(fireman.getM_recruited());
                allFiremen.setM_firstName(fireman.getM_firstName());
                allFiremen.setM_lastName(fireman.getM_lastName());
                allFiremen.setM_address(fireman.getM_address());
                allFiremen.setM_zipCode(fireman.getM_zipCode());
                allFiremen.setM_phone(fireman.getM_phone());
                allFiremen.setM_paymentNumber(fireman.getM_paymentNumber());
                allFiremen.setM_isTeamLeader(fireman.isM_isTeamLeader());
                allFiremen.setM_photoPath(fireman.getM_photoPath());
                break;
            }
        }

    }

    public void updateVehicle(BEVehicle vehicle) {
        try {
            DALUpdate.getInstance().updateVehicle(null);
        } catch (SQLException ex) {
            Logger.getLogger(BLLUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateMaterial(BEMaterial material){
        try {
            DALUpdate.getInstance().updateMaterial(material);
        } catch (SQLException ex) {
            Logger.getLogger(BLLUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
