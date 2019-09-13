package com.hongsi.martholidayalarm.utils.stopwatch;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class StopWatch {

    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    private final String name;
    private final Map<String, Long> currentTasks = new ConcurrentHashMap<>();
    private final List<TaskInfo> completedTasks = new ArrayList<>();

    private long totalElapsedTime = 0L;

    public StopWatch() {
        this("");
    }

    public StopWatch(String name) {
        this.name = name;
    }

    public void start(String taskName) {
        if (taskName == null) {
            throw new IllegalArgumentException("Invalid task name");
        }
        if (currentTasks.containsKey(taskName)) {
            throw new IllegalArgumentException("Already working : " + taskName);
        }
        currentTasks.put(taskName, getCurrentTimeMillis());
    }

    public void stop(String taskName) {
        if (!currentTasks.containsKey(taskName)) {
            throw new IllegalArgumentException("Not found task : " + taskName);
        }
        long elapsedTime = getCurrentTimeMillis() - currentTasks.remove(taskName);
        totalElapsedTime += elapsedTime;
        completedTasks.add(new TaskInfo(taskName, elapsedTime));
    }

    public long getTotalElapsedTime(TimeUnit timeUnit) {
        return timeUnit.convert(totalElapsedTime, DEFAULT_TIME_UNIT);
    }

    /**
     * This code is from org.springframework.util.StopWatch
     */
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder(shortSummary());
        sb.append('\n');
        if (completedTasks.isEmpty()) {
            sb.append("No task info kept");
        }
        else {
            sb.append("-----------------------------------------\n");
            sb.append("ms     %     Task name\n");
            sb.append("-----------------------------------------\n");
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumIntegerDigits(5);
            nf.setGroupingUsed(false);
            NumberFormat pf = NumberFormat.getPercentInstance();
            pf.setMinimumIntegerDigits(3);
            pf.setGroupingUsed(false);
            for (TaskInfo task : completedTasks) {
                sb.append(nf.format(task.getElapsedTime())).append("  ");
                sb.append(pf.format(toSeconds(task.getElapsedTime()) / toSeconds(totalElapsedTime))).append("  ");
                sb.append(task.getName()).append("\n");
            }
        }
        return sb.toString();
    }

    public String shortSummary() {
        return "StopWatch '" + name + "': running time (millis) = " + totalElapsedTime;
    }

    private double toSeconds(long elapsedTime) {
        return elapsedTime / 1000.0;
    }

    private Long getCurrentTimeMillis() {
        return Long.valueOf(System.currentTimeMillis());
    }
}
