package garage;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.development.configuration.AppConfig;
import com.development.model.VehicleType;
import com.development.service.VehicleTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TestVehicleType {
	
	private static final String VEHICLE_NAME = "Truck";

	@Autowired
	@Qualifier("vehicleTypeService")
	VehicleTypeService service;
	private static VehicleType vehicleType;
	
	@BeforeClass
	public static void init() {
		vehicleType = getCreatedVehicleType();
	}
	
	private static VehicleType getCreatedVehicleType() {
		if (vehicleType == null) {
			vehicleType = new VehicleType();
			vehicleType.setName(VEHICLE_NAME);
			
		}
		return vehicleType;
	}

	@Test
	public void testCreateVehicle() {
		vehicleType = getCreatedVehicleType();
		service.save(vehicleType);
		String name = service.getByName(VEHICLE_NAME).getName();
		assertEquals(name, VEHICLE_NAME);
	}
	
	@Test
	public void testEditVehicle() {
		vehicleType = getCreatedVehicleType();
		vehicleType.setName(VEHICLE_NAME + 1);
		service.update(vehicleType);
		String name = service.getByName(VEHICLE_NAME+1).getName();
		assertEquals(name, VEHICLE_NAME+1);
		
		vehicleType.setName(VEHICLE_NAME);
		service.update(vehicleType);
		name = service.getByName(VEHICLE_NAME).getName();
		assertEquals(name, VEHICLE_NAME);
	}

}
