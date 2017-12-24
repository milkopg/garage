package com.development.controller;

import java.util.List;
import java.util.Locale;

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

import com.development.general.Constants;
import com.development.model.Operation;
import com.development.model.ParkingLevel;
import com.development.model.Vehicle;
import com.development.model.VehicleType;
import com.development.service.VehicleTypeService;

@Controller
public class SetupController {
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	VehicleTypeService vehicleTypeService;
	
	@RequestMapping(value = {"setup" }, method = RequestMethod.GET)
	public ModelAndView setup() {
	
		
//		List<ParkingLevel> parkingLevels = parkingLevelService.getAllParkingLevels(); 
		ModelAndView mav = new ModelAndView("setup");
//		mav.addObject("parkingLevel", parkingLevels.get(0));
//		mav.addObject("operation", new Operation());
//		mav.addObject("vehicle", new Vehicle());
		mav.addObject("vehicleType", new VehicleType());
		return mav;
	}
	
	
	@RequestMapping(value = "addVehicleType", method = RequestMethod.POST)
	public ModelAndView addVehicleType(@Valid VehicleType vehicleType, BindingResult result, ModelMap modelMap) {
		 ModelAndView model =  new ModelAndView("setup");
		 String name = vehicleType.getName();
		 if (name == null) {
			 model.addObject(Constants.ERROR_MESSAGE_OBJECT_NAME, messageSource.getMessage("NotEmpty.vehicleType.name", null, Locale.getDefault()));
			 return model;
		 }
		 vehicleTypeService.save(vehicleType);
		 modelMap.addAttribute("vehicleTypes", getVehicleTypes());
		 return model;
		
	}
	
	 @ModelAttribute("vehicleTypes")
	 public List<VehicleType> getVehicleTypes() {
	   List<VehicleType> vehicleTypeList = vehicleTypeService.getAllVehicleTypes();
	   return vehicleTypeList;
	 }
}
