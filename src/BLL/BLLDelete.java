
package BLL;

import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import DAL.DALDelete;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BLLDelete {
    
    private static BLLDelete m_instance;
    
    private BLLDelete(){
        
    }
    
    public static BLLDelete getInstance(){
        if(m_instance == null){
            m_instance = new BLLDelete();
        }
        return m_instance;
    }
    
    public void deleteFireman(BEFireman fireman){
        try {
            DALDelete.getInstance().deleteFireman(fireman);
        } catch (SQLException ex) {
            Logger.getLogger(BLLDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
        BLLRead.getInstance().removeFromFiremen(fireman);
    }
    
    public void deleteVehicle(BEVehicle vehicle){
        try {
            DALDelete.getInstance().deleteVehicle(vehicle);
        } catch (SQLException ex) {
            Logger.getLogger(BLLDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
        BLLRead.getInstance().removeFromVehicles(vehicle);
    }
    
    public void deleteMaterial(BEMaterial material){
        try {
            DALDelete.getInstance().deleteMaterial(material);
        } catch (SQLException ex) {
            Logger.getLogger(BLLDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
        BLLRead.getInstance().removeFromMaterial(material);
        
    }
    
}
