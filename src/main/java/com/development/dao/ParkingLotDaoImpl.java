package com.development.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.development.model.ParkingLot;
import com.development.model.GarageStatus;
import com.development.model.ParkingLevel;

@Repository("parkingLotDao")
public class ParkingLotDaoImpl extends AbstractDao<Integer, ParkingLot> implements ParkingLotDao {

	public ParkingLot getById(int id) {
		return getByKey(id);
	}

	public ParkingLot getByName(String name) {
		ParkingLot parkingLot = (ParkingLot) getEntityManager()
				.createQuery("SELECT o FROM ParkingLot o where o.name = :name").setParameter("name", name)
				.getSingleResult();
		return parkingLot;
	}

	@SuppressWarnings("unchecked")
	public List<ParkingLot> getLotsByParkingLevel(int parkingLevelId) {
		List<ParkingLot> parkingLots = getEntityManager()
				.createQuery("SELECT o from ParkingLot o where o.parkingLevel.id=:parkingLevelId")
				.setParameter("parkingLevelId", parkingLevelId)
				.getResultList();
		return parkingLots;
	}

	@SuppressWarnings("unchecked")
	public List<ParkingLot> getFreeLotsByParkingLevel(int parkingLevelId) {
		List<ParkingLot> parkingLots = getEntityManager()
				.createQuery("SELECT o from ParkingLot o where o.parkingLevel.id=:parkingLevelId AND o.isFree = 1")
				.setParameter("parkingLevelId", parkingLevelId)
				.getResultList();
		return parkingLots;
	}

	@SuppressWarnings("unchecked")
	public List<ParkingLot> getUsedLotsByParkingLevel(int parkingLevelId) {
		List<ParkingLot> parkingLots = getEntityManager()
				.createQuery("SELECT o from ParkingLot o where o.parkingLevel.id=:parkingLevelId AND o.isFree = 0")
				.setParameter("parkingLevelId", parkingLevelId)
				.getResultList();
		return parkingLots;
	}

	@SuppressWarnings("unchecked")
	public List<ParkingLot> getAllLots() {
		List<ParkingLot> parkingLots = getEntityManager()
				.createQuery("SELECT o FROM ParkingLot o order by o.name")
				.getResultList();
		return parkingLots;
	}

	public void save(ParkingLot parkingLot) {
		persist(parkingLot);
	}

	public void update(ParkingLot parkingLot) {
		merge(parkingLot);
	}

	public void deleteByName(String name) {
		ParkingLot parkingLot = getByName(name);
		if (parkingLot != null) {
			delete(parkingLot);
		}
	}

	@SuppressWarnings("unchecked")
	public List<GarageStatus> getGerageStatus() {
		final String NATIVE_SQL = "SELECT  `level`.`LEVEL_NAME` AS `level_name`,  `level`.`CAPACITY` AS `capacity`,  SUM(`t_parking_lot`.`IS_FREE`) AS `free`,  (level.CAPACITY - SUM(`t_parking_lot`.`IS_FREE`)) AS `used` FROM (`t_parking_lot`  LEFT JOIN `t_parking_level` `level`    ON ((`t_parking_lot`.`PARKING_LEVEL_ID` = `level`.`ID`))) WHERE (`t_parking_lot`.`IS_FREE` = 1 ) GROUP BY `t_parking_lot`.`PARKING_LEVEL_ID`;";
		EntityManager entityManager = getEntityManager();
		entityManager.clear();
		List<GarageStatus> garageStatus = entityManager
				.createNativeQuery(NATIVE_SQL, GarageStatus.class)
				.getResultList();
		return garageStatus;
	}

	@SuppressWarnings("unchecked")
	public void deleteByParkingLevel(ParkingLevel parkingLevel) {
		Integer id = parkingLevel.getId();
		List<ParkingLot> parkingLots = getEntityManager()
				.createQuery("SELECT o FROM ParkingLot o where o.ParkingLevel.id =:id ")
				.setParameter("id", id)
				.getResultList();
		for (ParkingLot lot : parkingLots) {
			delete(lot);
		}
	}
}
