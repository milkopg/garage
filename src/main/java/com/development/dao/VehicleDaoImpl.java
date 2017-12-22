package com.development.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.development.model.Vehicle;

@Repository("vehicleDao")
public class VehicleDaoImpl extends AbstractDao<Integer, Vehicle> implements VehicleDao {

	public Vehicle getById(int id) {
		Vehicle vehicle = getByKey(id);
		return vehicle;
	}

	public Vehicle getByPlateNumber(String plateNumber) {
		Vehicle vehicle = null;
		try {
			vehicle = (Vehicle) getEntityManager()
					.createQuery("SELECT o FROM Vehicle o WHERE o.plateNumber LIKE :plateNumber")
					.setParameter("plateNumber", plateNumber).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return vehicle;
	}

	public void save(Vehicle vehicle) {
		persist(vehicle);
	}
	
	public void update(Vehicle entity) {
		merge(entity);
	}

	public void deleteByPlateNumber(String plateNumber) {
		Vehicle vehicle = null;
		try {
			vehicle = (Vehicle) getEntityManager()
					.createQuery("SELECT o FROM Vehicle o WHERE o.plateNumber LIKE :plateNumber")
					.setParameter("plateNumber", plateNumber).getSingleResult();
		} catch (NoResultException e) {
			return;
		}
		delete(vehicle);
	}

	@SuppressWarnings("unchecked")
	public List<Vehicle> getAllVehicles() {
		List<Vehicle> vehicles = getEntityManager()
				.createQuery("SELECT o FROM Vehicle o order by plateNumber asc")
				.getResultList();
		return vehicles;
	}
}
