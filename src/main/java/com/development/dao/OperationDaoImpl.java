package com.development.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.development.model.Operation;
import com.development.model.ViewGarageStatus;

@Repository("operationDao")
public class OperationDaoImpl extends AbstractDao<Long, Operation> implements OperationDao {

	public Operation getById(long id) {
		return getByKey(id);	
	}

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationsAfterTimeEnter(Date date) {
		List<Operation> operations = getEntityManager()
				.createQuery("SELECT o FROM OPERATION o where o.timeEnter < :date")
				.setParameter("date", date, TemporalType.DATE)
				.getResultList();
		return operations;
	}

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationsBeforeTimeEnter(Date date) {
		List<Operation> operations = getEntityManager()
				.createQuery("SELECT o FROM OPERATION o where o.timeEnter > :date")
				.setParameter("date", date, TemporalType.DATE)
				.getResultList();
		return operations;
	}
	
	@SuppressWarnings("unchecked")
	public List<Operation> getOperationsAfterTimeExit(Date date) {
		List<Operation> operations = getEntityManager()
				.createQuery("SELECT o FROM OPERATION o where o.timeExit < :date")
				.setParameter("date", date, TemporalType.DATE)
				.getResultList();
		return operations;
	}

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationsBeforeTimeExit(Date date) {
		List<Operation> operations = getEntityManager()
				.createQuery("SELECT o FROM OPERATION o where o.timeExit > :date")
				.setParameter("date", date, TemporalType.DATE)
				.getResultList();
		return operations;
	}

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationsByPlateNumber(String plateNumber) {
		 List<Operation> operations = getEntityManager()
				 .createQuery("SELECT o FROM Operation o where o.vehicle.plateNumber LIKE :plateNumber ORDER BY o.id DESC")
				 .setParameter("plateNumber", plateNumber)
				 .getResultList();
		return operations;		
	}

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationsByParkingLotName(String name) {
		 List<Operation> operations = getEntityManager()
				 .createQuery("SELECT o FROM Operation o where o.parkingLot.name LIKE :name")
				 .setParameter("name", name)
				 .getResultList();
		return operations;
	}

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationsByParkingLevelName(String name) {
		 List<Operation> operations = getEntityManager()
				 .createQuery("SELECT o FROM Operation o where o.parkingLot.parkingLevel.levelName LIKE :name")
				 .setParameter("name", name)
				 .getResultList();
		return operations;
	}

	public void save(Operation operation) {
		persist(operation);
	}

	public void update(Operation operation) {
		merge(operation);
	}

	public void deleteById(long id) {
		Operation operation = getById(id);
		if (operation != null) {
			delete(operation);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationsByPlateNumberForEnteredVehicles(String plateNumber) {
		 List<Operation> operations = getEntityManager()
				 .createQuery("SELECT o FROM Operation o where o.vehicle.plateNumber LIKE :plateNumber  AND o.timeExit is null")
				 .setParameter("plateNumber", plateNumber)
				 .getResultList();
		return operations;
	}

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationsByPlateNumberForExitedVehicles(String plateNumber) {
		 List<Operation> operations = getEntityManager()
				 .createQuery("SELECT o FROM Operation o where o.vehicle.plateNumber LIKE :plateNumber  AND o.timeEnter IS NOT NULL and o.timeExit IS NOT NULL")
				 .setParameter("plateNumber", plateNumber)
				 .getResultList();
		return operations;
	}
	
	public boolean isVehicleInParking(String plateNumber) {
		if (plateNumber == null) return false;
		List<Operation> operations = getOperationsByPlateNumberForEnteredVehicles(plateNumber);
		return operations != null && operations.size() > 0;
	}

	public boolean isVehicleAlreadyExit(String plateNumber) {
		List<Operation> operationsFinished = getOperationsByPlateNumberForExitedVehicles(plateNumber);
		List<Operation> allOperations = getOperationsByPlateNumber(plateNumber);
		return operationsFinished != null && allOperations != null ? operationsFinished.size() != allOperations.size() : false;
	}

}
