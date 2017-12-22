package com.development.service;

import java.util.List;

import com.development.model.Vehicle;

public interface VehicleService {
	Vehicle getById(int id);
	Vehicle getByPlateNumber(String plateNumber);
	void save(Vehicle vehicle);
	void update (Vehicle vehicle);
	void deleteByPlateNumber(String plateNumber);
	List<Vehicle> getAllVehicles();
}
