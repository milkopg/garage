package com.development.data;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.development.model.ParkingLevel;
import com.development.model.ParkingLot;
import com.development.model.VehicleType;
import com.development.service.ParkingLevelService;
import com.development.service.ParkingLotService;
import com.development.service.VehicleTypeService;

public class DataInitManager {
	private ParkingLevelService parkingLevelService;
	
	private ParkingLotService parkingLotService;
	
	private VehicleTypeService vehicleTypeService;
	
	public DataInitManager(ParkingLevelService parkingLevelService, ParkingLotService parkingLotService, VehicleTypeService vehicleTypeService) {
		this.parkingLevelService = parkingLevelService;
		this.parkingLotService = parkingLotService;
		this.vehicleTypeService = vehicleTypeService;
	}
	
	@Transactional
	public void initData() {
		List<ParkingLot> parkingLots = parkingLotService.getAllLots();
		List<ParkingLevel> parkingLevels = parkingLevelService.getAllParkingLevels();
		List<VehicleType> vehicleTypes = vehicleTypeService.getAllVehicleTypes();
		if (parkingLevels == null || parkingLevels.isEmpty())  {
			initParkingLevel();
			parkingLevels = parkingLevelService.getAllParkingLevels();
		}
		if (parkingLots == null || parkingLots.isEmpty()) {
			for (ParkingLevel parkingLevel : parkingLevels) {
				for (int i=1; i <= parkingLevel.getCapacity() ; i++) {
					ParkingLot parkingLot = new ParkingLot();
					parkingLot.setIsFree(true);
					parkingLot.setName(parkingLevel.getId() * 1000 + i + "");
					parkingLot.setParkingLevel(parkingLevel);
					parkingLotService.save(parkingLot);
				}
			}
		}
		
		if (vehicleTypes == null || vehicleTypes.isEmpty()) {
			initVehicleTypes();
		}
	}
	
	private void initVehicleTypes() {
		VehicleType vehicleType = new VehicleType();
		vehicleType.setName("Car");
		vehicleTypeService.save(vehicleType);
		vehicleType = new VehicleType();
		vehicleType.setName("MotorBike");
		vehicleTypeService.save(vehicleType);

	}

	private void initParkingLevel() {
		ParkingLevel parkingLevel = new ParkingLevel();
		parkingLevel.setCapacity(100);
		parkingLevel.setLevelName("Level1");
		parkingLevelService.save(parkingLevel);
	}
}	
