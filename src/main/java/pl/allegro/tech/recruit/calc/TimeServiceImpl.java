package pl.allegro.tech.recruit.calc;

import java.time.Month;
import java.time.ZonedDateTime;

public class TimeServiceImpl implements TimeService {

    @Override
    public Month getCurrentMonth() {
        return ZonedDateTime.now().getMonth();
    }
}
