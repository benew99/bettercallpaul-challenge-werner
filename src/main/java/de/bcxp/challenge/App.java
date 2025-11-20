package de.bcxp.challenge;

//import de.bcxp.challenge.io.CsvTableReader;
import de.bcxp.challenge.io.TableReaderFactory;
import de.bcxp.challenge.io.TableReader;
import de.bcxp.challenge.weather.WeatherAnalyzer;
import de.bcxp.challenge.weather.WeatherDay;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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

        try {
            // Your preparation code …

            InputStream inputStream = App.class.getResourceAsStream(WEATHER_RESOURCE_PATH);

            if (inputStream == null) {
                System.err.println("Could not find resource: " + WEATHER_RESOURCE_PATH);
                return;
            }

            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            TableReader tableReader = TableReaderFactory.forResource(WEATHER_RESOURCE_PATH);

            //String dayWithSmallestTempSpread = solveWeather();     // Your day analysis function call …
            String dayWithSmallestTempSpread = solveWeather(reader, tableReader);
            System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);

            // Countries are not implemented yet - this will be done after refactoring
            String countryWithHighestPopulationDensity = "TODO"; // Your population density analysis function call …
            System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
        } catch (Exception e) {
            System.err.println("Error while processing weather data: " + e.getMessage());
        }

        
    }

    /**
     * Core weather analysis logic.
     * This method is completely independent from file locations or resource handling.
     *
     * @param reader source of tabular data
     * @param tableReader strategy used to parse the data
     * @return day with the smallest temperature spread
     */
    public static String solveWeather(Reader reader, TableReader tableReader) throws Exception {

        List<String[]> rows = tableReader.readAll(reader);

        List<WeatherDay> days = rows.stream()
                .map(App::mapToWeatherDay)
                .collect(Collectors.toList());

        WeatherAnalyzer analyzer = new WeatherAnalyzer();
        Optional<WeatherDay> result = analyzer.findDayWithSmallestTemperatureSpread(days);

        return result.map(day -> Integer.toString(day.getDay()))
                     .orElse("no data");
    }

    /**
     * Maps a CSV row to the WeatherDay domain object.
     * Expected column order: Day, MxT, MnT, ...
     */
    private static WeatherDay mapToWeatherDay(String[] columns) {
        int day = Integer.parseInt(columns[0].trim());
        int maxTemp = Integer.parseInt(columns[1].trim());
        int minTemp = Integer.parseInt(columns[2].trim());
        return new WeatherDay(day, maxTemp, minTemp);
    }
}
