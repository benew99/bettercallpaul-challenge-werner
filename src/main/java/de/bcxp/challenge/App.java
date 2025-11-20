package de.bcxp.challenge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

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
     * Naive implementation: reads weather.csv directly and computes
     * the day with the smallest temperature spread.
     */
    private static String solveWeather() {

        int bestDay = -1;
        int smallestSpread = Integer.MAX_VALUE;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        App.class.getResourceAsStream("/de/bcxp/challenge/weather.csv"),
                        StandardCharsets.UTF_8))) {

            // Skip header line
            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] columns = line.split(",");

                int day = Integer.parseInt(columns[0].trim());
                int maxTemp = Integer.parseInt(columns[1].trim());
                int minTemp = Integer.parseInt(columns[2].trim());

                int spread = maxTemp - minTemp;

                if (spread < smallestSpread) {
                    smallestSpread = spread;
                    bestDay = day;
                }
            }

        } catch (Exception e) {
            System.err.println("Error while processing weather data: " + e.getMessage());
            return "error";
        }

        return bestDay == -1 ? "no data" : Integer.toString(bestDay);
    }
}
