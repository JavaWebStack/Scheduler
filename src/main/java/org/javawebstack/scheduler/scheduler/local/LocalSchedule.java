package org.javawebstack.scheduler.scheduler.local;

import org.javawebstack.scheduler.scheduler.*;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class LocalSchedule implements Schedule {

    private final SortedSet<SchedulerContext> tasks = new ConcurrentSkipListSet<>((o1, o2) -> (int)((o1.getNextExecutionAt() - o2.getNextExecutionAt()) / 1000));

    public void schedule(SchedulerContext context) {
        unschedule(context.getName());
        tasks.add(context);
    }

    public void unschedule(String name) {
        new HashSet<>(tasks).stream().filter(e -> e.getName().equals(name)).forEach(tasks::remove);
    }

    public SchedulerContext next(long time) {
        if(tasks.size() == 0)
            return null;
        SchedulerContext context = tasks.first();
        if(context.getNextExecutionAt() > time)
            return null;
        tasks.remove(context);
        return context;
    }

}
