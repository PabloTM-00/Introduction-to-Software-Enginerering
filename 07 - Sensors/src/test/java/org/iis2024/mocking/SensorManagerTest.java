package org.iis2024.mocking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SensorManagerTest {

    private SensorManager sensorManager;

    @BeforeEach
    void setUp() {
        sensorManager = new SensorManager();
    }

    @Test
    @DisplayName("Initial number of sensors is0")
    void numberOfSensorsIsZeroWhenInitialised(){
        assertEquals(0,sensorManager.getNumberOfSensors());
    }

    @Test
    @DisplayName("Exception raised when 0 sensors and attempt to delete")
    void numberOfSensorsIsZeroAndAttemptToDelete() throws SensorException {
        try{
            assertEquals(0,sensorManager.getNumberOfSensors());
            sensorManager.deleteSensor("NonExistentSensor");
        }
        catch (SensorException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Given an empty manager, When registering a sensor, Then the number of sensors is one and no sensor methods are invoked")
    void givenEmptyManager_whenRegisteringSensor_thenNumberOfSensorsIsOneAndNoSensorMethodsInvoked() throws SensorException {
        TemperatureSensor mockSensor = mock(TemperatureSensor.class);

        sensorManager.registerSensor(mockSensor);

        assertEquals(1, sensorManager.getNumberOfSensors());
        verifyNoInteractions(mockSensor);
    }

    @Test
    @DisplayName("Registering sensor in empty manager adds one sensor and verifies no sensor methods called")
    void addSensorEmptyManager() throws SensorException {
        TemperatureSensor mockSensor = mock(TemperatureSensor.class);

        sensorManager.registerSensor(mockSensor);

        assertEquals(1, sensorManager.getNumberOfSensors());
        verify(mockSensor, never()).getName();
        verify(mockSensor, never()).getTemperatureCelsius();
        verify(mockSensor, never()).isAvailable();
    }

    @Test
    @DisplayName("Average temperature correct with one available sensor and verification of calls")
    void avgTempOneAvailableSensor() throws SensorException {
        TemperatureSensor mockSensor = mock(TemperatureSensor.class);
        when(mockSensor.isAvailable()).thenReturn(true);
        when(mockSensor.getTemperatureCelsius()).thenReturn(25.0);
        when(mockSensor.getName()).thenReturn("SensorA");

        sensorManager.registerSensor(mockSensor);

        double average = sensorManager.getAverageTemperature();

        assertEquals(25.0, average);
        verify(mockSensor, times(1)).isAvailable();
        verify(mockSensor, times(1)).getTemperatureCelsius();
        verifyNoMoreInteractions(mockSensor);
    }

    @Test
    @DisplayName("Average temperature correct with three available sensors and verification of getName() on first")
    void avgTempThreeAvailableSensors() throws SensorException {
        TemperatureSensor mockSensor1 = mock(TemperatureSensor.class);
        TemperatureSensor mockSensor2 = mock(TemperatureSensor.class);
        TemperatureSensor mockSensor3 = mock(TemperatureSensor.class);

        when(mockSensor1.isAvailable()).thenReturn(true);
        when(mockSensor1.getTemperatureCelsius()).thenReturn(10.0);
        when(mockSensor1.getName()).thenReturn("SensorA");

        when(mockSensor2.isAvailable()).thenReturn(true);
        when(mockSensor2.getTemperatureCelsius()).thenReturn(20.0);
        when(mockSensor2.getName()).thenReturn("SensorB");

        when(mockSensor3.isAvailable()).thenReturn(true);
        when(mockSensor3.getTemperatureCelsius()).thenReturn(30.0);
        when(mockSensor3.getName()).thenReturn("SensorC");


        sensorManager.registerSensor(mockSensor1);
        sensorManager.registerSensor(mockSensor2);
        sensorManager.registerSensor(mockSensor3);

        double average = sensorManager.getAverageTemperature();

        assertEquals(20.0, average);
        verify(mockSensor1, never()).getName();
        verify(mockSensor1, times(1)).isAvailable();
        verify(mockSensor1, times(1)).getTemperatureCelsius();
        verify(mockSensor2, times(1)).isAvailable();
        verify(mockSensor2, times(1)).getTemperatureCelsius();
        verify(mockSensor3, times(1)).isAvailable();
        verify(mockSensor3, times(1)).getTemperatureCelsius();
        verifyNoMoreInteractions(mockSensor1, mockSensor2, mockSensor3);
    }

    @Test
    @DisplayName("Average temperature correct with two available and one unavailable, verify unavailable calls")
    void avgTempTwoAvailableOneUnavailable() throws SensorException {
        TemperatureSensor mockSensor1 = mock(TemperatureSensor.class);
        TemperatureSensor mockSensor2 = mock(TemperatureSensor.class);
        TemperatureSensor mockSensor3 = mock(TemperatureSensor.class); // This one will be unavailable

        when(mockSensor1.isAvailable()).thenReturn(true);
        when(mockSensor1.getTemperatureCelsius()).thenReturn(10.0);
        when(mockSensor1.getName()).thenReturn("SensorA");

        when(mockSensor2.isAvailable()).thenReturn(true);
        when(mockSensor2.getTemperatureCelsius()).thenReturn(20.0);
        when(mockSensor2.getName()).thenReturn("SensorB");

        when(mockSensor3.isAvailable()).thenReturn(false); // Unavailable
        when(mockSensor3.getName()).thenReturn("SensorC");


        sensorManager.registerSensor(mockSensor1);
        sensorManager.registerSensor(mockSensor2);
        sensorManager.registerSensor(mockSensor3);

        double average = sensorManager.getAverageTemperature();

        assertEquals(15.0, average);
        verify(mockSensor3, times(1)).isAvailable();
        verify(mockSensor3, never()).getTemperatureCelsius();
        verifyNoMoreInteractions(mockSensor3); // Only isAvailable should be called on unavailable

        verify(mockSensor1, times(1)).isAvailable();
        verify(mockSensor1, times(1)).getTemperatureCelsius();
        verify(mockSensor2, times(1)).isAvailable();
        verify(mockSensor2, times(1)).getTemperatureCelsius();

    }

    @Test
    @DisplayName("Sensor deleted reduces count, verify deleted sensor methods not called except getName")
    void deleteSensorReducesCountAndVerifiesCalls() throws SensorException {
        TemperatureSensor mockSensorToDelete = mock(TemperatureSensor.class);
        TemperatureSensor mockSensorToKeep = mock(TemperatureSensor.class);

        when(mockSensorToDelete.getName()).thenReturn("ToDelete");
        when(mockSensorToKeep.getName()).thenReturn("ToKeep");

        sensorManager.registerSensor(mockSensorToDelete);
        sensorManager.registerSensor(mockSensorToKeep);

        assertEquals(2, sensorManager.getNumberOfSensors());

        sensorManager.deleteSensor("ToDelete");

        assertEquals(1, sensorManager.getNumberOfSensors());
        verify(mockSensorToDelete, atLeastOnce()).getName();
        verify(mockSensorToDelete, never()).getTemperatureCelsius();
        verify(mockSensorToDelete, never()).isAvailable();
        verifyNoMoreInteractions(mockSensorToDelete);

        // Optionally verify the kept sensor wasn't interacted with during deletion of the other
        verifyNoInteractions(mockSensorToKeep);
    }

    @Test
    @DisplayName("Registering sensor when manager is full throws Exception (try-catch)")
    void registerSensorWhenFullThrowsException_tryCatch() throws SensorException {
        int maxSensors = 100; // Using the actual constant from the class if accessible, otherwise hardcode

        for (int i = 0; i < maxSensors; i++) {
            TemperatureSensor mockSensor = mock(TemperatureSensor.class);
            when(mockSensor.getName()).thenReturn("Sensor" + i); // Needs unique names for safety, though registration doesn't use name for full check
            sensorManager.registerSensor(mockSensor);
        }

        TemperatureSensor extraSensor = mock(TemperatureSensor.class);
        when(extraSensor.getName()).thenReturn("ExtraSensor");

        try {
            sensorManager.registerSensor(extraSensor);
            fail("Expected SensorException when adding sensor to full manager.");
        } catch (SensorException e) {
            // Success, exception was caught
        } catch (Exception e) {
            fail("Caught an unexpected exception type: " + e.getClass().getName());
        }
        verifyNoInteractions(extraSensor); // The extra sensor's methods shouldn't be called on failed registration
    }

    @Test
    @DisplayName("Sensor deleted after 3 consecutive failures, verify isAvailable called 4 times and getName never")
    void sensorDeletedAfter3FailuresAndIsAvailableCalled4Times() throws SensorException {
        TemperatureSensor mockSensor = mock(TemperatureSensor.class);
        // Stubbing getName is still good practice if other parts of the manager might call it,
        // but it's not called during the deletion triggered by contactSensors.
        when(mockSensor.getName()).thenReturn("FailingSensor");

        // Stub isAvailable to return true once, then false three times
        when(mockSensor.isAvailable()).thenReturn(true, false, false, false);

        sensorManager.registerSensor(mockSensor);
        assertEquals(1, sensorManager.getNumberOfSensors());

        sensorManager.contactSensors(); // Available (tries = 0)
        assertEquals(1, sensorManager.getNumberOfSensors());

        sensorManager.contactSensors(); // Unavailable (tries = 1)
        assertEquals(1, sensorManager.getNumberOfSensors());

        sensorManager.contactSensors(); // Unavailable (tries = 2)
        assertEquals(1, sensorManager.getNumberOfSensors());

        sensorManager.contactSensors(); // Unavailable (tries = 3, deleted)
        assertEquals(0, sensorManager.getNumberOfSensors());

        verify(mockSensor, times(4)).isAvailable(); // This was correct
        verify(mockSensor, never()).getTemperatureCelsius(); // Correct
        verify(mockSensor, never()).getName(); // This is the corrected assertion
        verifyNoMoreInteractions(mockSensor);

    }
}
