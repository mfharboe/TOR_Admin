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
public interface IDALDelete {

    /**
     * Deletes a given fireman from the DB.
     * @param fireman
     * @throws SQLException
     */
    void deleteFireman(BEFireman fireman) throws SQLException;

    /**
     * Delete a given material from the DB.
     * @param material
     * @throws SQLException
     */
    void deleteMaterial(BEMaterial material) throws SQLException;

    /**
     * Delete a given vehicle from the DB.
     * @param vehicle
     * @throws SQLException
     */
    void deleteVehicle(BEVehicle vehicle) throws SQLException;
    
}
