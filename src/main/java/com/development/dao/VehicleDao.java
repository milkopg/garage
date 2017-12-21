package com.development.dao;

import java.util.List;

import com.development.model.Vehicle;

public interface VehicleDao {
	Vehicle findById(int id);
	Vehicle findByPlateNumber(String plateNumber);
	void save(Vehicle vehicle);
	void deleteByPlateNumber(String plateNumber);
	List<Vehicle> findAllVeacles();
}
