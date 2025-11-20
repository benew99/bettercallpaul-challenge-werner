package de.bcxp.challenge.countries;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Encapsulates the logic for evaluating the country data.
 */
public class CountryAnalyzer {

    /**
     * Finds the country with the highest population density.
     *
     * @param countries list of countries
     * @return country with the highest population density, if available
     */
    public Optional<Country> findCountryWithHighestPopulationDensity(List<Country> countries) {
        return countries.stream()
                .max(Comparator.comparingDouble(Country::getPopulationDensity));
    }
}
