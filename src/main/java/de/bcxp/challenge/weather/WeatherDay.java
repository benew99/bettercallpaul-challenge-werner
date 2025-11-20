package de.bcxp.challenge.weather;

/**
 * Domain object for a single day in the weather dataset.
 */
public class WeatherDay {

    private final int day;
    private final int maxTemp;
    private final int minTemp;

    public WeatherDay(int day, int maxTemp, int minTemp) {
        this.day = day;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public int getDay() {
        return day;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getTemperatureSpread() {
        return maxTemp - minTemp;
    }
}
