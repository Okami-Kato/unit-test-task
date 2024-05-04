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
    public void calculate_noFollowers_notOctober() {
        when(timeService.getCurrentMonth()).thenReturn(Month.SEPTEMBER);
        final Developer developer = new Developer("bob", List.of());

        int actual = calculator.calculate(developer);

        assertEquals(0, actual);
    }

    @Test
    public void calculate_noFollowers_inOctober() {
        when(timeService.getCurrentMonth()).thenReturn(Month.OCTOBER);
        final Developer developer = new Developer("bob", List.of());

        int actual = calculator.calculate(developer);

        assertEquals(HACKTOBER_BONUS, actual);
    }

    @Test
    public void calculate_withFollowers_notOctober() {
        when(timeService.getCurrentMonth()).thenReturn(Month.SEPTEMBER);
        final List<Developer> followers = List.of(
            new Developer("alice", List.of()),
            new Developer("peter", List.of())
        );
        final Developer developer = new Developer("bob", followers);

        int actual = calculator.calculate(developer);

        assertEquals(followers.size() * FOLLOWERS_MULTIPLIER, actual);
    }

    @Test
    public void calculate_withFollowers_inOctober() {
        when(timeService.getCurrentMonth()).thenReturn(Month.OCTOBER);
        final List<Developer> followers = List.of(
            new Developer("alice", List.of()),
            new Developer("peter", List.of())
        );
        final Developer developer = new Developer("bob", followers);

        int actual = calculator.calculate(developer);

        assertEquals(followers.size() * FOLLOWERS_MULTIPLIER + HACKTOBER_BONUS, actual);
    }

    @Test
    public void calculate_nullFollowers_notOctober() {
        when(timeService.getCurrentMonth()).thenReturn(Month.SEPTEMBER);
        final Developer developer = new Developer("bob", null);

        int actual = calculator.calculate(developer);

        assertEquals(0, actual);
    }

    @Test
    public void calculate_nullFollowers_inOctober() {
        when(timeService.getCurrentMonth()).thenReturn(Month.OCTOBER);
        final Developer developer = new Developer("bob", null);

        int actual = calculator.calculate(developer);

        assertEquals(HACKTOBER_BONUS, actual);
    }
}
