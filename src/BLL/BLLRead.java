package BLL;

import BE.BEAlarm;
import BE.BEFireman;
import BE.BEIncident;
import BE.BEIncidentDetails;
import BE.BEMaterial;
import BE.BERoleTime;
import BE.BEUsage;
import BE.BEVehicle;
import BE.BEZipcode;
import DAL.DALRead;
import GUI.MessageDialog;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BLLRead {

    private static BLLRead m_instance;
    ArrayList<BEIncident> recentIncidents;
    ArrayList<BEIncident> incidentsByDate;
    ArrayList<BEIncident> allIncidents;
    ArrayList<BEAlarm> allAlarms;
    ArrayList<BEIncidentDetails> incidentDetails;
    ArrayList<BEUsage> incidentUsage;
    ArrayList<BERoleTime> incidentRoleTime;
    ArrayList<BEFireman> allFiremen;
    ArrayList<BEZipcode> allZipcodes;
    ArrayList<BEVehicle> allVehicles;
    ArrayList<BEMaterial> allMaterials;

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
            if (recentIncidents.isEmpty()) {
                MessageDialog.getInstance().dialogNoRecentIncidents(); //MÅ IKKE VÆRE HER
            } else {
                readIncidentDetails();
                readIncidentUsage();
                readIncidentRoleTime();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recentIncidents;
    }
    
    public ArrayList<BEZipcode> readAllZipcodes() {
        if(allZipcodes == null) {
            try {
                allZipcodes = DALRead.getInstance().readZipcodes();
            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return allZipcodes;
    }
    
    public ArrayList<BEFireman> readAllFiremen(){
        if(allFiremen == null){
            try {
                allFiremen = DALRead.getInstance().readFiremen();
            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return allFiremen;
    }

    public ArrayList<BEIncidentDetails> readIncidentDetails() {
        if (incidentDetails == null) {
            try {
                incidentDetails = DALRead.getInstance().readIncidentDetails();

            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return incidentDetails;
    }

    public ArrayList<BEUsage> readIncidentUsage() {
        if (incidentUsage == null) {
            try {
                incidentUsage = DALRead.getInstance().readUsage();
            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return incidentUsage;
    }

    public ArrayList<BERoleTime> readIncidentRoleTime() {
        if (incidentRoleTime == null) {
            try {
                incidentRoleTime = DALRead.getInstance().readRoleTime();
            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return incidentRoleTime;

    }

    public ArrayList<BEAlarm> readAllAlarms() {
        if (allAlarms == null) {
            try {
                allAlarms = DALRead.getInstance().readAlarms();
            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return allAlarms;
    }
    
    public ArrayList<BEMaterial> readAllMaterials(){
        if(allMaterials == null){
            try {
                allMaterials = DALRead.getInstance().readMaterial();
            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return allMaterials;
    }
    
    public ArrayList<BEVehicle> readAllVehicles(){
        if(allVehicles == null){
            try {
                allVehicles = DALRead.getInstance().readVehicles();
            } catch (SQLException ex) {
                Logger.getLogger(BLLRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return allVehicles;
    }

    public void clearDetailsArray() {
        incidentDetails = null;
        incidentUsage = null;
        incidentRoleTime = null;
    }
    
    public void clearFiremenArray(){
        allFiremen = null;
    }
    
    public void removeFromFiremen(BEFireman fireman){
        allFiremen.remove(fireman);
    }
    
    public void removeFromVehicles(BEVehicle vehicle){
        allVehicles.remove(vehicle);
    }

    public void removeFromMaterial(BEMaterial material) {
        allMaterials.remove(material);
    }
    

}
