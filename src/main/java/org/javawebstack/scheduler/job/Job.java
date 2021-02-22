package org.javawebstack.scheduler.job;

public interface Job {

    JobResult perform(JobContext context);

    default void onSuccess(JobContext context) {
    }

    default void onFailure(JobContext context) {
    }

}
