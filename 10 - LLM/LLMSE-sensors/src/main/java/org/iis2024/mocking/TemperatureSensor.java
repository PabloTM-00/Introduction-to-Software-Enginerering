package org.iis2024.mocking;

/** Interface representing a temperature sensor. */
public interface TemperatureSensor {

  /**
   * Get the name of the temperature sensor.
   *
   * @return The name of the sensor.
   */
  String getName();

  /**
   * Get the current temperature measured by the sensor in Celsius.
   *
   * @return The current temperature in Celsius.
   */
  double getTemperatureCelsius();

  /**
   * Check if the sensor is available for temperature measurement.
   *
   * @return True if the sensor is available, false otherwise.
   */
  boolean isAvailable();
}
