package org.iis2024.mocking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.Mock;

class SensorManagerTest {

    SensorManager manager;

    @BeforeEach
    void setup() {
        manager = new SensorManager();
    }

    @Test
    void testInitialSensorCountIsZero() {
        assertEquals(0, manager.getNumberOfSensors());
    }

    @Test
    void testDeleteFromEmptyManagerThrows() {
        assertThrows(SensorException.class, () -> manager.deleteSensor("NonExistent"));
    }

    @Test
    void testRegisterOneSensor() throws Exception {
        TemperatureSensor sensor = mock(TemperatureSensor.class);
        when(sensor.getName()).thenReturn("S1");
        manager.registerSensor(sensor);
        assertEquals(1, manager.getNumberOfSensors());
        verify(sensor, never()).getTemperatureCelsius();
        verify(sensor, never()).isAvailable();
        verify(sensor, never()).getName();
    }

    @Test
    void testDeleteNonExistentSensorFromTwo() throws Exception {
        TemperatureSensor s1 = mock(TemperatureSensor.class);
        TemperatureSensor s2 = mock(TemperatureSensor.class);
        when(s1.getName()).thenReturn("S1");
        when(s2.getName()).thenReturn("S2");

        manager.registerSensor(s1);
        manager.registerSensor(s2);

        assertThrows(SensorException.class, () -> manager.deleteSensor("Unknown"));
        verify(s1, atLeastOnce()).getName();
        verify(s2, atLeastOnce()).getName();
    }

    @Test
    void testAverageTemperatureSingleSensor() throws Exception {
        TemperatureSensor s = mock(TemperatureSensor.class);
        when(s.getName()).thenReturn("S1");
        when(s.isAvailable()).thenReturn(true);
        when(s.getTemperatureCelsius()).thenReturn(25.0);

        manager.registerSensor(s);
        assertEquals(25.0, manager.getAverageTemperature());
        verify(s).isAvailable();
        verify(s).getTemperatureCelsius();
    }

    @Test
    void testAverageTemperatureThreeSensors() throws Exception {
        TemperatureSensor s1 = mock(TemperatureSensor.class);
        TemperatureSensor s2 = mock(TemperatureSensor.class);
        TemperatureSensor s3 = mock(TemperatureSensor.class);

        when(s1.isAvailable()).thenReturn(true);
        when(s2.isAvailable()).thenReturn(true);
        when(s3.isAvailable()).thenReturn(true);
        when(s1.getTemperatureCelsius()).thenReturn(20.0);
        when(s2.getTemperatureCelsius()).thenReturn(30.0);
        when(s3.getTemperatureCelsius()).thenReturn(40.0);

        manager.registerSensor(s1);
        manager.registerSensor(s2);
        manager.registerSensor(s3);

        assertEquals(30.0, manager.getAverageTemperature());
        verify(s1, never()).getName();
    }

    @Test
    void testAverageTemperatureTwoAvailableOneUnavailable() throws Exception {
        TemperatureSensor s1 = mock(TemperatureSensor.class);
        TemperatureSensor s2 = mock(TemperatureSensor.class);
        TemperatureSensor s3 = mock(TemperatureSensor.class);

        when(s1.isAvailable()).thenReturn(true);
        when(s2.isAvailable()).thenReturn(false);
        when(s3.isAvailable()).thenReturn(true);
        when(s1.getTemperatureCelsius()).thenReturn(20.0);
        when(s2.getTemperatureCelsius()).thenReturn(20.0); //(non-chatgpt comment) added this for my further understanding
        when(s3.getTemperatureCelsius()).thenReturn(40.0);

        manager.registerSensor(s1);
        manager.registerSensor(s2);
        manager.registerSensor(s3);

        assertEquals(30.0, manager.getAverageTemperature());
        verify(s2).isAvailable();
        verify(s2, never()).getTemperatureCelsius();
    }

    @Test
    void testSensorDeletedReducesCount() throws Exception {
        TemperatureSensor s = mock(TemperatureSensor.class);
        when(s.getName()).thenReturn("S1");
        manager.registerSensor(s);
        manager.deleteSensor("S1");

        assertEquals(0, manager.getNumberOfSensors());
        verify(s, atLeastOnce()).getName();
        verify(s, never()).getTemperatureCelsius();
        verify(s, never()).isAvailable();
    }

    @Test
    void testRegisterMoreThanLimitThrows() throws Exception {
        for (int i = 0; i < manager.getMaximumNumberOfSensors(); i++) {
            TemperatureSensor s = mock(TemperatureSensor.class);
            when(s.getName()).thenReturn("S" + i);
            manager.registerSensor(s);
        }

        TemperatureSensor extra = mock(TemperatureSensor.class);
        assertThrows(SensorException.class, () -> manager.registerSensor(extra));
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

