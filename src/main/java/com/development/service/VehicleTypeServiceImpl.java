package com.development.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.development.dao.VehicleTypeDao;
import com.development.model.VehicleType;

@Service("vehicleTypeService")
@Transactional
public class VehicleTypeServiceImpl implements VehicleTypeService {
	
	@Autowired
	private VehicleTypeDao dao;
	
	public VehicleTypeServiceImpl() {
		//initData();
	}
	
	public VehicleType getById(int id) {
		return dao.getById(id);
	}

	public VehicleType getByName(String name) {
		return dao.getByName(name);
	}

	public void save(VehicleType vehicleType) {
		dao.save(vehicleType);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void update(VehicleType vehicleType) {
		if (vehicleType == null) return;
		VehicleType dbVehicleType = dao.getById(vehicleType.getId());
		if (dbVehicleType != null) {
			dbVehicleType.setName(vehicleType.getName());
		}
		dao.update(vehicleType);
	}

	public void deleteByVehicleTypeName(String name) {
		dao.deleteByVehicleTypeName(name);
	}

	public List<VehicleType> getAllVehicleTypes() {
		return dao.getAllVehicleTypes();
	}

	public void initData() {
		List<VehicleType> vehicleTypes = getAllVehicleTypes();
		if (vehicleTypes == null || vehicleTypes.isEmpty()) {
			VehicleType vehicleType = new VehicleType();
			vehicleType.setName("Car");
			dao.save(vehicleType);
			vehicleType = new VehicleType();
			vehicleType.setName("Motorbikes");
			dao.save(vehicleType);
		}
	}

}
