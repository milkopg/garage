package com.development.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.development.general.Constants;
import com.development.log4j.LoggerManager;
import com.development.model.GarageStatus;
import com.development.model.Operation;
import com.development.model.OperationType;
import com.development.model.ParkingLevel;
import com.development.model.ParkingLot;
import com.development.model.Vehicle;
import com.development.model.VehicleType;
import com.development.service.OperationService;
import com.development.service.ParkingLevelService;
import com.development.service.ParkingLotService;
import com.development.service.VehicleTypeService;

/**
 * Controller class which is responsible for all operations screen
 * @author Milko
 *
 */
@Controller
public class OperationController {
	
	private static final Logger logger = LoggerManager.getSystemLogger();
	private static final Logger userLogger = LoggerManager.getUserLogger();

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
	
	/**
	 * Default view, this is a home screen
	 * @return ModelAndView of home screen with loaded necessary objects
	 */
	@RequestMapping(value = { "/", Constants.SCREEN_HOME}, method = RequestMethod.GET)
	public ModelAndView home() {
		List<ParkingLevel> parkingLevels = parkingLevelService.getAllParkingLevels(); 
		ModelAndView mav = new ModelAndView(Constants.SCREEN_HOME);
		mav.addObject(Constants.MODEL_PARKINGL_LEVEL, parkingLevels != null && !parkingLevels.isEmpty() ? parkingLevels.get(0) : new ParkingLevel());
		mav.addObject(Constants.MODEL_OPERATION, new Operation());
		mav.addObject(Constants.MODEL_VEHICLE, new Vehicle());
		mav.addObject(Constants.MODEL_VEHICLE_TYPE, new VehicleType());
		return mav;
	}
	
	/**
	 * Load parkingLevelsList for attribute parkingLevels fetched from database
	 * @return List<Parking> parkingLevels
	 */
	 @ModelAttribute(Constants.MODEL_LIST_PARKING_LEVEL)
	 public List<ParkingLevel> getParkingLevelList() {
	   List<ParkingLevel> parkingLevelList = parkingLevelService.getAllParkingLevels();
	   return parkingLevelList;
	 }
	 
	/**
	 * Load vehicleTypeList for attribute vehicleTypes fetched from database
	 * @return List<VehicleType> vehicleTypeList
	 */
	 @ModelAttribute(Constants.MODEL_LIST_VEHICLE_TYPE)
	 public List<VehicleType> getVehicleTypes() {
	   List<VehicleType> vehicleTypeList = vehicleTypeService.getAllVehicleTypes();
	   return vehicleTypeList;
	 }
	 
	 /**
	  * Load GarageStatusList for attribute vehicleTypes fetched from database
	  * @return List<GarageStatus> garageStatusList
	  */
	 @ModelAttribute(Constants.MODEL_LIST_GARAGE_STATUS)
	 public List<GarageStatus> getParkingLots() {
	   List<GarageStatus> garageStatuses = parkingLotService.getGerageStatus();
	   return garageStatuses;
	 }
	 
	 /**
	  * Validate car status before submit form method of home screen . Here user can exit, enter, getStatus of current vehicle
	  * @param operation - getOperationType - exit (1), enter (2), status (3)
	  * @param vehicle - take vehicle plateNumber from vehicle object
	  * @param vehicleType - get vehicleType from vehicleType
	  * @param parkingLevel - current parking level to which operation	 should be performed
	  * @param result - to take result error from bind data
	  * @param modelMap or all mappedObjects. It added error messages on this object
	  * @return ModelAndView of processed data
	  */
	@RequestMapping(value = Constants.SCREEN_HOME, method = RequestMethod.POST)
	public ModelAndView process(@Valid Operation operation, Vehicle vehicle, @Valid VehicleType vehicleType, 
			@Valid ParkingLevel parkingLevel, BindingResult result, ModelMap modelMap) {
		ModelAndView model =  new ModelAndView(Constants.SCREEN_HOME);
		userLogger.info("Vehicle plate number: {}", vehicle.getPlateNumber());
		if (vehicle.getPlateNumber() == null) {
			return model;
		}
		int operationType = OperationType.valueOf(operation.getType()).getValue();
		userLogger.info("OperationType: {}", operationType);
		if (operationType == -1) {
			logger.trace("returns {}", model);
			return model;
		} 
		
		validateEmptyVehicleNumber(model, vehicle, operationType);
		validateCarAlreadyEntered(model, vehicle, operationType);
		validateAvailableParkingLotInLevel(model, parkingLevel.getLevelName());
		if (model.getModelMap().containsKey(Constants.ERROR_MESSAGE)) {
			userLogger.info("process contains errors");
			return model;
		}
		model = new ModelAndView(Constants.SCREEN_HOME);
		ParkingLevel dbParkingLevel = parkingLevelService.getByName(parkingLevel.getLevelName());
		
		userLogger.info("Process to current operation: {}", operationType);
		processOperation(model, modelMap, operationType, vehicle, vehicleType, dbParkingLevel);
		
		logger.trace("returns {}", model);
		return model;
	}

	/**
	 * Check in advance if there is free parking lots for current level name. Add error if level name is null, level name is not valid 
	 * and if there is no available parking lots 
	 * @param model for adding object error
	 * @param levelName 
	 */
	private void validateAvailableParkingLotInLevel(ModelAndView model, String levelName) {
		if (levelName == null) {
			model.addObject(Constants.ERROR_MESSAGE, messageSource.getMessage("error.parkingLevel.name", null, Locale.getDefault()));
		}
		ParkingLevel parkingLevel = parkingLevelService.getByName(levelName);
		if (parkingLevel == null) {
			model.addObject(Constants.ERROR_MESSAGE, messageSource.getMessage("error.parkingLevel.notFound", null, Locale.getDefault()));
		}
		
		if (!parkingLotService.isAvailableParkingLot(parkingLevel)) {
			model.addObject(Constants.ERROR_MESSAGE, messageSource.getMessage("error.parkingLevel.notFreeLots", null, Locale.getDefault()));
		}
	}

	/**
	 * process to next operation according operationType. 
	 * @param model current view to add information messages
	 * @param modelMap required to add attribute to reload data
	 * @param operationType Possible types are ENTER, EXIT, STATUS, UNKNOWN
	 * @param vehicle - get plateNumber from Vehicle Object
	 * @param vehicleType - Car, Motorbike , etc
	 * @param parkingLevel - take parking level name to enter to appropriate level
	 */
	private void processOperation(ModelAndView model, ModelMap modelMap, int operationType, Vehicle vehicle, VehicleType vehicleType, ParkingLevel parkingLevel) {
		logger.trace("ModelMap: {}, operationType: {}, vehicle: {}, vehicleType: {}, parkingLevel: {}", modelMap, operationType, vehicle, vehicleType, parkingLevel);
		switch (OperationType.valueOf(operationType)) {
		case ENTER:
			VehicleType vType = vehicleTypeService.getByName(vehicleType.getName());
			userLogger.info("Entering car with plateNumber: {}, type: {}, to parkingLevel: {}", vehicle.getPlateNumber(), vType.getName(), parkingLevel.getLevelName());
			ParkingLot enteredLot = operationService.enterCar(vehicle.getPlateNumber(), vType, parkingLevel);
			modelMap.addAttribute("garageStatus", getParkingLots());
			model.addObject(Constants.INFO_MESSAGE, messageSource.getMessage("info.operation.enter.success",   new String [] {vehicle.getPlateNumber(), enteredLot.getName(), enteredLot.getParkingLevel().getLevelName()}, Locale.getDefault()));
			break;
		case EXIT:
			userLogger.info("Exit car with plateNumber: {}", vehicle.getPlateNumber());
			int stayedHours = operationService.exitCar(vehicle.getPlateNumber());
			model.addObject(Constants.INFO_MESSAGE, messageSource.getMessage("info.operation.exit.success",   new String [] {vehicle.getPlateNumber(), String.valueOf(stayedHours)}, Locale.getDefault()));
			modelMap.addAttribute("garageStatus", getParkingLots());
			break;
		case STATUS:
			userLogger.info("Get Status of car with plateNumber: {}", vehicle.getPlateNumber());
			List<Operation> operations;
			if ("".equals(vehicle.getPlateNumber())) {
				operations = operationService.getOperationsByParkingLevelName(parkingLevel.getLevelName());
			} else {
				operations = operationService.getOperationsByPlateNumber(vehicle.getPlateNumber());
			}
			modelMap.addAttribute("operations", operations);
			break;
		case UNKNOWN:
			break;
		}
	}
	
	/**
	 * Check for empty vehicle plate number and returns validation error if appears
	 * @param model ModelAndView to which view should be added error message
	 * @param vehicle where is get plate number
	 * @param operationType 
	 */
	private void validateEmptyVehicleNumber(ModelAndView model, Vehicle vehicle, int operationType) {
		logger.trace("model: {}, vehicle: {}", model, vehicle);
		if (operationType == OperationType.STATUS.getValue()) return;
		if (vehicle == null || vehicle.getPlateNumber() == null || vehicle.getPlateNumber().length() < 2) {
		    model.addObject(Constants.ERROR_MESSAGE, messageSource.getMessage("NotEmpty.vehicle.plateNumber", null, Locale.getDefault()));
		    logger.warn("Empty or short vehicle number: {}", vehicle.getPlateNumber());
		}
	}
	
	/**
	 * Check if car is already in garage to prevent enter twice without exit
	 * @param model ModelAndView to which view should be added error message
	 * @param vehicle vehicle where is get plate number
	 * @param operationType - getOperationType
	 */
	private void validateCarAlreadyEntered(ModelAndView model, Vehicle vehicle, int operationType) {
		logger.trace("model: {}, vehicle: {}, operationType: {}", model, vehicle, operationType);
		if (OperationType.ENTER.getValue() == operationType 
				&& operationService.isVehicleInParking(vehicle.getPlateNumber())) {
			model.addObject(Constants.ERROR_MESSAGE, messageSource.getMessage("vehicle.alreadyEntered", null, Locale.getDefault()));
			logger.warn("You are trying to enter car which is already entered in garage");
		}
	}
}
	
