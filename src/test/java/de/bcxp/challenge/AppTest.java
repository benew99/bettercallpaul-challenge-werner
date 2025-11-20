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
    void solveWeather_returnsDayWithSmallestTemperatureSpread() throws Exception {

        String csv =
                "Day,MxT,MnT\n" +
                "1,88,59\n" +
                "2,79,63\n" +
                "3,77,55\n";

        String result = App.solveWeather(
                new StringReader(csv),
                new CsvTableReader(',')
        );

        assertEquals("2", result);
    }
}
