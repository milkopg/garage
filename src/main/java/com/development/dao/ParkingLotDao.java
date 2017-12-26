package com.development.dao;

import java.util.List;

import com.development.model.ParkingLot;
import com.development.model.GarageStatus;
import com.development.model.ParkingLevel;

public interface ParkingLotDao {
	/**
	 * Get parking lot by database id
	 * @param id databaseID
	 * @return found ParkingLot
	 */
	ParkingLot getById(int id);
	
	/**
	 * Get parking lot by ParkingLot name
	 * @param name of parking lot which is searched for
	 * @return found ParkingLot object
	 */
	ParkingLot getByName(String name);
	
	/**
	 * Get List<ParkingLot> by parkingLevelId
	 * @param parkingLevelId filtered by
 	 * @return found List<ParkingLot>
	 */
	List<ParkingLot> getLotsByParkingLevel(int parkingLevelId);
	
	/**
	 * Get free List<ParkingLot> filtered by parking level
	 * @param parkingLevelId filtered by
	 * @return found List<ParkingLot>
	 */
	List<ParkingLot> getFreeLotsByParkingLevel(int parkingLevelId);
	
	/**
	 * Get Used ParkingLot<List> filtered by parkingLevel
	 * @param parkingLevelId filtered by
	 * @return found List<ParkingLot>
	 */
	List<ParkingLot> getUsedLotsByParkingLevel(int parkingLevelId);
	
	/**
	 * Get all List<ParkingLot> for all parking levels
	 * @return List<ParkingLot>
	 */
	List<ParkingLot> getAllLots();
	
	/**
	 * Get garage status of all parking levels
	 * @return
	 */
	List<GarageStatus> getGerageStatus();
	
	/**
	 * Save parkingLot
	 * @param parkingLot which should be saved
	 */
	void save (ParkingLot parkingLot);
	
	/**
	 * Update parking lot
	 * @param parkingLot which should be updated
	 */
	void update (ParkingLot parkingLot);
	
	/**
	 * Delete parking lot by given name
	 * @param name of parking lot to be deleted
	 */
	void deleteByName(String name);
	
	/**
	 * Delete lot by parking level. Loops over lots and delete it.
	 * @param parkingLevel which should be filtered
	 */
	void deleteByParkingLevel(ParkingLevel parkingLevel);
	
}
