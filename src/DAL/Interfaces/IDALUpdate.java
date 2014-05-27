/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAL.Interfaces;

import BE.BEFireman;
import BE.BEIncident;
import BE.BEIncidentDetails;
import BE.BEMaterial;
import BE.BEVehicle;
import java.sql.SQLException;

/**
 *
 * @author Michael
 */
public interface IDALUpdate {

    /**
     * Updates a given fireman in the DB.
     * @param fireman
     * @throws SQLException
     */
    void updateFireman(BEFireman fireman) throws SQLException;

    /**
     * Updates given incidentDetails in the DB.
     * @param incidentDetails
     * @throws SQLException
     */
    void updateIncidentDetails(BEIncidentDetails incidentDetails) throws SQLException;

    /**
     * Updates a given Incident in the DB.
     * @param incident
     * @throws SQLException
     */
    void updateIncidentDone(BEIncident incident) throws SQLException;

    /**
     * Updates a given material in the DB.
     * @param material
     * @throws SQLException
     */
    void updateMaterial(BEMaterial material) throws SQLException;

    /**
     * Updates a given vehicle in the DB.
     * @param vehicle
     * @throws SQLException
     */
    void updateVehicle(BEVehicle vehicle) throws SQLException;
    
}
