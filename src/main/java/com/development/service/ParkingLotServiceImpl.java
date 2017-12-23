package com.development.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.development.dao.ParkingLotDao;
import com.development.model.ParkingLot;

@Service("parkingLotService")
@Transactional
public class ParkingLotServiceImpl implements ParkingLotService {

	@Autowired
	ParkingLotDao dao;
	
	public ParkingLot getById(int id) {
		return dao.getById(id);
	}

	public ParkingLot getByName(String name) {
		return dao.getByName(name);
	}

	public List<ParkingLot> getLotsByParkingLevel(int parkingLevelId) {
		return dao.getLotsByParkingLevel(parkingLevelId);
	}

	public List<ParkingLot> getFreeLotsByParkingLevel(int parkingLevelId) {
		return dao.getLotsByParkingLevel(parkingLevelId);
	}

	public List<ParkingLot> getUsedLotsByParkingLevel(int parkingLevelId) {
		return dao.getUsedLotsByParkingLevel(parkingLevelId);
	}

	public List<ParkingLot> getAllLots() {
		return dao.getAllLots();
	}

	public void save(ParkingLot parkingLot) {
		if (parkingLot == null) return;
		dao.save(parkingLot);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void update(ParkingLot parkingLot) {
		if (parkingLot == null) return;
		ParkingLot dbParkingLot = dao.getById(parkingLot.getId());
		if (dbParkingLot != null) {
			dbParkingLot.setIsFree(parkingLot.getIsFree());
			dbParkingLot.setName(parkingLot.getName());
			dbParkingLot.setParkingLevel(parkingLot.getParkingLevel());	
			dao.update(parkingLot);
		}
	}

	public void deleteByName(String name) {
		dao.deleteByName(name);
	}

}
