package com.development.controller;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
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

import com.development.data.DataInitManager;
import com.development.general.Constants;
import com.development.model.Operation;
import com.development.model.ParkingLevel;
import com.development.model.VehicleType;
import com.development.service.OperationService;
import com.development.service.ParkingLevelService;
import com.development.service.ParkingLotService;
import com.development.service.VehicleTypeService;

/**
 * Controller class for initial garage parking settings like create VehicleType, create parking levels, create parking lots, etc
 * @author Milko
 *
 */
@Controller
public class SetupController {
	
	private static final Logger logger = LoggerFactory.getLogger(SetupController.class);
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	VehicleTypeService vehicleTypeService;
	
	@Autowired
	OperationService operationService;
	
	@Autowired
	ParkingLevelService parkingLevelService;
	
	@Autowired
	ParkingLotService parkingLotService;
	
	private DataInitManager dataManager;
	
	/**
	 * get instance of DataInit manager when dependency injection is loaded
	 * @throws Exception
	 */
	@PostConstruct
	public void initIt() throws Exception {
		dataManager = new DataInitManager(parkingLotService);		
	}
	
	/**
	 * Default view of setup screen. It loads empty models of vehicleType and parkingLevel
	 * @return
	 */
	@RequestMapping(value = {Constants.SCREEN_SETUP }, method = RequestMethod.GET)
	public ModelAndView setup() {
		ModelAndView mav = new ModelAndView(Constants.SCREEN_SETUP);
		mav.addObject(Constants.MODEL_VEHICLE_TYPE, new VehicleType());
		mav.addObject(Constants.MODEL_PARKINGL_LEVEL, new ParkingLevel());
		return mav;
	}
	
	/**
	 * POST method of addVehicleType operation
	 * @param vehicleType - to take type of vehicle
	 * @param result - to take validation errors
	 * @param modelMap to add error message attribute
	 * @return ModelAndView of added vehicleType
	 */
	@RequestMapping(value = Constants.OPERATION_ADD_VEHICLE_TYPE, method = RequestMethod.POST)
	public ModelAndView addVehicleType(@Valid VehicleType vehicleType, BindingResult result, ModelMap modelMap) {
		logger.trace("vehicleType: {}, result: {}, modelMap: {}", vehicleType, result, modelMap);
		 ModelAndView model =  new ModelAndView(Constants.SCREEN_SETUP);
		 model.addObject(Constants.MODEL_PARKINGL_LEVEL, new ParkingLevel());
		 String name = vehicleType.getName();
		 if (name == null || "".equals(name)) {
			 model.addObject(Constants.ERROR_MESSAGE_VEHICLE_TYPE_ADD, messageSource.getMessage("NotEmpty.vehicleType.name", null, Locale.getDefault()));
			 return model;
		 }
		 vehicleTypeService.save(vehicleType);
		 modelMap.addAttribute(Constants.MODEL_LIST_VEHICLE_TYPE, getVehicleTypes());
		 return setup();
	}

	@RequestMapping(value = Constants.OPERATION_REMOVE_VEHICLE_TYPE, method = RequestMethod.POST)
	public ModelAndView removeVehicleType(@Valid VehicleType vehicleType, BindingResult result, ModelMap modelMap) {
		 ModelAndView model =  new ModelAndView(Constants.SCREEN_SETUP);
		 model.addObject(Constants.MODEL_PARKINGL_LEVEL, new ParkingLevel());
		 String name = vehicleType.getName();
		 if (name == null) {
			 model.addObject(Constants.ERROR_MESSAGE_VEHICLE_TYPE_REMOVE, messageSource.getMessage("NotEmpty.vehicleType.name", null, Locale.getDefault()));
			 return model;
		 }
		 List<Operation> operations = operationService.getOperationsByVehicleTypeName(name);
		 if (operations != null && !operations.isEmpty()) {
			 model.addObject(Constants.ERROR_MESSAGE_VEHICLE_TYPE_REMOVE, messageSource.getMessage("error.vehicleType.delete.haverecords", null, Locale.getDefault()));
			 return model;
		 }
		 VehicleType vehicleTypeForDelete = vehicleTypeService.getByName(name);
		 vehicleTypeService.deleteByVehicleTypeName(vehicleTypeForDelete.getName());
		 modelMap.addAttribute(Constants.MODEL_LIST_VEHICLE_TYPE, getVehicleTypes());
		 return setup();
	}
	
	@RequestMapping(value = Constants.OPERATION_ADD_PARKING_LEVEL, method = RequestMethod.POST)
	public ModelAndView addParkingLevel(@Valid ParkingLevel parkingLevel, BindingResult result, ModelMap modelMap) {
		 ModelAndView model =  new ModelAndView(Constants.SCREEN_SETUP);
		 model.addObject(Constants.MODEL_VEHICLE_TYPE, new VehicleType());
		 String name = parkingLevel.getLevelName();
		 if (name == null) {
			 model.addObject(Constants.ERROR_MESSAGE_PARKING_LEVEL_ADD, messageSource.getMessage("NotEmpty.parkingLevel.name", null, Locale.getDefault()));
			 return model;
		 }
		 
		 if (parkingLevel.getCapacity() == null) {
			 model.addObject(Constants.ERROR_MESSAGE_PARKING_LEVEL_ADD, messageSource.getMessage("NotEmpty.parkingLevel.capacity", null, Locale.getDefault()));
			 return model;
		 }
		 
		 Integer startNumber = parkingLevel.getStartNumber();
		 if (startNumber == null) {
			 model.addObject(Constants.ERROR_MESSAGE_PARKING_LEVEL_ADD, messageSource.getMessage("NotEmpty.parkingLevel.startNumber", null, Locale.getDefault()));
			 return model;
		 }
		
		 
		 parkingLevelService.save(parkingLevel);
		 dataManager.initData(startNumber, parkingLevel);
		 modelMap.addAttribute(Constants.MODEL_LIST_PARKING_LEVEL, getParkingLevels());
		 return setup();
	}
	
	@RequestMapping(value = Constants.OPERATION_REMOVE_PARKING_LEVEL, method = RequestMethod.POST)
	public ModelAndView removeParkingLevel(@Valid ParkingLevel parkingLevel, ModelMap modelMap) {
		 ModelAndView model =  new ModelAndView(Constants.SCREEN_SETUP);
		 model.addObject(Constants.MODEL_VEHICLE_TYPE, new VehicleType());
		 String name = parkingLevel.getLevelName();
		 if (name == null) {
			 model.addObject(Constants.ERROR_MESSAGE_PARKING_LEVEL_REMOVE, messageSource.getMessage("NotEmpty.vehicleType.name", null, Locale.getDefault()));
			 return model;
		 }
		
		 List <Operation> operations = operationService.getOperationsByParkingLevelName(name);
		 if (operations != null && !operations.isEmpty()) {
			 model.addObject(Constants.ERROR_MESSAGE_PARKING_LEVEL_REMOVE, messageSource.getMessage("error.parkingLevel.delete.haverecords", null, Locale.getDefault()));
			 return model;
		 }
		 
		 ParkingLevel parkingLevelForDelete = parkingLevelService.getByName(name);
		 parkingLotService.deleteByParkingLevel(parkingLevelForDelete);
		 parkingLevelService.deleteByName(parkingLevelForDelete.getLevelName());
		 parkingLotService.deleteByName(name);
		 
		 modelMap.addAttribute(Constants.MODEL_LIST_PARKING_LEVEL, getParkingLevels());
		 return setup();
	}

	 @ModelAttribute(Constants.MODEL_LIST_VEHICLE_TYPE)
	 public List<VehicleType> getVehicleTypes() {
	   List<VehicleType> vehicleTypeList = vehicleTypeService.getAllVehicleTypes();
	   return vehicleTypeList;
	 }
	 
	 @ModelAttribute(Constants.MODEL_LIST_PARKING_LEVEL)
	 public List<ParkingLevel> getParkingLevels() {
	   List<ParkingLevel> parkingLevelList = parkingLevelService.getAllParkingLevels();
	   return parkingLevelList;
	 }
}
