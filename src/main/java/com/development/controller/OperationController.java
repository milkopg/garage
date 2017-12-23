package com.development.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.development.model.Operation;
import com.development.model.ParkingLevel;
import com.development.model.ParkingLot;
import com.development.model.Vehicle;
import com.development.model.VehicleType;
import com.development.service.OperationService;
import com.development.service.ParkingLevelService;
import com.development.service.ParkingLotService;
import com.development.service.VehicleTypeService;

@Controller
@RequestMapping("/")
public class OperationController {

	@Autowired
	OperationService operationService;
	
	@Autowired
	ParkingLevelService parkingLevelService;
	
	@Autowired
	ParkingLotService parkingLotService;
	
	@Autowired
	VehicleTypeService vehicleTypeService;
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView home() {
		initData();
		List<ParkingLevel> parkingLevels = parkingLevelService.getAllParkingLevels(); 
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("parkingLevel", parkingLevels.get(0));
		mav.addObject("operation", new Operation());
		mav.addObject("vehicle", new Vehicle());
		mav.addObject("vehicleType", new VehicleType());
		return mav;
	}
	
	 @ModelAttribute("parkingLevels")
	 public List<ParkingLevel> getParkingLevelList() {
	   List<ParkingLevel> parkingLevelList = parkingLevelService.getAllParkingLevels();
	   return parkingLevelList;
	 }
	 
	 @ModelAttribute("vehicleTypes")
	 public List<VehicleType> getVehicleTypes() {
	   List<VehicleType> vehicleTypeList = vehicleTypeService.getAllVehicleTypes();
	   return vehicleTypeList;
	 }

	@Transactional
	private void initData() {
		List<ParkingLot> parkingLots = parkingLotService.getAllLots();
		List<ParkingLevel> parkingLevels = parkingLevelService.getAllParkingLevels();
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
	}
	
	private void initParkingLevel() {
		ParkingLevel parkingLevel = new ParkingLevel();
		parkingLevel.setCapacity(100);
		parkingLevel.setName("Level1");
		parkingLevelService.save(parkingLevel);
	}

	@RequestMapping(value = "process", method = RequestMethod.POST)
	public ModelAndView process(@Valid Operation operation, @Valid Vehicle vehicle, BindingResult result, ModelMap modelMap) {
		ModelAndView model = new ModelAndView("operation");
		operation.getParkingLot();
		return model;
	}
}
	