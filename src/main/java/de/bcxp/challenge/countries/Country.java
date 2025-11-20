package de.bcxp.challenge.countries;

/**
 * Domain object representing a country with population and area.
 */
public class Country {

    private final String name;
    private final long population;
    private final double areaInKm2;

    public Country(String name, long population, double areaInKm2) {
        this.name = name;
        this.population = population;
        this.areaInKm2 = areaInKm2;
    }

    public String getName() {
        return name;
    }

    public long getPopulation() {
        return population;
    }

    public double getAreaInKm2() {
        return areaInKm2;
    }

    public double getPopulationDensity() {
        if (areaInKm2 == 0) {
            return 0.0; 
        }
        return population / areaInKm2;
    }
}
