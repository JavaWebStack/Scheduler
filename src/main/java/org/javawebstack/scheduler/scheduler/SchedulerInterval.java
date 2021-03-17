package org.javawebstack.scheduler.scheduler;

public class SchedulerInterval {

    private long every;

    public SchedulerInterval(long every) {
        this.every = every;
    }

    public SchedulerInterval(String source) {
        this(Long.parseLong(source));
    }

    public long next(long last) {
        return last + every; // TODO
    }

    public String toString() {
        return String.valueOf(every); // TODO
    }

}
