
package BLL;

import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import DAL.DALCreate;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BLLCreate {
    
    private static BLLCreate m_instance;
    
    private BLLCreate(){
        
    }
    
    public static BLLCreate getInstance(){
        if(m_instance == null){
            m_instance = new BLLCreate();
        }
        return m_instance;
    }
    
    public void createFireman(BEFireman fireman){
        try {
            DALCreate.getInstance().createFireman(fireman);
        } catch (SQLException ex) {
            Logger.getLogger(BLLCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createVehicle(BEVehicle vehicle){
        try {
            DALCreate.getInstance().createVehicle(vehicle);
        } catch (SQLException ex) {
            Logger.getLogger(BLLCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createMaterial(BEMaterial material){
        try {
            DALCreate.getInstance().createMaterial(material);
        } catch (SQLException ex) {
            Logger.getLogger(BLLCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
