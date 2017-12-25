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
import com.development.model.Operation;
import com.development.model.ParkingLevel;
import com.development.model.VehicleType;
import com.development.service.OperationService;
import com.development.service.ParkingLevelService;
import com.development.service.ParkingLotService;
import com.development.service.VehicleTypeService;

@Controller
public class SetupController {
	
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
	
	@PostConstruct
	public void initIt() throws Exception {
		dataManager = new DataInitManager(parkingLotService);		
	}
	
	@RequestMapping(value = {"setup" }, method = RequestMethod.GET)
	public ModelAndView setup() {
		ModelAndView mav = new ModelAndView("setup");
		mav.addObject("vehicleType", new VehicleType());
		mav.addObject("parkingLevel", new ParkingLevel());
		return mav;
	}
	
	
	@RequestMapping(value = "addVehicleType", method = RequestMethod.POST)
	public ModelAndView addVehicleType(@Valid VehicleType vehicleType, BindingResult result, ModelMap modelMap) {
		 ModelAndView model =  new ModelAndView("setup");
		 model.addObject("parkingLevel", new ParkingLevel());
		 String name = vehicleType.getName();
		 if (name == null || "".equals(name)) {
			 model.addObject(Constants.ERROR_MESSAGE_VEHICLE_TYPE_ADD, messageSource.getMessage("NotEmpty.vehicleType.name", null, Locale.getDefault()));
			 return model;
		 }
		 vehicleTypeService.save(vehicleType);
		 modelMap.addAttribute("vehicleTypes", getVehicleTypes());
		 return setup();
	}

	@RequestMapping(value = "removeVehicleType", method = RequestMethod.POST)
	public ModelAndView removeVehicleType(@Valid VehicleType vehicleType, BindingResult result, ModelMap modelMap) {
		 ModelAndView model =  new ModelAndView("setup");
		 model.addObject("parkingLevel", new ParkingLevel());
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
		 modelMap.addAttribute("vehicleTypes", getVehicleTypes());
		 return setup();
	}
	
	@RequestMapping(value = "addParkingLevel", method = RequestMethod.POST)
	public ModelAndView addParkingLevel(@Valid ParkingLevel parkingLevel, BindingResult result, ModelMap modelMap) {
		 ModelAndView model =  new ModelAndView("setup");
		 model.addObject("vehicleType", new VehicleType());
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
		 modelMap.addAttribute("parkingLevels", getParkingLevels());
		 return setup();
	}
	
	@RequestMapping(value = "removeParkingLevel", method = RequestMethod.POST)
	public ModelAndView removeParkingLevel(@Valid ParkingLevel parkingLevel, ModelMap modelMap) {
		 ModelAndView model =  new ModelAndView("setup");
		 model.addObject("vehicleType", new VehicleType());
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
		 
		 modelMap.addAttribute("parkingLevels", getParkingLevels());
		 return setup();
	}

	 @ModelAttribute("vehicleTypes")
	 public List<VehicleType> getVehicleTypes() {
	   List<VehicleType> vehicleTypeList = vehicleTypeService.getAllVehicleTypes();
	   return vehicleTypeList;
	 }
	 
	 @ModelAttribute("parkingLevels")
	 public List<ParkingLevel> getParkingLevels() {
	   List<ParkingLevel> parkingLevelList = parkingLevelService.getAllParkingLevels();
	   return parkingLevelList;
	 }
}
