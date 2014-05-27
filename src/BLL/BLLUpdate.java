package BLL;

import BE.BEFireman;
import BE.BEIncidentDetails;
import BE.BEMaterial;
import BE.BEVehicle;
import DAL.Interfaces.IDALUpdate;
import java.sql.SQLException;

public class BLLUpdate {

    private static BLLUpdate m_instance;
    IDALUpdate dalUpdate;

    private BLLUpdate() {

    }

    /**
     *
     * @return m_instance of BLLUpdate.
     */
    public static BLLUpdate getInstance() {
        if (m_instance == null) {
            m_instance = new BLLUpdate();
        }
        return m_instance;
    }

    public void setDAL(IDALUpdate d) {
        dalUpdate = d;
    }

    /**
     * Updates values for a given incidentDetails
     *
     * @param updatedDetails
     */
    public void updateDetails(BEIncidentDetails updatedDetails) {
        if (updatedDetails == null) {
            BLLError.getInstance().updateDetailsError();
            return;
        }
        if (updatedDetails.getM_incident().isM_isDone() == true) {
            try {
                dalUpdate.updateIncidentDone(updatedDetails.getM_incident());
            } catch (SQLException ex) {
                BLLError.getInstance().finishIncidentError();
                return;
            }
        }
        try {
            dalUpdate.updateIncidentDetails(updatedDetails);
        } catch (SQLException ex) {
            BLLError.getInstance().updateDetailsError();
        }
    }

    /**
     * Updates a given fireman in DB and array.
     *
     * @param fireman
     */
    public void updateFireman(BEFireman fireman) {
        if (fireman == null) {
            BLLError.getInstance().updateFiremanError();
            return;
        }
        if (fireman.getM_zipCode() == null) {
            BLLError.getInstance().fillOutZip();
            return;
        }
        if (fireman.getM_recruited() == null) {
            BLLError.getInstance().fillOutDate();
            return;
        }
        try {
            dalUpdate.updateFireman(fireman);
        } catch (SQLException ex) {
            BLLError.getInstance().updateFiremanError();
            return;
        }
        BLLRead.getInstance().updateFiremanArray(fireman);
    }

    /**
     * Updates a given vehicle in DB and array.
     *
     * @param vehicle
     */
    public void updateVehicle(BEVehicle vehicle) {
        if (vehicle == null) {
            BLLError.getInstance().updateVehicleError();
            return;
        }
        try {
            dalUpdate.updateVehicle(vehicle);
        } catch (SQLException ex) {
            BLLError.getInstance().updateVehicleError();
            return;
        }
        BLLRead.getInstance().updateVehicleArray(vehicle);
    }

    /**
     * Updates a given material in DB and array.
     *
     * @param material
     */
    public void updateMaterial(BEMaterial material) {
        if (material == null) {
            BLLError.getInstance().updateMaterialError();
            return;
        }
        try {
            dalUpdate.updateMaterial(material);
        } catch (SQLException ex) {
            BLLError.getInstance().updateMaterialError();
            return;
        }
        BLLRead.getInstance().updateMaterialArray(material);
    }
}
