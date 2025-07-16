package org.iis25.services;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ShouldIGoToServiceTest {

    @Mock
    private IWeatherService mockWeatherService;

    @Mock
    private IMapService mockMapService;

    private ShouldIGoToService service;

    @BeforeEach
    void setUp(){
        service = new ShouldIGoToService(mockMapService, mockWeatherService);
    }

    @Test
    void shouldRaiseInvalidServiceExceptionWhenAnyOfTheServiceInstancesProvidedInConstructorAreNull(){

        assertThrows(InvalidServiceInstanceException .class,
                () -> new ShouldIGoToService(null,null));

        assertThrows(InvalidServiceInstanceException .class,
                () -> new ShouldIGoToService(null,mockWeatherService));

        assertThrows(InvalidServiceInstanceException .class,
                () -> new ShouldIGoToService(mockMapService,null));
    }

    @Test
    void shouldRaiseInvalidRainProbabilityExceptionWhenSettingIncorrectRainProbabilityThresholdValue(){
        assertThrows(InvalidRainProbabilityException.class,
                () -> service.setRainProbabilityThreshold(-1.0));

        assertThrows(InvalidRainProbabilityException.class,
                () -> service.setRainProbabilityThreshold(110.0));
    }

    @Test
    void shouldRaiseInvalidRainAmountExceptionWhenSettingIncorrectRainAmountThresholdValue(){
        double incorrectRainAmountThreshold = -1.0;

        InvalidRainAmountException thrownException = assertThrows(InvalidRainAmountException.class,
                () -> service.setRainAmountThreshold(incorrectRainAmountThreshold));
    }

    @Test
    void shouldRaiseInvalidMapExecutionExceptionWhenMapServiceExecutionFails(){
        LocalDate myDate = LocalDate.now();

        when(mockMapService.getCoordinates("Nerja")).
                thenThrow(new RuntimeException());

        assertThrows(InvalidMapServiceExecutionException.class,
                () -> service.shouldIGoTo("Nerja", myDate));

    }

    @Test
    void shouldRaiseRunTimeExceptionWhenDateProvidedIsPast(){
        LocalDate testDate = LocalDate.parse("2025-01-01");

        assertThrows(RuntimeException.class,
                () -> service.shouldIGoTo("Nerja", testDate));
    }

    @Test
    void shouldInvokeWeatherServiceWithCorrectGPSCoordinatesWhenLegalPlaceDescriptionIsProvided(){
        /*
        SIGTS needs GPS coordinates, when returned to SIGTS,with weather service we invoke
        probability and amount of rain
         */

        GPSCoordinates correctCoordinates = new GPSCoordinates(36.75,3.87);

        when(mockMapService.getCoordinates("Nerja")).thenReturn(correctCoordinates);
        LocalDate myDate = LocalDate.now();
        service.shouldIGoTo("Nerja",myDate);

        verify(mockWeatherService).rainProbability(correctCoordinates,myDate);

    }

    @Test
    void shouldReturnTrueWhenRainProbabilityReturnedByWeatherServiceIsZero(){

    }

    @Test
    void shouldReturnFalseWhenRainProbabilityAndAmountReturnedByWeatherServiceAreEqualToThesholds(){

    }

    @Test
    void shouldRespondWithin2500MillisecondsWhenMapServiceTakes2SecondsToRespond(){

    }

    @Test
    void shouldRespondWithin2500MillisecondsWhenMapServiceTakes2SecondsToRespondAndWeatherServiceTakes400MillisToRespond(){

    }
}