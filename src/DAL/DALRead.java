package DAL;

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
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

public class DALRead {

    private static DALRead m_instance;
    Connection m_connection;

    ArrayList<BEIncidentType> resIncidentTypes;
    ArrayList<BEAlarm> resAlarms;
    ArrayList<BEMaterial> resMaterials;
    ArrayList<BERole> resRoles;
    ArrayList<BEFireman> resFiremen;
    ArrayList<BEZipcode> resZipcodes;
    ArrayList<BEVehicle> resVehicles;

    private DALRead() {
        m_connection = DB_Connection.getInstance().getConnection();
    }

    public static DALRead getInstance() {
        if (m_instance == null) {
            m_instance = new DALRead();
        }
        return m_instance;
    }

    /**
     * Creates an ArrayList of Incidents where isDone = false
     *
     * @return ArrayList of Incidents
     * @throws SQLException
     */
    public ArrayList<BEIncident> readRecentIncidents() throws SQLException {
        ArrayList<BEIncident> res = new ArrayList<>();
        Statement stm = m_connection.createStatement();
        stm.execute("select * from Incident where Incident.isDone = 0");
        ResultSet result = stm.getResultSet();
        while (result.next()) {
            int id = result.getInt("id");
            String incidentName = result.getString("incidentName");
            Date date = result.getDate("date");
            Time time = result.getTime("startTime");
            int incidentTypeId = result.getInt("incidentTypeId");
            BEIncidentType refIncidentType = null;
            for (BEIncidentType beincidenttype : readIncidentTypes()) {
                if (beincidenttype.getM_id() == incidentTypeId) {
                    refIncidentType = beincidenttype;
                }
            }
            boolean isDone = result.getBoolean("isDone");
            BEIncident be = new BEIncident(id, incidentName, date, time, refIncidentType, isDone);
            res.add(be);
        }
        return res;
    }

    /**
     * Creates an ArrayList of IncidentTypes
     *
     * @return ArrayList of Incident Types
     * @throws SQLException
     */
    public ArrayList<BEIncidentType> readIncidentTypes() throws SQLException {
        if (resIncidentTypes == null) {
            resIncidentTypes = new ArrayList<>();
            Statement stm = m_connection.createStatement();
            stm.execute("select * from IncidentType");
            ResultSet result = stm.getResultSet();
            while (result.next()) {
                int id = result.getInt("id");
                String description = result.getString("incidentTypeDescription");
                BEIncidentType be = new BEIncidentType(id, description);
                resIncidentTypes.add(be);
            }
        }
        return resIncidentTypes;
    }

    /**
     * Creates an ArrayList of Alarms
     *
     * @return ArrayList of Alarms
     * @throws SQLException
     */
    public ArrayList<BEAlarm> readAlarms() throws SQLException {
        if (resAlarms == null) {
            resAlarms = new ArrayList<>();
            Statement stm = m_connection.createStatement();
            stm.execute("select * from Alarm");
            ResultSet result = stm.getResultSet();
            while (result.next()) {
                int id = result.getInt("id");
                String description = result.getString("alarmDescription");
                BEAlarm be = new BEAlarm(id, description);
                resAlarms.add(be);
            }
        }
        return resAlarms;
    }

    /**
     * Creates an ArrayList of Material
     *
     * @return ArrayList of Material
     * @throws SQLException
     */
    public ArrayList<BEMaterial> readMaterial() throws SQLException {
        if (resMaterials == null) {
            resMaterials = new ArrayList<>();
            Statement stm = m_connection.createStatement();
            stm.execute("select * from Material order by materialDescription");
            ResultSet result = stm.getResultSet();
            while (result.next()) {
                int id = result.getInt("id");
                String description = result.getString("materialDescription");
                BEMaterial be = new BEMaterial(id, description);
                resMaterials.add(be);
            }
        }
        return resMaterials;

    }

    /**
     * Creates an ArrayList of Roles
     *
     * @return ArrayList of Roles
     * @throws SQLException
     */
    public ArrayList<BERole> readRoles() throws SQLException {
        if (resRoles == null) {
            resRoles = new ArrayList<>();
            Statement stm = m_connection.createStatement();
            stm.execute("select * from Role");
            ResultSet result = stm.getResultSet();
            while (result.next()) {
                int id = result.getInt("id");
                String description = result.getString("roleDescription");
                BERole be = new BERole(id, description);
                resRoles.add(be);
            }
        }
        return resRoles;
    }

    /**
     * Creates an ArrayList of Vehicles
     *
     * @return an ArrayList of Vehicles
     * @throws SQLException
     */
    public ArrayList<BEVehicle> readVehicles() throws SQLException {
        if (resVehicles == null) {
            resVehicles = new ArrayList<>();
            Statement stm = m_connection.createStatement();
            stm.execute("select * from Vehicle");
            ResultSet result = stm.getResultSet();
            while (result.next()) {
                int odinNumber = result.getInt("odinNumber");
                String registrationNumber = result.getString("registrationNumber");
                String brand = result.getString("brand");
                String model = result.getString("model");
                String description = result.getString("vehicleDescription");
                boolean isReady = result.getBoolean("isReady");
                BEVehicle be = new BEVehicle(odinNumber, registrationNumber, brand, model, description, isReady);
                resVehicles.add(be);
            }
        }
        return resVehicles;
    }

    /**
     * Creates an ArrayList of Firemen
     *
     * @return ArrayList of Firemen
     * @throws SQLException
     */
    public ArrayList<BEFireman> readFiremen() throws SQLException {
        if (resFiremen == null) {
            resFiremen = new ArrayList<>();
            Statement stm = m_connection.createStatement();
            stm.execute("select * from Fireman order by lastName, firstname");
            ResultSet result = stm.getResultSet();
            while (result.next()) {
                int id = result.getInt("id");
                Date recruitDate = result.getDate("recruited");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String address = result.getString("address");
                int zip = result.getInt("zipCode");
                BEZipcode refZipCode = null;
                for (BEZipcode bezipcode : readZipcodes()) {
                    if (bezipcode.getM_zipCode() == zip) {
                        refZipCode = bezipcode;
                    }
                }
                int phone = result.getInt("phone");
                int paymentNumber = result.getInt("paymentNumber");
                boolean isTeamLeader = result.getBoolean("isTeamLeader");
                String photoPath = result.getString("photoPath");
                BEFireman be = new BEFireman(id, recruitDate, firstName, lastName, address, refZipCode, phone, paymentNumber, isTeamLeader, photoPath);
                resFiremen.add(be);
            }
        }
        return resFiremen;
    }

    /**
     * Creates an ArrayList of Zipcodes
     *
     * @return ArrayList of Zipcodes
     * @throws SQLException
     */
    public ArrayList<BEZipcode> readZipcodes() throws SQLException {
        if (resZipcodes == null) {
            resZipcodes = new ArrayList<>();
            Statement stm = m_connection.createStatement();
            stm.execute("select * from [zip/city]");
            ResultSet result = stm.getResultSet();
            while (result.next()) {
                int zipcode = result.getInt("zipcode");
                String city = result.getString("city");
                BEZipcode be = new BEZipcode(zipcode, city);
                resZipcodes.add(be);
            }
        }
        return resZipcodes;
    }

    /**
     * Creates an ArrayList of Usage
     *
     * @return ArrayList of Usage
     * @throws SQLException
     */
    public ArrayList<BEUsage> readUsage() throws SQLException {
        ArrayList<BEUsage> res = new ArrayList<>();
        Statement stm = m_connection.createStatement();
        stm.execute("select * from Usage "
                + "inner join Incident "
                + "on Usage.incidentId = Incident.id "
                + "where Incident.isDone = 0");
        ResultSet result = stm.getResultSet();
        while (result.next()) {
            int id = result.getInt("id");
            int materialId = result.getInt("materialId");
            BEMaterial refMaterial = null;
            for (BEMaterial be : readMaterial()) {
                if (be.getM_id() == materialId) {
                    refMaterial = be;
                }
            }
            int amount = result.getInt("amount");
            int incidentId = result.getInt("incidentId");
            BEIncident refIncident = null;
            for (BEIncident be : readRecentIncidents()) {
                if (be.getM_id() == incidentId) {
                    refIncident = be;
                }
            }
            BEUsage be = new BEUsage(id, refMaterial, amount, refIncident);
            res.add(be);
        }

        return res;

    }

    /**
     * Creates an ArrayList of IncidentDetails
     *
     * @return ArrayList of IncidentDetails
     * @throws SQLException
     */
    public ArrayList<BEIncidentDetails> readIncidentDetails() throws SQLException {
        ArrayList<BEIncidentDetails> res = new ArrayList<>();
        Statement stm = m_connection.createStatement();
        stm.execute("Select IncidentDetails.incidentLeader, "
                + "IncidentDetails.evaNumber, "
                + "IncidentDetails.fireReport, "
                + "IncidentDetails.incidentid, "
                + "IncidentDetails.involvedName, "
                + "IncidentDetails.involvedAddress, "
                + "IncidentDetails.remark, "
                + "IncidentDetails.alarmId,"
                + "IncidentDetails.detectorNumber,"
                + "IncidentDetails.groupNumber "
                + "from IncidentDetails inner join Incident "
                + "on IncidentDetails.incidentId = incident.id "
                + "where incident.isDone = 0");
        ResultSet result = stm.getResultSet();
        while (result.next()) {
            String incidentLeader = result.getString("incidentLeader");
            String evaNumber = result.getString("evaNumber");
            String fireReport = result.getString("fireReport");
            int incidentId = result.getInt("incidentId");
            BEIncident refIncident = null;
            for (BEIncident be : readRecentIncidents()) {
                if (be.getM_id() == incidentId) {
                    refIncident = be;
                    break;
                }
            }
            String involvedName = result.getString("involvedName");
            String involvedAddress = result.getString("involvedAddress");
            String remark = result.getString("remark");
            int alarmId = result.getInt("alarmId");
            BEAlarm refAlarm = null;
            for (BEAlarm be : readAlarms()) {
                if (be.getM_id() == alarmId) {
                    refAlarm = be;
                    break;
                }
            }
            String detectorNumber = result.getString("detectorNumber");
            String groupNumber = result.getString("groupNumber");

            BEIncidentDetails be = new BEIncidentDetails(incidentLeader,
                    evaNumber,
                    fireReport,
                    refIncident,
                    involvedName,
                    involvedAddress,
                    remark,
                    refAlarm,
                    detectorNumber,
                    groupNumber);
            res.add(be);
        }
        return res;

    }

    /**
     * Creates an ArrayList of RoleTimes
     *
     * @return ArrayList of RoleTimes
     * @throws SQLException
     */
    public ArrayList<BERoleTime> readRoleTime() throws SQLException {
        ArrayList<BERoleTime> res = new ArrayList<>();
        Statement stm = m_connection.createStatement();
        stm.execute("select [Role/Time].incidentId, "
                + "[Role/Time].firemanId, "
                + "[Role/Time].isOnStation, "
                + "[Role/Time].roleId, "
                + "[Role/Time].vehicleOdinNumber, "
                + "[Role/Time].hours \n"
                + "from [Role/Time] inner join Incident "
                + "on [Role/Time].incidentId = Incident.id "
                + "where Incident.isDone = 0");
        ResultSet result = stm.getResultSet();
        while (result.next()) {
            int incidentid = result.getInt("incidentId");
            BEIncident refIncident = null;
            for (BEIncident be : readRecentIncidents()) {
                if (be.getM_id() == incidentid) {
                    refIncident = be;
                }
            }

            int firemanid = result.getInt("firemanId");
            BEFireman refFireman = null;
            for (BEFireman be : readFiremen()) {
                if (be.getM_id() == firemanid) {
                    refFireman = be;
                }
            }

            boolean isOnStation = result.getBoolean("isOnStation");
            int roleid = result.getInt("roleId");
            BERole refRole = null;
            for (BERole be : readRoles()) {
                if (be.getM_id() == roleid) {
                    refRole = be;
                }
            }

            int vehicleodinnumber = result.getInt("vehicleOdinNumber");
            BEVehicle refVehicle = null;
            for (BEVehicle be : readVehicles()) {
                if (be.getM_odinNumber() == vehicleodinnumber) {
                    refVehicle = be;
                }
            }

            int hours = result.getInt("hours");

            BERoleTime be = new BERoleTime(refIncident,
                    refFireman,
                    isOnStation,
                    refRole,
                    refVehicle,
                    hours);
            res.add(be);
        }
        return res;
    }

}
