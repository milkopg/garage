package com.development.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.development.dao.VehicleDao;
import com.development.model.Vehicle;

@Service("vehicleService")
@Transactional
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleDao dao;
	
	public Vehicle getById(int id) {
		return dao.getById(id);
	}

	public Vehicle getByPlateNumber(String plateNumber) {
		return dao.getByPlateNumber(plateNumber);
	}

	public void save(Vehicle vehicle) {
		dao.save(vehicle);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void update(Vehicle vehicle) {
		if (vehicle == null) return;
		Vehicle dbVehicle = getById(vehicle.getId());
		if (dbVehicle != null) {
			dbVehicle.setVehicleType(vehicle.getVehicleType());
			dbVehicle.setPlateNumber(vehicle.getPlateNumber());
		}
		dao.update(dbVehicle);
	}

	public void deleteByPlateNumber(String plateNumber) {
		dao.deleteByPlateNumber(plateNumber);	
	}

	public List<Vehicle> getAllVehicles() {
		return dao.getAllVehicles();
	}
}
