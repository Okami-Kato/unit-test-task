package pl.allegro.tech.recruit.calc;

import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class DeveloperReputationCalculator {

    private final int followersMultiplier;
    private final int hacktoberBonus;

    private final TimeService timeService;

    public DeveloperReputationCalculator(int followersMultiplier, int hacktoberBonus, TimeService timeService) {
        this.followersMultiplier = followersMultiplier;
        this.hacktoberBonus = hacktoberBonus;
        this.timeService = timeService;
    }

    public int calculate(Developer dev) {
        final int followersScore = Optional.ofNullable(dev.getFollowers())
                .map(List::size)
                .map(cnt -> cnt * followersMultiplier)
                .orElse(0);

        final boolean duringHacktober = Month.OCTOBER.equals(timeService.getCurrentMonth());

        if (duringHacktober) {
            return followersScore + hacktoberBonus;
        }

        return followersScore;
    }

}
