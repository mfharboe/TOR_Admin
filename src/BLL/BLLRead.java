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
import java.sql.SQLException;
import java.util.ArrayList;

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

    /**
     * Reads all recent incidents and adds them to the array
     *
     * @return array of recent incidents
     */
    public ArrayList<BEIncident> readAllRecentIncidents() {
        try {
            recentIncidents = DALRead.getInstance().readRecentIncidents();
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
     * Reads all details about an incident and adds it to the array
     *
     * @return array of incident details
     */
    public ArrayList<BEIncidentDetails> readIncidentDetails() {
        if (incidentDetails == null) {
            try {
                incidentDetails = DALRead.getInstance().readIncidentDetails();
            } catch (SQLException ex) {
                BLLError.getInstance().readIncidentDetailsError();
            }
        }
        return incidentDetails;
    }

    /**
     * Reads all usage for an incidents and adds it to the array
     *
     * @return array of usage
     */
    public ArrayList<BEUsage> readIncidentUsage() {
        if (incidentUsage == null) {
            try {
                incidentUsage = DALRead.getInstance().readUsage();
            } catch (SQLException ex) {
                BLLError.getInstance().readUsageError();
            }
        }
        return incidentUsage;
    }

    /**
     * Reads all role/time for an incident and adds it to the array
     *
     * @return array of role/time
     */
    public ArrayList<BERoleTime> readIncidentRoleTime() {
        if (incidentRoleTime == null) {
            try {
                incidentRoleTime = DALRead.getInstance().readRoleTime();
            } catch (SQLException ex) {
                BLLError.getInstance().readRoleTimeError();
            }
        }
        return incidentRoleTime;

    }

    /**
     * Reads all alarmtypes and adds them to the array
     *
     * @return array of alarm types
     */
    public ArrayList<BEAlarm> readAllAlarms() {
        if (allAlarms == null) {
            try {
                allAlarms = DALRead.getInstance().readAlarms();
            } catch (SQLException ex) {
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
                allZipcodes = DALRead.getInstance().readZipcodes();
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
                allFiremen = DALRead.getInstance().readFiremen();
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
                allVehicles = DALRead.getInstance().readVehicles();
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
                allMaterials = DALRead.getInstance().readMaterial();
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
     * Removes a given fireman from the array.
     *
     * @param fireman
     */
    public void removeFiremenFromArray(BEFireman fireman) {
        allFiremen.remove(fireman);
    }

    /**
     * Removes a given vehicle from the array.
     *
     * @param vehicle
     */
    public void removeVehicleFromArray(BEVehicle vehicle) {
        allVehicles.remove(vehicle);
    }

    /**
     * Removes some given material from the array.
     *
     * @param material
     */
    public void removeMaterialFromArray(BEMaterial material) {
        allMaterials.remove(material);
    }

}
