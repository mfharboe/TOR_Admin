
package DAL.Interfaces;

import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import java.sql.SQLException;

public interface IDALCreate {

    /**
     * Create a new given fireman in the DB.
     * @param fireman
     * @throws SQLException
     */
    void createFireman(BEFireman fireman) throws SQLException;

    /**
     * Create a new given material in the DB.
     * @param material
     * @throws SQLException
     */
    void createMaterial(BEMaterial material) throws SQLException;

    /**
     * Create a new given vehicle in the DB.
     * @param vehicle
     * @throws SQLException
     */
    void createVehicle(BEVehicle vehicle) throws SQLException;
    
}
