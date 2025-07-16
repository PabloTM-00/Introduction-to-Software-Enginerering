package org.iis2024.mocking;

/** Manages a collection of temperature sensors. */
public class SensorManager {
  private final int MAXIMUM_NUMBER_OF_TRIES = 3;
  private final int MAXIMUM_NUMBER_OF_SENSORS = 100;
  private final int MINIMUM_TEMPERATURE = -90;
  private final int MAXIMUM_TEMPERATURE = 60;
  private TemperatureSensor[] sensorList;
  private int[] disconnectTries;
  private int firstFreeIndex;

  /** Constructs a SensorManager with default maximum sensor and tries limits. */
  public SensorManager() {
    sensorList = new TemperatureSensor[MAXIMUM_NUMBER_OF_SENSORS];
    disconnectTries = new int[MAXIMUM_NUMBER_OF_SENSORS];
    firstFreeIndex = 0;
  }

  /**
   * Registers a sensor with the manager.
   *
   * @param sensor The sensor to be registered.
   * @throws SensorException If the manager is already full.
   */
  public void registerSensor(TemperatureSensor sensor) throws SensorException {
    if (firstFreeIndex >= MAXIMUM_NUMBER_OF_SENSORS) {
      throw new SensorException("Cannot register a new sensor. The manager is full.");
    }
    sensorList[firstFreeIndex] = sensor;
    disconnectTries[firstFreeIndex] = 0;
    firstFreeIndex++;
  }

  /**
   * Retrieves the number of sensors registered with the manager.
   *
   * @return The number of registered sensors.
   */
  public int getNumberOfSensors() {
    return firstFreeIndex;
  }

  /**
   * Deletes the sensor with the specified name.
   *
   * @param name The name of the sensor to delete.
   * @throws SensorException If the specified sensor does not exist.
   */
  public void deleteSensor(String name) throws SensorException {
    int sensorPosition = findSensorPosition(name);
    boolean exists = sensorPosition != -1;
    if (!exists) {
      throw new SensorException("Sensor " + name + " does not exist.");
    }
    deleteSensor(sensorPosition);
  }

  /**
   * Contacts each registered sensor. If a sensor fails to respond for the third time consecutively,
   * it is removed from the manager.
   */
  public void contactSensors() {
    for (int i = firstFreeIndex - 1; i >= 0; i--) {
      TemperatureSensor sensor = sensorList[i];
      if (!sensor.isAvailable()) {
        disconnectTries[i]++;
        if (disconnectTries[i] >= MAXIMUM_NUMBER_OF_TRIES) {
          deleteSensor(i);
        }
      } else {
        disconnectTries[i] = 0;
      }
    }
  }

  /**
   * Calculates the average temperature from all registered sensors that are available.
   *
   * @return The average temperature.
   * @throws SensorException If no sensors are available or the temperature readings are out of
   *     bounds.
   */
  public double getAverageTemperature() throws SensorException {
    if (getNumberOfSensors() <= 0) {
      throw new SensorException("Cannot calculate average temperature. No sensors available.");
    }

    double temperatureSum = 0;
    int availableSensors = 0;
    for (int i = 0; i < firstFreeIndex; i++) {
      TemperatureSensor sensor = sensorList[i];
      if (sensor.isAvailable()) {
        availableSensors++;
        double temperature = sensor.getTemperatureCelsius();
        if (temperature < MINIMUM_TEMPERATURE || temperature > MAXIMUM_TEMPERATURE) {
          throw new SensorException("Error reading sensor " + sensor.getName());
        }
        temperatureSum += temperature;
      }
    }

    if (availableSensors == 0) {
      throw new SensorException("Cannot calculate average temperature. No sensors available.");
    }

    temperatureSum /= availableSensors;
    return temperatureSum;
  }

  public int getMaximumNumberOfSensors() {
    return MAXIMUM_NUMBER_OF_SENSORS ;
  }

  private int findSensorPosition(String name) {
    int i = 0;
    boolean found = false;
    while (!found && i < firstFreeIndex) {
      found = sensorList[i].getName().equals(name);
      i++;
    }
    return found ? i - 1 : -1;
  }

  private void deleteSensor(int position) {
    sensorList[position] = sensorList[firstFreeIndex - 1];
    disconnectTries[position] = disconnectTries[firstFreeIndex - 1];
    sensorList[firstFreeIndex - 1] = null;
    firstFreeIndex--;
  }
}
