package com.development.service;

import java.util.Date;
import java.util.List;

import com.development.model.Operation;
import com.development.model.ParkingLevel;
import com.development.model.ParkingLot;
import com.development.model.VehicleType;

public interface OperationService {
	
	/**
	 * Get Opepation by database id
	 * @param id
	 * @return found Operation
	 */
	Operation getById(long id);
	
	/**
	 * get List of Operations after Date of time enter
	 * @param date after operations should be loaded
	 * @return List<Operation>
	 */
	
	
	List<Operation> getOperationsAfterTimeEnter(Date date);
	
	
	/**
	 * get List<Operation> before time enter
	 * @param date enter 
	 * @return List<Operation>
	 */
	
	List<Operation> getOperationsBeforeTimeEnter(Date date);
	
	/** get List of Operations after Date for time exit
	 * @param date after car are exit
	 * @return List<Operation>
	 */
	List<Operation> getOperationsAfterTimeExit(Date date);
	
	/**
	 * get List<Operation> before TimeExit date
	 * @param date of TimeExit
	 * @return List<Operation>
	 */
	List<Operation> getOperationsBeforeTimeExit(Date date);
	
	/**
	 * Get List<Opertions> by given vehicle plate number
	 * @param plateNumber of current vehicle
	 * @return List<Operation>
	 */
	List<Operation> getOperationsByPlateNumber(String plateNumber);
	
	
	/**
	 * Get List<Operation> by parking lot name
	 * @param name of parkingLot
	 * @return List<Operation>
	 */
	List<Operation> getOperationsByParkingLotName(String name);
	
	
	/**
	 * get List<Operation> by given parking level name
	 * @param name of ParkingLevel
	 * @return List<Operation>
	 */
	List<Operation> getOperationsByParkingLevelName(String name);
	
	
	/**
	 * get List<Operation> by plate number only for entered, but not exit vehicles
 	 * @param plateNumber of vehicle
	 * @return List<Operation>
	 */
	List<Operation> getOperationsByVehicleTypeName(String name);
	
	/**
	 * Enter car operation, it takes a free space for specific vehicleType and parking level
	 * @param plateNumber of vehicle which is entered
	 * @param vehicleType type of vehicle
	 * @param parkingLevel which is entered
	 */
	void enterCar(String plateNumber, VehicleType vehicleType, ParkingLevel parkingLevel);
	
	/**
	 * Exit car operation. It return free current parking lot to current level
	 * @param plateNumber of vehicle which is exit
	 * @return number of hours spent in garage
	 */
	int exitCar (String plateNumber);
	
	/**
	 * Check if vehicle is still is parking
	 * @param plateNumber of vehicle
 	 * @return true if vehicle is in parking otherwise false
	 */
	boolean isVehicleInParking(String plateNumber);
	
	/**
	 * Taking first available parking lot for current parking level
	 * @param parkingLevel which should be parked in
	 * @return taken ParkingLot
	 */
	ParkingLot takeFirstAvailableParkingLot(ParkingLevel parkingLevel);
	
	/**
	 * Save database operation. Operation object should be a valid Entity
	 * @param operation for save
	 */
	
	
	void save(Operation operation);
	
	
	/**
	 * Update database operation. Operation object should be a valid Entity
	 * @param operation for update
	 */
	void update(Operation operation);
	
	/**
	 * Delete operation by id. Id must be a valid database id constraint
	 * @param operationId which should de deleted
	 */
	void deleteById(long operationId);
}
