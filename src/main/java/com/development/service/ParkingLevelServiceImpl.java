package com.development.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.development.dao.ParkingLevelDao;
import com.development.dao.ParkingLotDao;
import com.development.model.ParkingLevel;
import com.development.model.ParkingLot;

@Service("parkingLevelService")
@Transactional
public class ParkingLevelServiceImpl implements ParkingLevelService {

	@Autowired
	ParkingLevelDao dao;
	
	@Autowired
	ParkingLotDao daoLot;
	
	public ParkingLevelServiceImpl() {
		//initData();
	}
	
	public ParkingLevel getById(int id) {
		return dao.getById(id);
	}

	public ParkingLevel getByName(String name) {
		return dao.getByName(name);
	}

	public void save(ParkingLevel parkingLevel) {
		if (parkingLevel == null) return;
		dao.save(parkingLevel);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void update(ParkingLevel parkingLevel) {
		if (parkingLevel == null) return;
		ParkingLevel dbParkingLevel = dao.getById(parkingLevel.getId());
		if (dbParkingLevel != null) {
			dbParkingLevel.setCapacity(parkingLevel.getCapacity());
			dbParkingLevel.setName(parkingLevel.getName());
		}
		dao.update(dbParkingLevel);

	}

	public void deleteByName(String name) {
		dao.deleteByName(name);
	}

	public List<ParkingLevel> getAllParkingLevels() {
		return dao.getAllParkingLevels();
	}

	public void initData() {
		List<ParkingLevel> parkingLevels = getAllParkingLevels();
		if (parkingLevels == null || parkingLevels.isEmpty()) {
			ParkingLevel defaultLevel = new ParkingLevel();
			defaultLevel.setCapacity(100);
			defaultLevel.setName("Level1");
			dao.save(defaultLevel);
			
			for (int i=0; i < 100; i ++) {
				ParkingLot parkingLot = new ParkingLot();
				parkingLot.setIsFree(true);
				parkingLot.setName((1000+i)+"");
				daoLot.save(parkingLot);
			}
		}

	}

}
