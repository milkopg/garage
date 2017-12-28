package com.development.service;

import java.util.List;

import com.development.model.ParkingLevel;

public interface ParkingLevelService {
	/**
	 * get ParkingLevel by database id
	 * @param id database id
	 * @return found ParkingLevel
	 */
	ParkingLevel getById(int id);
	
	/**
	 * get ParkingLevel by name
	 * @param name of parkingLevel
	 * @return found ParkingLeve;
	 */
	ParkingLevel getByName(String name);
	
	/**
	 * Save parking level
	 * @param parkingLevel which should be saved
	 */
	void save(ParkingLevel parkingLevel);
	
	/**
	 * Update parking level
	 * @param parkingLevel which should be updated
	 */
	void update(ParkingLevel parkingLevel);
	
	/**
	 * Delete parking level by name
	 * @param name of parking level for delete
	 */
	void deleteByName(String name);
	
	/**
	 * Get all Parking levels
	 * @return List<ParkingLevel>
	 */
	List<ParkingLevel> getAllParkingLevels();
	
	/**
	 * create initial data
	 */
	void initData();
}
