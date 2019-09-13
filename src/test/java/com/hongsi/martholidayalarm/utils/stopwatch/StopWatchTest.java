package com.hongsi.martholidayalarm.utils.stopwatch;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class StopWatchTest {

    private StopWatch stopWatch;

    @Before
    public void setUp() {
        stopWatch = new StopWatch();
    }

    @Test
    public void start_fail_OnlyNotNullTaskName() {
        assertThatIllegalArgumentException().isThrownBy(() -> stopWatch.start(null));
    }

    @Test
    public void start_fail_TaskNameMustBeUnique() {
        String taskName = "name";
        stopWatch.start(taskName);

        assertThatIllegalArgumentException().isThrownBy(() -> stopWatch.start(taskName));
    }

    @Test
    public void stop_fail_OnlyRunningTaskNameCanBeStopped() {
        assertThatIllegalArgumentException().isThrownBy(() -> stopWatch.stop("name"));
    }
}