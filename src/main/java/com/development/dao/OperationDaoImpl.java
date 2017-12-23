package com.development.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.development.model.Operation;

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
				 .createQuery("SELECT o FROM Operation o where o.vehicle.plateNumber LIKE :plateNumber")
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
				 .createQuery("SELECT o FROM Operation o where o.parkingLot.parkingLevel.name LIKE :name")
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
				 .createQuery("SELECT o FROM Operation o where o.vehicle.plateNumber LIKE :plateNumber AND o.timeEnter IS NOT NULL AND o.timeEnter < :now AND o.timeExit IS NULL")
				 .setParameter("plateNumber", plateNumber)
				 .setParameter("now", new Date(), TemporalType.DATE)
				 .getResultList();
		return operations;
	}

	public boolean isVehicleInParking(String plateNumber) {
		if (plateNumber == null) return false;
		List<Operation> operations = getOperationsByPlateNumberForEnteredVehicles(plateNumber);
		return operations != null && operations.size() > 0;
	}
}
