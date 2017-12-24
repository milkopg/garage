package com.development.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.development.general.Constants;
import com.development.model.Operation;
import com.development.model.OperationType;
import com.development.model.ParkingLevel;
import com.development.model.ParkingLot;
import com.development.model.Vehicle;
import com.development.model.VehicleType;
import com.development.model.ViewGarageStatus;
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
	
	@Autowired
	MessageSource messageSource;
	
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
	 
	 @ModelAttribute("garageStatus")
	 public List<ViewGarageStatus> getParkingLots() {
	   List<ViewGarageStatus> garageStatuses = parkingLotService.getGerageStatus();
	   return garageStatuses;
	 }
	 
	@Transactional
	private void initData() {
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

	@RequestMapping(value = "home", method = RequestMethod.POST)
	public ModelAndView process(@Valid Operation operation, @Valid Vehicle vehicle, @Valid VehicleType vehicleType, 
			@Valid ParkingLevel parkingLevel, BindingResult result, ModelMap modelMap) {
		ModelAndView model =  new ModelAndView("home");
		if (vehicle.getPlateNumber() == null) {
			return model;
		}
		int operationType = OperationType.valueOf(operation.getType()).getValue();
		if (operationType == -1) return model;
		
		validateEmptyVehicleNumber(model, vehicle);
		validateCarAlreadyEntered(model, vehicle, operationType);
		//validateCarExitTwice(model, vehicle, operationType);
		if (model.getModelMap().containsKey(Constants.ERROR_MESSAGE_OBJECT_NAME)) {
			return model;
		}
		model = new ModelAndView("home");
		processOperation(modelMap, operationType, vehicle, vehicleType);
		return model;
	}

	
	

	private void processOperation(ModelMap modelMap, int operationType, Vehicle vehicle, VehicleType vehicleType) {
		switch (OperationType.valueOf(operationType)) {
		case ENTER:
			VehicleType vType = vehicleTypeService.getByName(vehicleType.getName());
			operationService.enterCar(vehicle.getPlateNumber(), vType);
			modelMap.addAttribute("garageStatus", getParkingLots());
			break;
		case EXIT:
			operationService.exitCar(vehicle.getPlateNumber());
			modelMap.addAttribute("garageStatus", getParkingLots());
			break;
		case STATUS:
			List<Operation> operations = operationService.getOperationsByPlateNumber(vehicle.getPlateNumber());
			modelMap.addAttribute("operations", operations);
			break;
		case DEVICE_UNKNOWN:
			break;
		}
	}
	
	private void validateEmptyVehicleNumber(ModelAndView model, Vehicle vehicle) {
		if (vehicle == null || vehicle.getPlateNumber() == null || vehicle.getPlateNumber().length() < 2) {
		    model.addObject(Constants.ERROR_MESSAGE_OBJECT_NAME, messageSource.getMessage("NotEmpty.vehicle.plateNumber", null, Locale.getDefault()));
		}
	}
	
	private void validateCarAlreadyEntered(ModelAndView model, Vehicle vehicle, int operationType) {
		if (OperationType.ENTER.getValue() == operationType 
				&& operationService.isVehicleInParking(vehicle.getPlateNumber())) {
			model.addObject(Constants.ERROR_MESSAGE_OBJECT_NAME, messageSource.getMessage("vehicle.alreadyEntered", null, Locale.getDefault()));
		}
	}
	
	private void validateCarExitTwice(ModelAndView model, Vehicle vehicle, int operationType) {
		if (OperationType.EXIT.getValue() == operationType 
				&& operationService.isVehicleAlreadyExit(vehicle.getPlateNumber())) {
			model.addObject(Constants.ERROR_MESSAGE_OBJECT_NAME, messageSource.getMessage("vehicle.exit.twice", null, Locale.getDefault()));
		}
	}
}
	
