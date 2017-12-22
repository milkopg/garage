package com.development.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.development.model.ParkingLevel;

@Repository("parkingLevelDao")
public class ParkingLevelDaoImpl extends AbstractDao<Integer, ParkingLevel> implements ParkingLevelDao {

	@Override
	public ParkingLevel getById(int id) {
		return getByKey(id);
	}

	@Override
	public ParkingLevel getByName(String name) {
		ParkingLevel parkingLevel = (ParkingLevel) getEntityManager()
				.createQuery("SELECT o from ParkingLevel o where o.name = :name")
				.setParameter("name", name)
				.getResultList();
		return parkingLevel;
	}

	@Override
	public void save(ParkingLevel parkingLevel) {
		persist(parkingLevel);
	}

	@Override
	public void update(ParkingLevel parkingLevel) {
		merge(parkingLevel);
	}

	@Override
	public void deleteByName(String name) {
		ParkingLevel parkingLevel = getByName(name);
		if (parkingLevel != null) {
			delete(parkingLevel);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingLevel> getAllParkingLevels() {
		List<ParkingLevel> parkingLevels = getEntityManager()
				.createQuery("SELECT o FROM ParkingLevel o ORDER BY o.name")
				.getResultList();
		return parkingLevels;
	}

}
