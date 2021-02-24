package org.javawebstack.scheduler.job;

public interface Job {

    JobResult perform(JobContext context) throws Exception;

    default void onSuccess(JobContext context) throws Exception {
    }

    default void onFailure(JobContext context) throws Exception {
    }

}
