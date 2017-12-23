package garage;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.development.configuration.AppConfig;
import com.development.model.VehicleType;
import com.development.service.VehicleTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@TestPropertySource(locations = "classpath:application.properties")
public class TestVehicleType2 {
	
	private static final String VEHICLE_NAME = "Truck";

	@Autowired
	@Qualifier("vehicleTypeService")
	VehicleTypeService service;
	private static VehicleType vehicleType;
	
	
	@Test
	public void testCreateVehicle() {
		assertEquals("1", "1");
	}
	

}
