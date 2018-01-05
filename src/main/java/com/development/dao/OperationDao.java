package com.development.dao;

import java.util.Date;
import java.util.List;

import com.development.model.Operation;

public interface OperationDao {
	Operation getById(long id);
	List<Operation> getOperationsAfterTimeEnter(Date date);
	List<Operation> getOperationsAfterTimeExit(Date date);
	List<Operation> getOperationsBeforeTimeExit(Date date);
	public List<Operation> getOperationsBeforeTimeEnter(Date date);
	List<Operation> getOperationsByPlateNumber(String plateNumber);
	List<Operation> getOperationsByParkingLotName(String name);
	List<Operation> getOperationsByParkingLevelName(String name);
	List<Operation> getOperationsByPlateNumberForEnteredVehicles(String plateNumber);
	List<Operation> getOperationsByPlateNumberForExitedVehicles(String plateNumber);
	List<Operation> getOperationsByVehicleTypeName(String name);
	boolean isVehicleInParking(String plateNumber);
	void save(Operation operation);
	void update(Operation operation);
	void deleteById(long operationId);
}