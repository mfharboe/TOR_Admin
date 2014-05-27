package BLL;

import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import DAL.Interfaces.IDALDelete;
import java.sql.SQLException;

public class BLLDelete {

    private static BLLDelete m_instance;
    IDALDelete dalDelete;

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

    public void setDAL(IDALDelete d) {
        dalDelete = d;
    }

    /**
     * Deletes a given fireman from DB and array.
     *
     * @param fireman
     */
    public void deleteFireman(BEFireman fireman) {
        if (fireman == null) {
            BLLError.getInstance().deleteFiremanError();
            return;
        }
        try {
            dalDelete.deleteFireman(fireman);
        } catch (SQLException ex) {
            BLLError.getInstance().deleteFiremanError();
            return;
        }
        BLLRead.getInstance().removeFiremenFromArray(fireman);
    }

    /**
     * Deletes a given vehicle from DB and array.
     *
     * @param vehicle
     */
    public void deleteVehicle(BEVehicle vehicle) {
        if (vehicle == null) {
            BLLError.getInstance().deleteVehicleError();
            return;
        }
        try {
            dalDelete.deleteVehicle(vehicle);
        } catch (SQLException ex) {
            BLLError.getInstance().deleteVehicleError();
            return;
        }
        BLLRead.getInstance().removeVehicleFromArray(vehicle);
    }

    /**
     * Deletes a given material from DB and array.
     *
     * @param material
     */
    public void deleteMaterial(BEMaterial material) {
        if (material == null) {
            BLLError.getInstance().createMaterialError();
            return;
        }
        try {
            dalDelete.deleteMaterial(material);
        } catch (SQLException ex) {
            BLLError.getInstance().deleteMaterialError();
            return;
        }
        BLLRead.getInstance().removeMaterialFromArray(material);

    }

}
