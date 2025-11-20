package de.bcxp.challenge.weather;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Encapsulates the logic for evaluating the weather data.
 */
public class WeatherAnalyzer {

    /**
     * Finds the day with the smallest temperature range.
     *
     * @param days List of weather days
     * @return Day with the smallest temperature range, if available
     */
    public Optional<WeatherDay> findDayWithSmallestTemperatureSpread(List<WeatherDay> days) {
        return days.stream()
                .min(Comparator.comparingInt(WeatherDay::getTemperatureSpread));
    }
}
