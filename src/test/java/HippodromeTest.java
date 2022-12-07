import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void nullHorsesListException() {
        List<Horse> blankList = null;
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", illegalArgumentException.getMessage());
    }

    @Test
    void blankListException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", illegalArgumentException.getMessage());
    }

    @Test
    void getHorsesReturnSameList() {
        List<Horse> expectedList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            expectedList.add(new Horse("name" + i, i + 1, i + 5));
        }
        List<Horse> actualList = new Hippodrome(expectedList).getHorses();
        assertEquals(expectedList, actualList);
    }

    @Test
    void moveCalledOnAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();
        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }

    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>();
        Horse horseMinDistance = new Horse("min", 1, 1);
        Horse horseMediumDistance = new Horse("medium", 1, 2);
        Horse horseMaxDistance = new Horse("max", 1, 3);

        horses.add(horseMinDistance);
        horses.add(horseMaxDistance);
        horses.add(horseMediumDistance);

        assertEquals(horseMaxDistance, new Hippodrome(horses).getWinner());
    }
}
