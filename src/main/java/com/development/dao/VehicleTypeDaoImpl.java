package com.development.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.development.model.VehicleType;

@Repository("vehicleTypeDao")
public class VehicleTypeDaoImpl extends AbstractDao<Integer, VehicleType>implements VehicleTypeDao {

	public VehicleType getById(int id) {
		VehicleType vehicleType = getByKey(id);
		return vehicleType;
	}

	public VehicleType getByName(String name) {
		VehicleType vehicleType = (VehicleType) getEntityManager()
				.createQuery("SELECT o from VehicleType o where o.name LIKE :name")
				.setParameter("name", name)
				.getSingleResult();
		return vehicleType;
	}

	public void save(VehicleType vehicleType) {
		persist(vehicleType);
	}

	public void update(VehicleType vehicleType) {
		merge(vehicleType);;
	}

	public void deleteByVehicleTypeName(String name) {
		VehicleType vehicleType = getByName(name);
		if (vehicleType != null) {
			delete(vehicleType);
		}
	}

	@SuppressWarnings("unchecked")
	public List<VehicleType> getAllVehicleTypes() {
		List<VehicleType> vehicleTypes = getEntityManager()
				.createQuery("SELECT o from VehicleType ORDER BY o.name")
				.getResultList();
		return vehicleTypes;
	}

}
