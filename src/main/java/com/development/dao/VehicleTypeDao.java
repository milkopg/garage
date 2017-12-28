package com.development.dao;

import java.util.List;

import com.development.model.VehicleType;

public interface VehicleTypeDao {
	VehicleType getById(int id);
	VehicleType getByName(String name);
	void save(VehicleType vehicleType);
	void update (VehicleType vehicleType);
	void deleteByVehicleTypeName(String name);
	List<VehicleType> getAllVehicleTypes();
}
