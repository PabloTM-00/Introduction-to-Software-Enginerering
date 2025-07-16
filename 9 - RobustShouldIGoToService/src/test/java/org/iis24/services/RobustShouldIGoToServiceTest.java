package org.iis24.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GPSCoordinates {
    private double latitude;
    private double longitude;

    public GPSCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GPSCoordinates that = (GPSCoordinates) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(latitude, longitude);
    }
}

@ExtendWith(MockitoExtension.class)
class RobustShouldIGoToServiceTest {

    @Mock
    private IWeatherService ws;

    @Mock
    private IWeatherService ws2;

    @Mock
    private IMapService ms;

    @Mock
    private IMapService ms2;

    private RobustShouldIGoToService robustService;

    @BeforeEach
    void setUp() {
        LinkedList<IMapService> mapServices = new LinkedList<>();
        mapServices.add(ms);
        mapServices.add(ms2);

        LinkedList<IWeatherService> weatherServices = new LinkedList<>();
        weatherServices.add(ws);
        weatherServices.add(ws2);

        robustService = new RobustShouldIGoToService(mapServices, weatherServices);

        robustService.setRainProbabilityThreshold(50.0);
        robustService.setRainAmountThreshold(5.0);
    }

    @Test
    void shouldRaiseInvalidServiceInstanceExceptionWhenListsContainNullReferences(){
        LinkedList<IMapService> mapServiceListWithNull = new LinkedList<>();
        mapServiceListWithNull.add(ms);
        mapServiceListWithNull.add(null);
        mapServiceListWithNull.add(ms2);

        LinkedList<IWeatherService> validWeatherServiceList = new LinkedList<>();
        validWeatherServiceList.add(ws);
        validWeatherServiceList.add(ws2);

        InvalidServiceInstanceException thrownMapException = assertThrows(InvalidServiceInstanceException.class,
                () -> new RobustShouldIGoToService(mapServiceListWithNull, validWeatherServiceList));

        assertEquals("Invalid map service instance found in list", thrownMapException.getMessage());


        LinkedList<IMapService> validMapServiceList = new LinkedList<>();
        validMapServiceList.add(ms);
        validMapServiceList.add(ms2);

        LinkedList<IWeatherService> weatherServiceListWithNull = new LinkedList<>();
        weatherServiceListWithNull.add(ws);
        weatherServiceListWithNull.add(null);
        weatherServiceListWithNull.add(ws2);

        InvalidServiceInstanceException thrownWeatherException = assertThrows(InvalidServiceInstanceException.class,
                () -> new RobustShouldIGoToService(validMapServiceList, weatherServiceListWithNull));

        assertEquals("Invalid weather service instance found in list", thrownWeatherException.getMessage());
    }

    @Test
    void shouldRaiseInvalidMapServiceExecutionExceptionIfMapServiceFails(){
        String testPlace = "Failing Map Location";
        LocalDate testDate = LocalDate.now().plusDays(1);

        try {
            when(ms.getCoordinates(testPlace)).thenThrow(new RuntimeException("Simulated map service failure"));
        } catch (Exception e) {
            throw new RuntimeException("Error configuring mock map service", e);
        }

        InvalidMapServiceExecutionException thrownException = assertThrows(InvalidMapServiceExecutionException.class,
                () -> robustService.shouldIGoTo(testPlace, testDate));

        assertEquals("Map service received invalid input.", thrownException.getMessage());
    }

    @Test
    void shouldInvokeWeatherServiceWithValidCoordinatesIfOneMapServiceWorksWell() throws Exception {
        String testPlace = "Working Map Location";
        LocalDate testDate = LocalDate.now().plusDays(1);
        GPSCoordinates expectedCoords = new GPSCoordinates(36.7212, -4.4216);

        when(ms.getCoordinates(testPlace)).thenReturn(expectedCoords);

        robustService.shouldIGoTo(testPlace, testDate);

        verify(ms, times(1)).getCoordinates(testPlace);
        verify(ws, times(1)).rainProbability(eq(expectedCoords), eq(testDate));
        verify(ws, times(1)).totalAmountOfRain(eq(expectedCoords), eq(testDate));
    }

    @Test
    void shouldReturnTrueWhenAverageOfProbabilitiesAndTotalAmountOfRainAreZero() throws Exception {
        String testPlace = "Zero Rain Place";
        LocalDate testDate = LocalDate.now().plusDays(1);
        GPSCoordinates mockCoords = new GPSCoordinates(10.0, 10.0);

        when(ms.getCoordinates(testPlace)).thenReturn(mockCoords);

        when(ws.rainProbability(mockCoords, testDate)).thenReturn(0.0);
        when(ws.totalAmountOfRain(mockCoords, testDate)).thenReturn(0.0);

        when(ws2.rainProbability(mockCoords, testDate)).thenReturn(0.0);
        when(ws2.totalAmountOfRain(mockCoords, testDate)).thenReturn(0.0);

        robustService.setRainProbabilityThreshold(50.0);
        robustService.setRainAmountThreshold(5.0);

        boolean result = robustService.shouldIGoTo(testPlace, testDate);

        assertEquals(true, result);
    }

    @Test
    void shouldReturnFalseWhenAverageOfProbabilitiesAndTotalAmountOfRainAreEqualToThresholds() throws Exception {
        String testPlace = "Threshold Location";
        LocalDate testDate = LocalDate.now().plusDays(1);
        GPSCoordinates mockCoords = new GPSCoordinates(20.0, 20.0);

        when(ms.getCoordinates(testPlace)).thenReturn(mockCoords);

        robustService.setRainProbabilityThreshold(30.0);
        robustService.setRainAmountThreshold(10.0);

        when(ws.rainProbability(mockCoords, testDate)).thenReturn(30.0);
        when(ws.totalAmountOfRain(mockCoords, testDate)).thenReturn(10.0);

        when(ws2.rainProbability(mockCoords, testDate)).thenReturn(30.0);
        when(ws2.totalAmountOfRain(mockCoords, testDate)).thenReturn(10.0);

        boolean result = robustService.shouldIGoTo(testPlace, testDate);

        assertEquals(false, result);
    }

    @Test
    void shouldInvokeAllAvailableWeatherServices() throws Exception {
        String testPlace = "Verify All Weather Services";
        LocalDate testDate = LocalDate.now().plusDays(1);
        GPSCoordinates mockCoords = new GPSCoordinates(30.0, 30.0);

        when(ms.getCoordinates(testPlace)).thenReturn(mockCoords);

        robustService.shouldIGoTo(testPlace, testDate);

        verify(ws, times(1)).rainProbability(any(GPSCoordinates.class), any(LocalDate.class));
        verify(ws, times(1)).totalAmountOfRain(any(GPSCoordinates.class), any(LocalDate.class));
        verify(ws2, times(1)).rainProbability(any(GPSCoordinates.class), any(LocalDate.class));
        verify(ws2, times(1)).totalAmountOfRain(any(GPSCoordinates.class), any(LocalDate.class));
    }
}
