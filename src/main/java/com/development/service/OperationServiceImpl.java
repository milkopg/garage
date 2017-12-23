package com.development.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.development.dao.OperationDao;
import com.development.dao.ParkingLevelDao;
import com.development.dao.ParkingLotDao;
import com.development.dao.VehicleDao;
import com.development.model.Operation;
import com.development.model.ParkingLevel;
import com.development.model.ParkingLot;
import com.development.model.Vehicle;
import com.development.model.VehicleType;

@Service("operationService")
@Transactional
public class OperationServiceImpl implements OperationService {
	
	@Autowired
	OperationDao dao;
	
	@Autowired
	VehicleDao daoVehicle;
	
	@Autowired
	ParkingLevelDao daoParkingLevel;
	
	@Autowired
	ParkingLotDao daoParkingLot;

	public Operation getById(long id) {
		return dao.getById(id);
	}

	public List<Operation> getOperationsAfterTimeEnter(Date date) {
		return dao.getOperationsAfterTimeEnter(date);
	}

	public List<Operation> getOperationsBeforeTimeEnter(Date date) {
		return dao.getOperationsBeforeTimeEnter(date);
	}

	public List<Operation> getOperationsAfterTimeExit(Date date) {
		return dao.getOperationsAfterTimeExit(date);
	}

	public List<Operation> getOperationsBeforeTimeExit(Date date) {
		return dao.getOperationsBeforeTimeExit(date);
	}

	public List<Operation> getOperationsByPlateNumber(String plateNumber) {
		return dao.getOperationsByPlateNumber(plateNumber);
	}

	public List<Operation> getOperationsByParkingLotName(String name) {
		return dao.getOperationsByParkingLotName(name);
	}

	public List<Operation> getOperationsByParkingLevelName(String name) {
		return dao.getOperationsByParkingLevelName(name);
	}

	public void save(Operation operation) {
		if (operation == null) return;
		dao.save(operation);
	}

	public void update(Operation operation) {
		if (operation == null) return;
		Operation dbOperation = dao.getById(operation.getId());
		if (dbOperation != null) {
			dbOperation.setParkingLot(operation.getParkingLot());
			dbOperation.setTimeEnter(operation.getTimeEnter());
			dbOperation.setTimeExit(operation.getTimeExit());
			dbOperation.setVehicle(operation.getVehicle());
			dao.update(dbOperation);
		}

	}

	public void deleteById(long operationId) {
		dao.deleteById(operationId);
	}

	public void enterCar(String plateNumber, VehicleType vehicleType) {
		if (plateNumber == null || vehicleType == null) return;
		Vehicle vehicle = daoVehicle.getByPlateNumber(plateNumber);
		if (vehicle == null) {
			vehicle = new Vehicle();
			vehicle.setPlateNumber(plateNumber);
			vehicle.setVehicleType(vehicleType);
			daoVehicle.save(vehicle);
		}
		Operation operation = new Operation();
		operation.setParkingLot(takeFirstAvailableParkingLot());
		operation.setTimeEnter(new Date());
		operation.setVehicle(vehicle);
		dao.save(operation);
	}

	public int exitCar(String plateNumber) {
		List<Operation> operations = dao.getOperationsByPlateNumberForEnteredVehicles(plateNumber);
		if (operations == null || operations.isEmpty()) return -1;
		Operation operation = operations.get(0);
		operation.getParkingLot().setIsFree(true);
		operation.setTimeExit(new Date());
		daoParkingLot.update(operation.getParkingLot());
		dao.update(operation);
		
		int hours = calculateHours(operation);
		return hours;
	}

	private int calculateHours(Operation operation) {
		final int MILLI_TO_HOUR = 1000 * 60 * 60;
		return (int) (operation.getTimeExit().getTime() - operation.getTimeEnter().getTime()) / MILLI_TO_HOUR;
	}

	public ParkingLot takeFirstAvailableParkingLot() {
		List<ParkingLevel> parkingLevels = daoParkingLevel.getAllParkingLevels();
		for (ParkingLevel parkingLevel : parkingLevels) {
			List<ParkingLot> parkingLots = daoParkingLot.getFreeLotsByParkingLevel(parkingLevel.getId());
			for (ParkingLot freeParkingLot : parkingLots) {
				if (freeParkingLot.getIsFree()) {
					freeParkingLot.setIsFree(false);
					daoParkingLot.update(freeParkingLot);
					return freeParkingLot;
				}
			}
		}
		return null; 
	}

	public boolean isVehicleInParking(String plateNumber) {
		return dao.isVehicleInParking(plateNumber);
	}

	public boolean isVehicleAlreadyExit(String plateNumber) {
		return dao.isVehicleAlreadyExit(plateNumber);
	}
}
