
package DAL.Interfaces;

import BE.BEAlarm;
import BE.BEFireman;
import BE.BEIncident;
import BE.BEIncidentDetails;
import BE.BEIncidentType;
import BE.BEMaterial;
import BE.BERole;
import BE.BERoleTime;
import BE.BEUsage;
import BE.BEVehicle;
import BE.BEZipcode;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDALRead {

    /**
     * Reads all alarmTypes and adds them to an array.
     *
     * @return ArrayList of Alarms
     * @throws SQLException
     */
    ArrayList<BEAlarm> readAlarms() throws SQLException;

    /**
     * Reads all firemen and adds them to an array.
     *
     * @return ArrayList of Firemen
     * @throws SQLException
     */
    ArrayList<BEFireman> readFiremen() throws SQLException;

    /**
     * Reads all IncidentDetails of incidents that is not finsihed, and adds
     * them to an array.
     *
     * @return ArrayList of IncidentDetails
     * @throws SQLException
     */
    ArrayList<BEIncidentDetails> readIncidentDetails() throws SQLException;

    /**
     * Reads incidentsTypes and adds them to an array.
     *
     * @return ArrayList of IncidentTypes
     * @throws SQLException
     */
    ArrayList<BEIncidentType> readIncidentTypes() throws SQLException;

    /**
     * Reads all materials and adds them to an array.
     *
     * @return Ordered ArrayList of Material
     * @throws SQLException
     */
    ArrayList<BEMaterial> readMaterial() throws SQLException;

    /**
     * Reads all incidents from DB that is not finished, and adds them to an
     * array.
     *
     * @return ArrayList of Incidents
     * @throws SQLException
     */
    ArrayList<BEIncident> readRecentIncidents() throws SQLException;

    /**
     * Reads all RoleTime of Incidents that is not finished.
     *
     * @return Ordered ArrayList of RoleTimes
     * @throws SQLException
     */
    ArrayList<BERoleTime> readRoleTime() throws SQLException;

    /**
     * Reads all roleTypes and adds them to an array.
     *
     * @return ArrayList of Roles
     * @throws SQLException
     */
    ArrayList<BERole> readRoles() throws SQLException;

    /**
     * Reads all usage from incidents that is not finished and adds it to an
     * array.
     *
     * @return ArrayList of Usage
     * @throws SQLException
     */
    ArrayList<BEUsage> readUsage() throws SQLException;

    /**
     * Reads all vehicles and adds them to an array.
     *
     * @return an ArrayList of Vehicles
     * @throws SQLException
     */
    ArrayList<BEVehicle> readVehicles() throws SQLException;

    /**
     * Reads all zipCodes and adds them to an array.
     *
     * @return ArrayList of Zipcodes
     * @throws SQLException
     */
    ArrayList<BEZipcode> readZipcodes() throws SQLException;
    
}
