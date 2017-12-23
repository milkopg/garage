package com.development.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.development.model.ParkingLevel;

@Repository("parkingLevelDao")
public class ParkingLevelDaoImpl extends AbstractDao<Integer, ParkingLevel> implements ParkingLevelDao {

	public ParkingLevel getById(int id) {
		return getByKey(id);
	}

	public ParkingLevel getByName(String name) {
		ParkingLevel parkingLevel = (ParkingLevel) getEntityManager()
				.createQuery("SELECT o from ParkingLevel o where o.levelName = :name")
				.setParameter("name", name)
				.getResultList();
		return parkingLevel;
	}

	public void save(ParkingLevel parkingLevel) {
		persist(parkingLevel);
	}

	public void update(ParkingLevel parkingLevel) {
		merge(parkingLevel);
	}

	public void deleteByName(String name) {
		ParkingLevel parkingLevel = getByName(name);
		if (parkingLevel != null) {
			delete(parkingLevel);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ParkingLevel> getAllParkingLevels() {
		List<ParkingLevel> parkingLevels = getEntityManager()
				.createQuery("SELECT o FROM ParkingLevel o ORDER BY o.levelName")
				.getResultList();
		return parkingLevels;
	}

}
