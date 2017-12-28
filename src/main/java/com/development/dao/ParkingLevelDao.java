package com.development.dao;

import java.util.List;

import com.development.model.ParkingLevel;

public interface ParkingLevelDao {
	ParkingLevel getById(int id);
	ParkingLevel getByName(String name);
	void save(ParkingLevel parkingLevel);
	void update(ParkingLevel parkingLevel);
	void deleteByName(String name);
	List<ParkingLevel> getAllParkingLevels();
}
