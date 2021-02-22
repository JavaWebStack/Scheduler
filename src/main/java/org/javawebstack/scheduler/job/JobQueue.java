package org.javawebstack.scheduler.job;

import java.util.UUID;

public interface JobQueue {

    void schedule(JobContext job);
    default void schedule(Job job) {
        schedule(job, 0L);
    }
    default void schedule(Job job, long at) {
        schedule(job, at, 1);
    }
    default void schedule(Job job, long at, int maxAttempts) {
        JobContext context = new JobContext();
        context.setMaxAttempts(1);
        context.setId(UUID.randomUUID());
        context.setAvailableAt(at);
        context.setMaxAttempts(maxAttempts);
        context.setJob(job);
        schedule(context);
    }
    JobContext pop(long time);

}
