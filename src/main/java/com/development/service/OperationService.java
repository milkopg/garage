package com.development.service;

import java.util.Date;
import java.util.List;

import com.development.model.Operation;
import com.development.model.ParkingLot;
import com.development.model.VehicleType;

public interface OperationService {
	Operation getById(long id);
	List<Operation> getOperationsAfterTimeEnter(Date date);
	List<Operation> getOperationsBeforeTimeEnter(Date date);
	List<Operation> getOperationsAfterTimeExit(Date date);
	List<Operation> getOperationsBeforeTimeExit(Date date);
	List<Operation> getOperationsByPlateNumber(String plateNumber);
	List<Operation> getOperationsByParkingLotName(String name);
	List<Operation> getOperationsByParkingLevelName(String name);
	void enterCar(String plateNumber, VehicleType vehicleType);
	int exitCar (String plateNumber);
	ParkingLot takeFirstAvailableParkingLot();
	void save(Operation operation);
	void update(Operation operation);
	void deleteById(long operationId);
}
