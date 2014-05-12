package DAL;

import BE.BEAlarm;
import BE.BEIncident;
import BE.BEIncidentDetails;
import BE.BEIncidentType;
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

    ArrayList<BEIncidentType> resIncidentType;
    ArrayList<BEAlarm> resAlarms;

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

    public ArrayList<BEIncident> readIncidentsByDate(Date searchDate) throws SQLException {
        ArrayList<BEIncident> res = new ArrayList<>();
        Statement stm = m_connection.createStatement();
        stm.execute("select * from Incident where Incident.date = " + searchDate);
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
        if (resIncidentType == null) {
            resIncidentType = new ArrayList<>();
            Statement stm = m_connection.createStatement();
            stm.execute("select * from IncidentType");
            ResultSet result = stm.getResultSet();
            while (result.next()) {
                int id = result.getInt("id");
                String description = result.getString("incidentTypeDescription");
                BEIncidentType be = new BEIncidentType(id, description);
                resIncidentType.add(be);
            }
        }
        return resIncidentType;
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
                + "IncidentDetails.[message], "
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
            String message = result.getString("message");
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
                    message,
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

}
