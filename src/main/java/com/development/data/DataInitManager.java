package com.development.data;

import org.springframework.transaction.annotation.Transactional;

import com.development.model.ParkingLevel;
import com.development.model.ParkingLot;
import com.development.service.ParkingLotService;

public class DataInitManager {
	private ParkingLotService parkingLotService;
	
	public DataInitManager(ParkingLotService parkingLotService) {
		this.parkingLotService = parkingLotService;
	}
	
	@Transactional
	public void initData(int startNumber, ParkingLevel parkingLevel) {
		for (int i=1; i <= parkingLevel.getCapacity() ; i++) {
			ParkingLot parkingLot = new ParkingLot();
			parkingLot.setIsFree(true);
			parkingLot.setName(startNumber + i + "");
			parkingLot.setParkingLevel(parkingLevel);
			parkingLotService.save(parkingLot);
		}
	}
}	
