package de.bcxp.challenge;

import de.bcxp.challenge.io.CsvTableReader;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for the weather calculation logic.
 */
class AppTest {

    @Test
    void solveWeatherTest() throws Exception {

        String csv =
                "Day,MxT,MnT\n" +
                "1,20,5\n" +
                "2,12,10\n" +
                "3,30,20\n";

        String result = App.solveWeather(
                new StringReader(csv),
                new CsvTableReader(',')
        );

        assertEquals("2", result);
    }

    @Test
    void solveCountriesTest() throws Exception {

        String csv =
                "Name;Capital;Accession;Population;Area (km²)\n" +
                "BigLand;CapitalA;2000;4.586.301,00;100000\n" +  // density: 45.86
                "DenseLand;CapitalB;2000;2.000.000;10000\n" +   // density: 200
                "WideLand;CapitalC;2000;1500000;150000\n";    // density: 10

        String result = App.solveCountries(
                new StringReader(csv),
                new CsvTableReader(';')
        );

        assertEquals("DenseLand", result);
    }
}
