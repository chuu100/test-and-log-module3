import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;

class HorseTest {

    @Test
    void nullNameThrowsException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Horse(null, anyDouble(), anyDouble()));
        assertEquals("Name cannot be null.", illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\t"})
    void blankNameException(String name) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, anyDouble(), anyDouble()));
        assertEquals("Name cannot be blank.", illegalArgumentException.getMessage());
    }

    @Test
    void negativeSpeedValueException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> new Horse("name", -1.0, anyDouble()));
        assertEquals("Speed cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    void negativeDistanceValueException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> new Horse("name", anyDouble(), -1.0));
        assertEquals("Distance cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    void getName() {
        String expected = "name";
        String actual = new Horse(expected, anyDouble(), anyDouble()).getName();
        assertEquals(expected, actual);
    }

    @Test
    void getSpeed() {
        Double expected = 1.0;
        Double actual = new Horse("name", expected, anyDouble()).getSpeed();
        assertEquals(expected, actual);
    }

    @Test
    void getDistance() {
        Double expected = 1.0;
        Double actual = new Horse("name", anyDouble(), expected).getDistance();
        assertEquals(expected, actual);
    }

    @Test
    void getDistanceWithTwoParametersConstructor() {
        Double actual = new Horse("name", anyDouble()).getDistance();
        assertEquals(0, actual);
    }

    @Test
    void moveUsedGetRandomWithParams() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("name", 5, 5).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 0.6, 0.7})
    void moveAssignDistanceValue(double expectedRandom) {

        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            double speed = 5;
            double distance = 100;
            Horse horse = new Horse("Horse", speed, distance);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(expectedRandom);
            horse.move();

            assertEquals(distance + speed * expectedRandom, horse.getDistance());
        }
    }
}
