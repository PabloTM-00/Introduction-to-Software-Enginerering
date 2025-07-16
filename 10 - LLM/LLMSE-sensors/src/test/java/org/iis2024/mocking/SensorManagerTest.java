package org.iis2024.mocking;

import org.iis2024.mocking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SensorManagerTest {

    SensorManager manager;
    
    @Mock
    TemperatureSensor sensor;
    
    @Mock
    TemperatureSensor sensor2;
    
    @Mock
    TemperatureSensor sensor3;

    @BeforeEach
    void setup() {
        manager = new SensorManager();
    }

    @Test
    void testInitialSensorCountIsZero() {
    	assertEquals(0,manager.getNumberOfSensors());
    }

    @Test
    void testDeleteFromEmptyManagerThrows() {
    	assertThrows(SensorException.class,()-> {
    		manager.deleteSensor("Noexiste");
    	});
    }

    @Test
    void testRegisterOneSensor() throws Exception {
        manager.registerSensor(sensor);
        
        assertEquals(1,manager.getNumberOfSensors());
        
        verify(sensor,never()).getName();
        verify(sensor,never()).getTemperatureCelsius();
        verify(sensor,never()).isAvailable();
    }

    @Test
    void testDeleteNonExistentSensorFromTwo() throws Exception {
		 when(sensor.getName()).thenReturn("Sensor1");
		 when(sensor2.getName()).thenReturn("Sensor2");
    	
    	manager.registerSensor(sensor);
        manager.registerSensor(sensor2);
        
        assertThrows(SensorException.class, ()->{
        	manager.deleteSensor("TampocoExiste");
        });
                 
       verify(sensor).getName();
       verify(sensor2).getName();
    }

    @Test
    void testAverageTemperatureSingleSensor() throws Exception {
        when(sensor.getTemperatureCelsius()).thenReturn(30.0);
        when(sensor.isAvailable()).thenReturn(true);
        
        manager.registerSensor(sensor);
        
        double result = manager.getAverageTemperature();
        assertEquals(30.0,result);
        
        verify(sensor,times(1)).isAvailable();
        verify(sensor,times(1)).getTemperatureCelsius();
    }

    @Test
    void testAverageTemperatureThreeSensors() throws Exception {
    	
    	when(sensor.isAvailable()).thenReturn(true);
    	when(sensor2.isAvailable()).thenReturn(true);
    	when(sensor3.isAvailable()).thenReturn(true);
    	
    	when(sensor.getTemperatureCelsius()).thenReturn(30.0);
    	when(sensor2.getTemperatureCelsius()).thenReturn(30.0);
    	when(sensor3.getTemperatureCelsius()).thenReturn(30.0);
    	
    	manager.registerSensor(sensor);
    	manager.registerSensor(sensor2);
    	manager.registerSensor(sensor3);
    	
    	double result = manager.getAverageTemperature();
        assertEquals(30.0,result);
        
        verify(sensor,never()).getName();
    }

    @Test
    void testAverageTemperatureTwoAvailableOneUnavailable() throws Exception {
    	
    	when(sensor.isAvailable()).thenReturn(true);
    	when(sensor2.isAvailable()).thenReturn(true);
    	//sensor3 won't get called so not necessary to define a when
    	
    	when(sensor.getTemperatureCelsius()).thenReturn(30.0);
    	when(sensor2.getTemperatureCelsius()).thenReturn(30.0);
    	
    	manager.registerSensor(sensor);
    	manager.registerSensor(sensor2);
    	manager.registerSensor(sensor3);
    	
    	double result = manager.getAverageTemperature();
        assertEquals(30.0,result);
        
        verify(sensor3,times(1)).isAvailable();
        verify(sensor3,never()).getTemperatureCelsius();
    }

    @Test
    void testSensorDeletedReducesCount() throws Exception {
    	when(sensor.getName()).thenReturn("1");
    	when(sensor2.getName()).thenReturn("2");
    	when(sensor3.getName()).thenReturn("Sensor3");
    	
    	manager.registerSensor(sensor);
    	manager.registerSensor(sensor2);
    	manager.registerSensor(sensor3);
    	
    	manager.deleteSensor("Sensor3");
    	
    	assertEquals(2,manager.getNumberOfSensors());
    	
    	verify(sensor3,times(1)).getName();
    	verify(sensor3,never()).getTemperatureCelsius();
    	verify(sensor3,never()).isAvailable();
    }

    @Test
    void testRegisterMoreThanLimitThrows() throws Exception {
    	
        for(int i = 0; i < manager.getMaximumNumberOfSensors() ; i++) {
        	TemperatureSensor sensor = mock(TemperatureSensor.class);
        	when(sensor.getName()).thenReturn("Sensor " + i);
        	manager.registerSensor(sensor);
        }
        
        TemperatureSensor extraSensor = mock(TemperatureSensor.class);
        when(extraSensor.getName()).thenReturn("SensorExtra");
        
        assertThrows(SensorException.class, () -> {
        	manager.registerSensor(extraSensor);
        });
    }

    @Test
    void testSensorFailsThreeTimesGetsDeleted() throws Exception {
        TemperatureSensor s = mock(TemperatureSensor.class);
        when(s.getName()).thenReturn("FailingSensor");

        // First available, then three failures
        when(s.isAvailable())
                .thenReturn(true)    // First call
                .thenReturn(false)   // 2nd call
                .thenReturn(false)   // 3rd call
                .thenReturn(false);  // 4th call

        manager.registerSensor(s);
        manager.contactSensors(); // 1st - resets count
        manager.contactSensors(); // 2nd - 1 fail
        manager.contactSensors(); // 3rd - 2 fails
        manager.contactSensors(); // 4th - 3rd fail, should delete

        assertEquals(0, manager.getNumberOfSensors());
        verify(s, times(4)).isAvailable();
    }
}

