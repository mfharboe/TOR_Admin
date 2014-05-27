package BLL;

import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import DAL.Interfaces.IDALCreate;
import java.sql.SQLException;

public class BLLCreate {

    private static BLLCreate m_instance;
    IDALCreate dalCreate;

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
    
    public void setDAL(IDALCreate d){
        dalCreate = d;
    }

    /**
     * Creates a new fireman in DB and array.
     *
     * @param fireman
     */
    public void createFireman(BEFireman fireman) {
        if(fireman == null){
            BLLError.getInstance().createFiremanError();
            return;
        }
        if(fireman.getM_zipCode() == null){
            BLLError.getInstance().fillOutZip();
            return;
        }
        if(fireman.getM_recruited() == null){
            BLLError.getInstance().fillOutDate();
            return;
        }
        try {
            dalCreate.createFireman(fireman);
        } catch (SQLException ex) {
            BLLError.getInstance().createFiremanError();
            return;
        }
        BLLRead.getInstance().addFiremanToArray(fireman);
    }

    /**
     * Creates a new vehicle in DB and array.
     *
     * @param vehicle
     */
    public void createVehicle(BEVehicle vehicle) {
        if(vehicle == null){
            BLLError.getInstance().createVehicleError();
            return;
        }
        try {
            dalCreate.createVehicle(vehicle);
        } catch (SQLException ex) {
            BLLError.getInstance().createVehicleError();
            return;
        }
        BLLRead.getInstance().addVehicleToArray(vehicle);
    }

    /**
     * Creates a new material in DB and array.
     *
     * @param material
     */
    public void createMaterial(BEMaterial material) {
        if(material == null){
            BLLError.getInstance().createMaterialError();
            return;
        }
        try {
            dalCreate.createMaterial(material);
        } catch (SQLException ex) {
            BLLError.getInstance().createMaterialError();
            return;
        }
        BLLRead.getInstance().addMaterialToArray(material);
    }

}
