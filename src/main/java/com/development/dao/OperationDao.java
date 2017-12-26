package com.development.dao;

import java.util.Date;
import java.util.List;

import com.development.model.Operation;

public interface OperationDao {
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
	 * get List<Operation> before time enter
	 * @param date enter 
	 * @return List<Operation>
	 */
	public List<Operation> getOperationsBeforeTimeEnter(Date date);
	
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
	List<Operation> getOperationsByPlateNumberForEnteredVehicles(String plateNumber);
	
	/**
	 * get List<Operation> by plate vehicle number for exited vehicles
	 * @param plateNumber of vehicle
	 * @return List<Operation>
	 */
	List<Operation> getOperationsByPlateNumberForExitedVehicles(String plateNumber);
	
	/**
	 * get List<Operation> by vehicleType name
	 * @param name of vehicle type i.e. car , Motorbile
	 * @return
	 */
	List<Operation> getOperationsByVehicleTypeName(String name);
	
	/**
	 * Checking if vehicle is already in parking by given plate number
	 * @param plateNumber of checking vehicle
	 * @return true if vehicle is in parking , otherwise false
	 */
	boolean isVehicleInParking(String plateNumber);
	
	/**
	 * Save Operation to database
	 * @param operation to be saved
	 */
	void save(Operation operation);
	
	/**
	 * Update operation
	 * @param operation to be updated
	 */
	void update(Operation operation);
	
	/**
	 * Delete operation by operation id
	 * @param operationId which should be deleted
	 */
	void deleteById(long operationId);
}