package com.development.dao;

import java.util.List;

import com.development.model.VehicleType;

public interface VehicleTypeDao {
	/**
	 * Get vehicle type by given database id
	 * @param id database id
	 * @return found VehicleType
	 */
	VehicleType getById(int id);
	
	/**
	 * Get VehicleType by given name
	 * @param name of vehicle
	 * @return found VehicleType
	 */
	VehicleType getByName(String name);
	
	/**
	 * Save vehicle type
	 * @param vehicleType to be saved
	 */
	void save(VehicleType vehicleType);
	
	/**
	 * Update vehicle type
	 * @param vehicleType to be updated
	 */
	void update (VehicleType vehicleType);
	
	/**
	 * Delete vehicle type by given vehicle type name
	 * @param name of vehicleType which should be deleted
	 */
	void deleteByVehicleTypeName(String name);
	
	/**
	 * Get all vehicleType list
	 * @return
	 */
	List<VehicleType> getAllVehicleTypes();
}
