package BLL;

import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import DAL.DALDelete;
import java.sql.SQLException;

public class BLLDelete {

    private static BLLDelete m_instance;

    private BLLDelete() {

    }

    /**
     *
     * @return m_instance of BLLDelete.
     */
    public static BLLDelete getInstance() {
        if (m_instance == null) {
            m_instance = new BLLDelete();
        }
        return m_instance;
    }

    /**
     * Deletes a given fireman, and removes him from the array.
     *
     * @param fireman
     */
    public void deleteFireman(BEFireman fireman) {
        try {
            DALDelete.getInstance().deleteFireman(fireman);
        } catch (SQLException ex) {
            BLLError.getInstance().deleteFiremanError();
            return;
        }
        BLLRead.getInstance().removeFiremenFromArray(fireman);
    }

    /**
     * Deletes a given vehicle, and removes it from the array.
     *
     * @param vehicle
     */
    public void deleteVehicle(BEVehicle vehicle) {
        try {
            DALDelete.getInstance().deleteVehicle(vehicle);
        } catch (SQLException ex) {
            BLLError.getInstance().deleteVehicleError();
            return;
        }
        BLLRead.getInstance().removeVehicleFromArray(vehicle);
    }

    /**
     * Deletes a given material, and removes it from the array.
     *
     * @param material
     */
    public void deleteMaterial(BEMaterial material) {
        try {
            DALDelete.getInstance().deleteMaterial(material);
        } catch (SQLException ex) {
            BLLError.getInstance().deleteMaterialError();
            return;
        }
        BLLRead.getInstance().removeMaterialFromArray(material);

    }

}
