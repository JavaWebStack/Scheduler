package org.javawebstack.scheduler.job;

import java.util.UUID;

public interface JobQueue {

    void dispatch(JobContext job);
    default void dispatch(Job job) {
        dispatch(job, 0L);
    }
    default void dispatch(Job job, long at) {
        dispatch(job, at, 1);
    }
    default void dispatch(Job job, long at, int maxAttempts) {
        JobContext context = new JobContext();
        context.setMaxAttempts(1);
        context.setId(UUID.randomUUID());
        context.setAvailableAt(at);
        context.setMaxAttempts(maxAttempts);
        context.setJob(job);
        dispatch(context);
    }
    JobContext next(long time);

}
