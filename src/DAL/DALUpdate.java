package DAL;

import BE.BEFireman;
import BE.BEIncident;
import BE.BEIncidentDetails;
import BE.BEMaterial;
import BE.BEVehicle;
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

    public void updateIncidentDetails(BEIncidentDetails incidentDetails) throws SQLException {
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
        if (incidentDetails.getM_alarm() == null) {
            ps.setString(7, null);
        } else {
            ps.setInt(7, incidentDetails.getM_alarm().getM_id());
        }
        ps.setString(8, incidentDetails.getM_detectorNumber());
        ps.setString(9, incidentDetails.getM_groupNumber());
        ps.setInt(10, incidentDetails.getM_incident().getM_id());
        ps.executeUpdate();
    }

    public void updateIncidentDone(BEIncident incident) throws SQLException {
        String sql = "Update Incident set isDone = ? "
                + "where id = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setBoolean(1, incident.isM_isDone());
        ps.setInt(2, incident.getM_id());
        ps.executeUpdate();
    }
    
    public void updateFireman(BEFireman fireman) throws SQLException{
        String sql = "Update Fireman set recruited = ?, "
                + "firstName = ?, "
                + "lastName = ?, "
                + "address = ?, "
                + "zipcode = ?, "
                + "phone = ?, "
                + "paymentNumber = ?, "
                + "isTeamLeader = ?, "
                + "photoPath = ? "
                + "where id = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setDate(1, fireman.getM_recruited());
        ps.setString(2, fireman.getM_firstName());
        ps.setString(3, fireman.getM_lastName());
        ps.setString(4, fireman.getM_address());
        ps.setInt(5, fireman.getM_zipCode().getM_zipCode());
        ps.setInt(6, fireman.getM_phone());
        ps.setInt(7, fireman.getM_paymentNumber());
        ps.setBoolean(8, fireman.isM_isTeamLeader());
        ps.setString(9, fireman.getM_photoPath());
        ps.setInt(10, fireman.getM_id());
        ps.executeUpdate();
    }
    
    public void updateVehicle(BEVehicle vehicle) throws SQLException{
        String sql = "Update Vehicle set registrationNumber = ?, "
                + "brand = ?, "
                + "model = ?, "
                + "vehicleDescription = ? "
                + "where odinNumber = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setString(1, vehicle.getM_registrationNumber());
        ps.setString(2, vehicle.getM_brand());
        ps.setString(3, vehicle.getM_model());
        ps.setString(4, vehicle.getM_description());
        ps.setInt(5, vehicle.getM_odinNumber());
        ps.executeUpdate();
    }
    
    public void updateMaterial(BEMaterial material) throws SQLException{
        String sql = "Update Material set materialDescription = ? "
                + "where id = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setString(1, material.getM_description());
        ps.setInt(2, material.getM_id());
        ps.executeUpdate();
    }

}
