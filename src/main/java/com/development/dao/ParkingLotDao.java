package com.development.dao;

import java.util.List;

import com.development.model.ParkingLot;
import com.development.model.ViewGarageStatus;

public interface ParkingLotDao {
	ParkingLot getById(int id);
	ParkingLot getByName(String name);
	List<ParkingLot> getLotsByParkingLevel(int parkingLevelId);
	List<ParkingLot> getFreeLotsByParkingLevel(int parkingLevelId);
	List<ParkingLot> getUsedLotsByParkingLevel(int parkingLevelId);
	List<ParkingLot> getAllLots();
	List<ViewGarageStatus> getGerageStatus();
	void save (ParkingLot parkingLot);
	void update (ParkingLot parkingLot);
	void deleteByName(String name);
	
}
