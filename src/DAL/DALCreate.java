package DAL;

import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DALCreate {

    private static DALCreate m_instance;
    Connection m_connection;

    private DALCreate() {
        m_connection = DB_Connection.getInstance().getConnection();
    }

    public static DALCreate getInstance() {
        if (m_instance == null) {
            m_instance = new DALCreate();
        }
        return m_instance;
    }

    public void createFireman(BEFireman fireman) throws SQLException {
        String sql = "insert into Fireman values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = m_connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, fireman.getM_recruited());
        ps.setString(2, fireman.getM_firstName());
        ps.setString(3, fireman.getM_lastName());
        ps.setString(4, fireman.getM_address());
        ps.setInt(5, fireman.getM_zipCode().getM_zipCode());
        ps.setInt(6, fireman.getM_phone());
        ps.setInt(7, fireman.getM_paymentNumber());
        ps.setBoolean(8, fireman.isM_isTeamLeader());
        ps.setString(9, fireman.getM_photoPath());
        ps.executeUpdate();
        ResultSet result = ps.getGeneratedKeys();
        while (result.next()) {
            fireman.setM_id(result.getInt(1));
        }
    }

    public void createVehicle(BEVehicle vehicle) throws SQLException {
        String sql = "insert into Vehicle values(?,?,?,?,?)";
        PreparedStatement ps = m_connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, vehicle.getM_odinNumber());
        ps.setString(2, vehicle.getM_registrationNumber());
        ps.setString(3, vehicle.getM_brand());
        ps.setString(4, vehicle.getM_model());
        ps.setString(5, vehicle.getM_description());
        ps.executeUpdate();
    }

    public void createMaterial(BEMaterial material) throws SQLException {
        String sql = "insert into Material values (?)";
        m_connection.setAutoCommit(false);
        m_connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        PreparedStatement ps = m_connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, material.getM_description());
        ps.executeUpdate();
        m_connection.commit();
        ResultSet result = ps.getGeneratedKeys();
        while (result.next()) {
            material.setM_id(result.getInt(1));
        }
        m_connection.setAutoCommit(true);
    }

    public void rollBack() {
        try {
            m_connection.rollback();
        } catch (SQLException ex) {
            
        }
    }
}
