package org.javawebstack.scheduler;

public interface Job {

    JobResult perform(JobContext context);

    default void onSuccess(JobContext context) {
    }

    default void onFailure(JobContext context) {
    }

}
