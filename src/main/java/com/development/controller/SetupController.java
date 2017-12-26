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
		 logger.info("vehicleType with name : {} saved succefully", vehicleType.getName());
		 modelMap.addAttribute(Constants.MODEL_LIST_VEHICLE_TYPE, getVehicleTypes());
		 return setup();
	}

	/**
	 * POST method of removeVehicleType operation. It makes validation if name is not null and if there is a related operations for this vehicle type.
	 * @param vehicleType - to be removed
	 * @param result BindingResult object to get messages
	 * @param modelMap - to add error messages
	 * @return ModelAndView of refreshed screen
	 */
	@RequestMapping(value = Constants.OPERATION_REMOVE_VEHICLE_TYPE, method = RequestMethod.POST)
	public ModelAndView removeVehicleType(@Valid VehicleType vehicleType, BindingResult result, ModelMap modelMap) {
		logger.trace("vehicleType: {}, result: {}, modelMap: {}", vehicleType, result, modelMap);
		 ModelAndView model =  new ModelAndView(Constants.SCREEN_SETUP);
		 model.addObject(Constants.MODEL_PARKINGL_LEVEL, new ParkingLevel());
		 String name = vehicleType.getName();
		 if (name == null) {
			 model.addObject(Constants.ERROR_MESSAGE_VEHICLE_TYPE_REMOVE, messageSource.getMessage("NotEmpty.vehicleType.name", null, Locale.getDefault()));
			 logger.warn("You haven't choose a valid vehicleType name : {}", name);
			 return model;
		 }
		 List<Operation> operations = operationService.getOperationsByVehicleTypeName(name);
		 if (operations != null && !operations.isEmpty()) {
			 model.addObject(Constants.ERROR_MESSAGE_VEHICLE_TYPE_REMOVE, messageSource.getMessage("error.vehicleType.delete.haverecords", null, Locale.getDefault()));
			 logger.warn("Cannot remove current vehicleType name: {}. There is a operations via this vehicleType", name);
			 return model;
		 }
		 VehicleType vehicleTypeForDelete = vehicleTypeService.getByName(name);
		 vehicleTypeService.deleteByVehicleTypeName(vehicleTypeForDelete.getName());
		 logger.info("Removed successfully vehicleType with name: {}", name);
		 modelMap.addAttribute(Constants.MODEL_LIST_VEHICLE_TYPE, getVehicleTypes());
		 return setup();
	}
	
	/**
	 * POST method of addingParkingLevel operation. IF 
	 * @param parkingLevel of new created ParkingLevel object
	 * @param result BindingResult to resolve error messages
	 * @param modelMap to add error messages
	 * @return ModelView of refreshed screen
	 */
	@RequestMapping(value = Constants.OPERATION_ADD_PARKING_LEVEL, method = RequestMethod.POST)
	public ModelAndView addParkingLevel(@Valid ParkingLevel parkingLevel, BindingResult result, ModelMap modelMap) {
		logger.trace("parkingLevel: {}, result: {}, modelMap: {}", parkingLevel, result, modelMap);
		 ModelAndView model =  new ModelAndView(Constants.SCREEN_SETUP);
		 //adding empty vehicleType object to jsp
		 model.addObject(Constants.MODEL_VEHICLE_TYPE, new VehicleType());
		 String name = parkingLevel.getLevelName();
		 if (name == null) {
			 model.addObject(Constants.ERROR_MESSAGE_PARKING_LEVEL_ADD, messageSource.getMessage("NotEmpty.parkingLevel.name", null, Locale.getDefault()));
			 logger.warn("Empty of non valid parking level name: {}", name);
			 return model;
		 }
		 
		 if (parkingLevel.getCapacity() == null) {
			 model.addObject(Constants.ERROR_MESSAGE_PARKING_LEVEL_ADD, messageSource.getMessage("NotEmpty.parkingLevel.capacity", null, Locale.getDefault()));
			 logger.warn("Not valid capacity number: {}", parkingLevel.getCapacity());
			 return model;
		 }
		 
		 Integer startNumber = parkingLevel.getStartNumber();
		 if (startNumber == null) {
			 model.addObject(Constants.ERROR_MESSAGE_PARKING_LEVEL_ADD, messageSource.getMessage("NotEmpty.parkingLevel.startNumber", null, Locale.getDefault()));
			 logger.warn("Not valid startNumber number: {}", startNumber);
			 return model;
		 }
		
		 parkingLevelService.save(parkingLevel);
		 logger.info("ParkingLevel with name: {} was added succesfully", parkingLevel.getLevelName());
		 //add appropriate parking lots according configuration
		 dataManager.initData(startNumber, parkingLevel);
		 modelMap.addAttribute(Constants.MODEL_LIST_PARKING_LEVEL, getParkingLevels());
		 return setup();
	}
	
	/**
	 * POST method of removeParkingLevel operation. It checks for valid parkingLevel name and if there is related operations for this parking level and
	 * retun and error
	 * @param parkingLevel which should be removed
	 * @param modelMap where it should be added error messages
	 * @return ModelAndView and refresh screen
	 */
	@RequestMapping(value = Constants.OPERATION_REMOVE_PARKING_LEVEL, method = RequestMethod.POST)
	public ModelAndView removeParkingLevel(@Valid ParkingLevel parkingLevel, ModelMap modelMap) {
		logger.trace("parkingLevel: {}, modelMap: {}", parkingLevel, modelMap);
		 ModelAndView model =  new ModelAndView(Constants.SCREEN_SETUP);
		 model.addObject(Constants.MODEL_VEHICLE_TYPE, new VehicleType());
		 String name = parkingLevel.getLevelName();
		 if (name == null) {
			 model.addObject(Constants.ERROR_MESSAGE_PARKING_LEVEL_REMOVE, messageSource.getMessage("NotEmpty.parkingLevel.name", null, Locale.getDefault()));
			 logger.warn("Not a valid or null parkingLevel name: {}", name);
			 return model;
		 }
		
		 List <Operation> operations = operationService.getOperationsByParkingLevelName(name);
		 if (operations != null && !operations.isEmpty()) {
			 model.addObject(Constants.ERROR_MESSAGE_PARKING_LEVEL_REMOVE, messageSource.getMessage("error.parkingLevel.delete.haverecords", null, Locale.getDefault()));
			 logger.warn("There are an operations related to this level : {}", name);
			 return model;
		 }
		 
		 ParkingLevel parkingLevelForDelete = parkingLevelService.getByName(name);
		 parkingLotService.deleteByParkingLevel(parkingLevelForDelete);
		 logger.info("Removed parkinglots for parkingLevel name: {}", name);
		 
		 parkingLevelService.deleteByName(parkingLevelForDelete.getLevelName());
		 logger.info("Removed parkintLots with name: {}", name);
		 
		 modelMap.addAttribute(Constants.MODEL_LIST_PARKING_LEVEL, getParkingLevels());
		 return setup();
	}

	/**
	 * Fill vehicleTypeList attribute
	 * @return List<VehicleType>
	 */
	 @ModelAttribute(Constants.MODEL_LIST_VEHICLE_TYPE)
	 public List<VehicleType> getVehicleTypes() {
	   List<VehicleType> vehicleTypeList = vehicleTypeService.getAllVehicleTypes();
	   return vehicleTypeList;
	 }
	 
	 /**
	  * Fill parkingLevelList attribute
	  * @return List<ParkingLevel>
	  */
	 @ModelAttribute(Constants.MODEL_LIST_PARKING_LEVEL)
	 public List<ParkingLevel> getParkingLevels() {
	   List<ParkingLevel> parkingLevelList = parkingLevelService.getAllParkingLevels();
	   return parkingLevelList;
	 }
}
