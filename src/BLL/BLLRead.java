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
import DAL.Interfaces.IDALRead;
import java.sql.SQLException;
import java.util.ArrayList;

public class BLLRead {

    private static BLLRead m_instance;
    IDALRead dalRead;
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

    /**
     *
     * @return m_instance of BLLRead.
     */
    public static BLLRead getInstance() {
        if (m_instance == null) {
            m_instance = new BLLRead();
        }
        return m_instance;
    }

    public void setDAL(IDALRead d) {
        dalRead = d;
    }

    /**
     * Reads all recent incidents and adds them to the array
     *
     * @return array of recent incidents
     */
    public ArrayList<BEIncident> readAllRecentIncidents() {
        try {
            recentIncidents = dalRead.readRecentIncidents();
            if (recentIncidents.isEmpty()) {
                BLLError.getInstance().emptyArrayList();
            } else {
                readIncidentDetails();
                readIncidentUsage();
                readIncidentRoleTime();
            }
        } catch (SQLException ex) {
            BLLError.getInstance().readRecentIncidentError();
        }
        return recentIncidents;
    }

    /**
     * Reads all details about the incidents.
     *
     */
    public void readIncidentDetails() {
        try {
            incidentDetails = dalRead.readIncidentDetails();
        } catch (SQLException ex) {
            BLLError.getInstance().readIncidentDetailsError();
        }
    }

    /**
     * Reads all usage for the incidents.
     *
     */
    public void readIncidentUsage() {
        try {
            incidentUsage = dalRead.readUsage();
        } catch (SQLException ex) {
            BLLError.getInstance().readUsageError();
        }
    }

    /**
     * Reads all role/time for the incidents.
     *
     */
    public void readIncidentRoleTime() {
        try {
            incidentRoleTime = dalRead.readRoleTime();
        } catch (SQLException ex) {
            BLLError.getInstance().readRoleTimeError();
        }
    }

    /**
     * Reads all alarmtypes and adds them to the array
     *
     * @return array of alarm types
     */
    public ArrayList<BEAlarm> readAllAlarms() {
        if (allAlarms == null) {
            try {
                allAlarms = dalRead.readAlarms();
            } catch (SQLException ex) {
                System.out.println(ex);
                BLLError.getInstance().readAlarmError();
            }
        }
        return allAlarms;
    }

    /**
     * Reads all zipcodes and adds them to the array
     *
     * @return array of zipcodes
     */
    public ArrayList<BEZipcode> readAllZipcodes() {
        if (allZipcodes == null) {
            try {
                allZipcodes = dalRead.readZipcodes();
            } catch (SQLException ex) {
                BLLError.getInstance().readZipcodeError();
            }
        }
        return allZipcodes;
    }

    /**
     * Reads all firemen and adds them to the array
     *
     * @return array of firemen
     */
    public ArrayList<BEFireman> readAllFiremen() {
        if (allFiremen == null) {
            try {
                allFiremen = dalRead.readFiremen();
            } catch (SQLException ex) {
                BLLError.getInstance().readFiremenError();
            }
        }
        return allFiremen;
    }

    /**
     * Reads all vehicles and adds them to the array
     *
     * @return array of vehicles
     */
    public ArrayList<BEVehicle> readAllVehicles() {
        if (allVehicles == null) {
            try {
                allVehicles = dalRead.readVehicles();
            } catch (SQLException ex) {
                BLLError.getInstance().readVehiclesError();
            }
        }
        return allVehicles;
    }

    /**
     * Reads all material and adds it to the array
     *
     * @return array of material
     */
    public ArrayList<BEMaterial> readAllMaterials() {
        if (allMaterials == null) {
            try {
                allMaterials = dalRead.readMaterial();
            } catch (SQLException ex) {
                BLLError.getInstance().readMaterialError();
            }
        }
        return allMaterials;
    }

    /**
     * Clears all arrays of details for an incident.
     */
    public void clearDetailsArray() {
        incidentDetails = null;
        incidentUsage = null;
        incidentRoleTime = null;
    }

    /**
     *
     * @return current array of incidentDetails.
     */
    public ArrayList<BEIncidentDetails> returnIncidentDetails() {
        return incidentDetails;
    }

    /**
     *
     * @return current array of incidentUsage.
     */
    public ArrayList<BEUsage> returnIncidentUsage() {
        return incidentUsage;
    }

    /**
     *
     * @return current array of incidentRoleTime.
     */
    public ArrayList<BERoleTime> returnIncidentRoleTime() {
        return incidentRoleTime;
    }

    /**
     * Adds a new given fireman to the array.
     *
     * @param newFireman
     */
    public void addFiremanToArray(BEFireman newFireman) {
        allFiremen.add(newFireman);
    }

    /**
     * Adds a new given vehicle to the array.
     *
     * @param newVehicle
     */
    public void addVehicleToArray(BEVehicle newVehicle) {
        allVehicles.add(newVehicle);
    }

    /**
     * Adds some new given material to the array.
     *
     * @param newMaterial
     */
    public void addMaterialToArray(BEMaterial newMaterial) {
        allMaterials.add(newMaterial);
    }

    /**
     * Updates values for a fireman in the fireman array.
     *
     * @param fireman
     */
    public void updateFiremanArray(BEFireman fireman) {
        for (BEFireman firemanArray : readAllFiremen()) {
            if (firemanArray.getM_id() == fireman.getM_id()) {
                firemanArray.setM_recruited(fireman.getM_recruited());
                firemanArray.setM_firstName(fireman.getM_firstName());
                firemanArray.setM_lastName(fireman.getM_lastName());
                firemanArray.setM_address(fireman.getM_address());
                firemanArray.setM_zipCode(fireman.getM_zipCode());
                firemanArray.setM_phone(fireman.getM_phone());
                firemanArray.setM_paymentNumber(fireman.getM_paymentNumber());
                firemanArray.setM_isTeamLeader(fireman.isM_isTeamLeader());
                firemanArray.setM_photoPath(fireman.getM_photoPath());
                break;
            }
        }
    }

    /**
     * Updates values for a vehicle in the vehicle array.
     *
     * @param vehicle
     */
    public void updateVehicleArray(BEVehicle vehicle) {
        for (BEVehicle vehicleArray : readAllVehicles()) {
            if (vehicleArray.getM_odinNumber() == vehicle.getM_odinNumber()) {
                vehicleArray.setM_registrationNumber(vehicle.getM_registrationNumber());
                vehicleArray.setM_brand(vehicle.getM_brand());
                vehicleArray.setM_model(vehicle.getM_model());
                vehicleArray.setM_description(vehicle.getM_description());
                break;
            }
        }
    }

    /**
     * Updates values for a material in the material array.
     *
     * @param material
     */
    public void updateMaterialArray(BEMaterial material) {
        for (BEMaterial materialArray : readAllMaterials()) {
            if (materialArray.getM_id() == material.getM_id()) {
                materialArray.setM_description(material.getM_description());
                break;
            }
        }
    }

}
