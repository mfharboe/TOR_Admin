/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAL.Interfaces;

import BE.BEFireman;
import BE.BEMaterial;
import BE.BEVehicle;
import java.sql.SQLException;

/**
 *
 * @author Michael
 */
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
