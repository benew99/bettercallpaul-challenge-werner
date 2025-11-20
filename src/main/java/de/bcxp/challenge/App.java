package de.bcxp.challenge;

//import de.bcxp.challenge.io.CsvTableReader;
import de.bcxp.challenge.io.TableReaderFactory;
import de.bcxp.challenge.io.TableReader;
import de.bcxp.challenge.weather.WeatherAnalyzer;
import de.bcxp.challenge.weather.WeatherDay;
import de.bcxp.challenge.countries.Country;
import de.bcxp.challenge.countries.CountryAnalyzer;

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
    private static final String COUNTRIES_RESOURCE_PATH = "/de/bcxp/challenge/countries.csv";

    private App() {
        // prevent instantiation
    }

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        try {

            // Weather analysis
            InputStream weatherStream  = App.class.getResourceAsStream(WEATHER_RESOURCE_PATH);

            if (weatherStream  == null) {
                System.err.println("Could not find resource: " + WEATHER_RESOURCE_PATH);
                return;
            }

            try (Reader weatherReader = new InputStreamReader(weatherStream, StandardCharsets.UTF_8)){
                TableReader weatherTableReader = TableReaderFactory.forResource(WEATHER_RESOURCE_PATH);
                //String dayWithSmallestTempSpread = solveWeather();     // Your day analysis function call …
                String dayWithSmallestTempSpread = solveWeather(weatherReader , weatherTableReader);
                System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);
            }
            

            // Countries analysis
            InputStream countryStream = App.class.getResourceAsStream(COUNTRIES_RESOURCE_PATH);
            if (countryStream == null) {
                System.err.println("Could not find resource: " + COUNTRIES_RESOURCE_PATH);
                return;
            }

            try (Reader countryReader = new InputStreamReader(countryStream, StandardCharsets.UTF_8)) {
                TableReader countryTableReader = TableReaderFactory.forResource(COUNTRIES_RESOURCE_PATH);
                String countryWithHighestPopulationDensity = solveCountries(countryReader, countryTableReader);
                System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
            }

        } catch (Exception e) {
            System.err.println("Error while processing data: " + e.getMessage());
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


        /**
     * Core countries analysis logic.
     * This method is completely independent from file locations or resource handling.
     *
     * @param reader source of tabular data
     * @param tableReader strategy used to parse the data
     * @return name of the country with the highest population density
     */
    public static String solveCountries(Reader reader, TableReader tableReader) throws Exception {

        List<String[]> rows = tableReader.readAll(reader);

        List<Country> countries = rows.stream()
                .map(App::mapToCountry)
                .collect(Collectors.toList());

        CountryAnalyzer analyzer = new CountryAnalyzer();
        Optional<Country> result = analyzer.findCountryWithHighestPopulationDensity(countries);

        return result.map(Country::getName)
                     .orElse("no data");
    }


    /**
     * Maps a CSV row to the Country domain object.
     * Expected column order: Name;Capital;Accession;Population;Area (km²);...
     */
    private static Country mapToCountry(String[] columns) {
        String name = columns[0].trim();
        String populationRaw = columns[3].trim();
        String areaRaw = columns[4].trim();

        long population = parsePopulation(populationRaw);
        double area = parseArea(areaRaw);

        return new Country(name, population, area);
    }

    private static long parsePopulation(String value) {
        String normalized = value.trim();

        // Example: "4.036.355,00" -> "4036355"
        // Cut off decimal part if present (everything after comma)
        if (normalized.contains(",")) {
            normalized = normalized.substring(0, normalized.indexOf(","));
        }

        // Remove thousand separators
        normalized = normalized.replace(".", "");

    return Long.parseLong(normalized);
}


    private static double parseArea(String value) {
        // Example values are simple integers like "83855"
        // but we still strip thousand separators just in case
        String normalized = value.replace(".", "").replace(",", ".").trim();
        return Double.parseDouble(normalized);
    }


}
