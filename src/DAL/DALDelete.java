package DAL;

import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DALDelete {

    private static DALDelete m_instance;
    Connection m_connection;

    private DALDelete() {
        m_connection = DB_Connection.getInstance().getConnection();
    }

    public static DALDelete getInstance() {
        if (m_instance == null) {
            m_instance = new DALDelete();
        }
        return m_instance;
    }

    public void deleteFireman(BEFireman fireman) throws SQLException {
        String sql = "delete from Fireman "
                + "where Fireman.id = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setInt(1, fireman.getM_id());
        ps.executeUpdate();
    }

    public void deleteVehicle(BEVehicle vehicle) throws SQLException {
        String sql = "delete from Vehicle "
                + "where Vehicle.odinNumber = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setInt(1, vehicle.getM_odinNumber());
        ps.executeUpdate();
    }

    public void deleteMaterial(BEMaterial material) throws SQLException {
        String sql = "delete from Material "
                + "where Material.id = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setInt(1, material.getM_id());
        ps.executeUpdate();
    }

}
