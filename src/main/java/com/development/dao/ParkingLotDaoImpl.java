package com.development.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.development.model.ParkingLot;

@Repository("parkingLotDao")
public class ParkingLotDaoImpl extends AbstractDao<Integer, ParkingLot> implements ParkingLotDao {

	@Override
	public ParkingLot getById(int id) {
		return getByKey(id);
	}

	@Override
	public ParkingLot getByName(String name) {
		ParkingLot parkingLot = (ParkingLot) getEntityManager()
				.createQuery("SELECT o FROM ParkingLot o where o.name = :name").setParameter("name", name)
				.getSingleResult();
		return parkingLot;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingLot> getLotsByParkingLevel(int parkingLevelId) {
		List<ParkingLot> parkingLots = getEntityManager()
				.createQuery("SELECT o from ParkingLot o where o.parkingLevel.id=:parkingLevelId")
				.setParameter("parkingLevelId", parkingLevelId)
				.getResultList();
		return parkingLots;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingLot> getFreeLotsByParkingLevel(int parkingLevelId) {
		List<ParkingLot> parkingLots = getEntityManager()
				.createQuery("SELECT o from ParkingLot o where o.parkingLevel.id=:parkingLevelId AND o.isFree = 1")
				.setParameter("parkingLevelId", parkingLevelId)
				.getResultList();
		return parkingLots;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingLot> getUsedLotsByParkingLevel(int parkingLevelId) {
		List<ParkingLot> parkingLots = getEntityManager()
				.createQuery("SELECT o from ParkingLot o where o.parkingLevel.id=:parkingLevelId AND o.isFree = 0")
				.setParameter("parkingLevelId", parkingLevelId)
				.getResultList();
		return parkingLots;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingLot> getAllLots() {
		List<ParkingLot> parkingLots = getEntityManager()
				.createQuery("SELECT o FROM ParkingLot order by o.name")
				.getResultList();
		return parkingLots;
	}

	@Override
	public void save(ParkingLot parkingLot) {
		persist(parkingLot);
	}

	@Override
	public void update(ParkingLot parkingLot) {
		merge(parkingLot);
	}

	@Override
	public void deleteByName(String name) {
		ParkingLot parkingLot = getByName(name);
		if (parkingLot != null) {
			delete(parkingLot);
		}
	}
}
