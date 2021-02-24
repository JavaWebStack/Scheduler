package org.javawebstack.scheduler.scheduler;

public interface Schedule {

    void schedule(SchedulerContext context);
    default void schedule(String name, SchedulerInterval interval, SchedulerTask task) {
        schedule(name, interval, task, 1);
    }
    default void schedule(String name, SchedulerInterval interval, SchedulerTask task, int maxAttempts) {
        SchedulerContext context = new SchedulerContext();
        context.setName(name);
        context.setInterval(interval.toString());
        context.setNextExecutionAt(interval.next(System.currentTimeMillis()));
        context.setMaxAttempts(maxAttempts);
        context.setTask(task);
        schedule(context);
    }
    void unschedule(String name);
    SchedulerContext next(long time);

}
