package de.bcxp.challenge;

//import de.bcxp.challenge.io.CsvTableReader;
import de.bcxp.challenge.io.TableReaderFactory;
import de.bcxp.challenge.io.TableReader;
import de.bcxp.challenge.weather.WeatherAnalyzer;
import de.bcxp.challenge.weather.WeatherDay;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

    private static final String WEATHER_RESOURCE_PATH = "/de/bcxp/challenge/weather.csv";

    private App() {
        // prevent instantiation
    }

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        // Your preparation code …

        String dayWithSmallestTempSpread = solveWeather();     // Your day analysis function call …
        System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);

        // Countries are not implemented yet - this will be done after refactoring
        String countryWithHighestPopulationDensity = "TODO"; // Your population density analysis function call …
        System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
    }

    /**
     * Reads the weather data from the CSV file and determines 
     * the day with the smallest temperature range.
     */
    public static String solveWeather() {

        try {
            InputStream inputStream = App.class.getResourceAsStream(WEATHER_RESOURCE_PATH);
            if (inputStream == null) {
                System.err.println("Could not find resource: " + WEATHER_RESOURCE_PATH);
                return "error";
            }

            //TableReader tableReader = new CsvTableReader(',');
            TableReader tableReader = TableReaderFactory.forResource(WEATHER_RESOURCE_PATH);


            List<String[]> rows = tableReader.readAll(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8)
            );

            List<WeatherDay> days = rows.stream()
                    .map(App::mapToWeatherDay)
                    .collect(Collectors.toList());

            WeatherAnalyzer analyzer = new WeatherAnalyzer();
            Optional<WeatherDay> result = analyzer.findDayWithSmallestTemperatureSpread(days);

            return result.map(day -> Integer.toString(day.getDay()))
                    .orElse("no data");

        } catch (Exception e) {
            System.err.println("Error while processing weather data: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Mapping of a CSV row (string array) to the domain object WeatherDay.
     * Expects column order: Day, MxT, MnT. ...
     */
    private static WeatherDay mapToWeatherDay(String[] columns) {
        int day = Integer.parseInt(columns[0].trim());
        int maxTemp = Integer.parseInt(columns[1].trim());
        int minTemp = Integer.parseInt(columns[2].trim());
        return new WeatherDay(day, maxTemp, minTemp);
    }
}
