package DAL;

import BE.BEIncidentDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DALUpdate {

    private static DALUpdate m_instance;
    Connection m_connection;

    private DALUpdate() {
        m_connection = DB_Connection.getInstance().getConnection();
    }

    public static DALUpdate getInstance() {
        if (m_instance == null) {
            m_instance = new DALUpdate();
        }
        return m_instance;
    }
    
    public void updateIncidentDetails(BEIncidentDetails incidentDetails) throws SQLException{
        String sql = "Update IncidentDetails set incidentLeader = ?, "
                + "evaNumber = ?, "
                + "fireReport = ?, "
                + "involvedName = ?, "
                + "involvedAddress = ?, "
                + "remark = ?, "
                + "alarmId = ?,"
                + "detectorNumber = ?,"
                + "groupNumber = ? "
                + "where incidentId = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setString(1, incidentDetails.getM_incidentLeader());
        ps.setString(2, incidentDetails.getM_evaNumber());
        ps.setString(3, incidentDetails.getM_fireReport());
        ps.setString(4, incidentDetails.getM_involvedName());
        ps.setString(5, incidentDetails.getM_involvedAddress());
        ps.setString(6, incidentDetails.getM_remark());
        ps.setInt(7, incidentDetails.getM_alarm().getM_id());
        ps.setString(8, incidentDetails.getM_detectorNumber());
        ps.setString(9, incidentDetails.getM_groupNumber());
        ps.setInt(10, incidentDetails.getM_incident().getM_id());
        ps.executeUpdate();
    }

}
