package BLL;

import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import DAL.DALCreate;
import java.sql.SQLException;

public class BLLCreate {

    private static BLLCreate m_instance;

    private BLLCreate() {

    }

    /**
     *
     * @return m_instance of BLLCreate.
     */
    public static BLLCreate getInstance() {
        if (m_instance == null) {
            m_instance = new BLLCreate();
        }
        return m_instance;
    }

    /**
     * Creates a new fireman, and adds him to the array.
     *
     * @param fireman
     */
    public void createFireman(BEFireman fireman) {
        try {
            DALCreate.getInstance().createFireman(fireman);
        } catch (SQLException ex) {
            BLLError.getInstance().createFiremanError();
            return;
        }
        BLLRead.getInstance().addFiremanToArray(fireman);
    }

    /**
     * Creates a new vehicle, and adds it to the array.
     *
     * @param vehicle
     */
    public void createVehicle(BEVehicle vehicle) {
        try {
            DALCreate.getInstance().createVehicle(vehicle);
        } catch (SQLException ex) {
            BLLError.getInstance().createVehicleError();
            return;
        }
        BLLRead.getInstance().addVehicleToArray(vehicle);
    }

    /**
     * Creates a new material, and adds it to the array.
     *
     * @param material
     */
    public void createMaterial(BEMaterial material) {
        try {
            DALCreate.getInstance().createMaterial(material);
        } catch (SQLException ex) {
            BLLError.getInstance().createMaterialError();
            return;
        }
        BLLRead.getInstance().addMaterialToArray(material);
    }

}
