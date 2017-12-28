package com.development.service;

import java.util.List;

import com.development.model.Vehicle;

public interface VehicleService {
	/**
	 * Get vehicle by given id
	 * @param id database id
	 * @return found Vehicle
	 */
	Vehicle getById(int id);
	
	/**
	 * Get vehicle by given vehicle plate number
	 * @param plateNumber of vehicle
	 * @return Vehicle
	 */
	Vehicle getByPlateNumber(String plateNumber);
	
	/**
	 * Save vehicle
	 * @param vehicle which should saved
	 */
	void save(Vehicle vehicle);
	
	/**
	 * Update vehicle
	 * @param vehicle which should be updated
	 */
	void update (Vehicle vehicle);
	
	/**
	 * Delete vehicle by given plate number
	 * @param plateNumber to be deleted
	 */
	void deleteByPlateNumber(String plateNumber);
	
	/**
	 * Get List<Vehicle> of all vehicles
	 * @return
	 */
	List<Vehicle> getAllVehicles();
}
