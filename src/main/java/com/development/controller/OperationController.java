package com.development.controller;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.development.data.DataInitManager;
import com.development.general.Constants;
import com.development.model.GarageStatus;
import com.development.model.Operation;
import com.development.model.OperationType;
import com.development.model.ParkingLevel;
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
	
	@Autowired
	MessageSource messageSource;
	
	@PostConstruct
	public void initIt() throws Exception {
		DataInitManager dataManager = new DataInitManager(parkingLevelService, parkingLotService, vehicleTypeService);
		dataManager.initData();
	}
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView home() {
	
		
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
	 public List<GarageStatus> getParkingLots() {
	   List<GarageStatus> garageStatuses = parkingLotService.getGerageStatus();
	   return garageStatuses;
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
	
