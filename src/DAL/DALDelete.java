package DAL;

import DAL.Interfaces.IDALDelete;
import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DALDelete implements IDALDelete {

    private static DALDelete m_instance;
    Connection m_connection;

    private DALDelete() {
        m_connection = DB_Connection.getInstance().getConnection();
    }

    /**
     * 
     * @return m_instance of DALDelete 
     */
    public static DALDelete getInstance() {
        if (m_instance == null) {
            m_instance = new DALDelete();
        }
        return m_instance;
    }

    /**
     * Deletes a given fireman from the DB.
     * @param fireman
     * @throws SQLException 
     */
    @Override
    public void deleteFireman(BEFireman fireman) throws SQLException {
        String sql = "delete from Fireman "
                + "where Fireman.id = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setInt(1, fireman.getM_id());
        ps.executeUpdate();
    }

    /**
     * Delete a given vehicle from the DB.
     * @param vehicle
     * @throws SQLException 
     */
    @Override
    public void deleteVehicle(BEVehicle vehicle) throws SQLException {
        String sql = "delete from Vehicle "
                + "where Vehicle.odinNumber = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setInt(1, vehicle.getM_odinNumber());
        ps.executeUpdate();
    }

    /**
     * Delete a given material from the DB.
     * @param material
     * @throws SQLException 
     */
    @Override
    public void deleteMaterial(BEMaterial material) throws SQLException {
        String sql = "delete from Material "
                + "where Material.id = ?";
        PreparedStatement ps = m_connection.prepareStatement(sql);
        ps.setInt(1, material.getM_id());
        ps.executeUpdate();
    }

}
