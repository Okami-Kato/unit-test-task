package pl.allegro.tech.recruit.calc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeveloperReputationCalculatorTest {

    private static final int FOLLOWERS_MULTIPLIER = 2;
    private static final int HACKTOBER_BONUS = 10;

    private TimeService timeService;
    private DeveloperReputationCalculator calculator;

    @BeforeEach
    public void setUp() {
        timeService = mock(TimeService.class);
        calculator = new DeveloperReputationCalculator(FOLLOWERS_MULTIPLIER, HACKTOBER_BONUS, timeService);
    }

    @Test
    public void calculate_notOctober() {
        List<Testcase> testcases = List.of(
            new Testcase(new Developer("bob", null), 0),
            new Testcase(new Developer("bob", List.of()), 0),
            new Testcase(new Developer("bob", List.of(
                new Developer("alice", List.of()),
                new Developer("peter", List.of()))
            ), 2 * FOLLOWERS_MULTIPLIER)
        );

        when(timeService.getCurrentMonth()).thenReturn(Month.SEPTEMBER);
        for (Testcase tc : testcases) {
            int actual = calculator.calculate(tc.getInput());
            assertEquals(tc.getWant(), actual);
        }
    }


    @Test
    public void calculate_inOctober() {
        List<Testcase> testcases = List.of(
            new Testcase(new Developer("bob", null), HACKTOBER_BONUS),
            new Testcase(new Developer("bob", List.of()), HACKTOBER_BONUS),
            new Testcase(new Developer("bob", List.of(
                new Developer("alice", List.of()),
                new Developer("peter", List.of()))
            ), 2 * FOLLOWERS_MULTIPLIER + HACKTOBER_BONUS)
        );

        when(timeService.getCurrentMonth()).thenReturn(Month.OCTOBER);
        for (Testcase tc : testcases) {
            int actual = calculator.calculate(tc.getInput());
            assertEquals(tc.getWant(), actual);
        }
    }

    private static class Testcase {
        private final Developer input;
        private final int want;

        public Testcase(Developer input, int want) {
            this.input = input;
            this.want = want;
        }

        public Developer getInput() {
            return input;
        }

        public int getWant() {
            return want;
        }
    }
}
