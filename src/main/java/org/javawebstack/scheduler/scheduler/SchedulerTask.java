package org.javawebstack.scheduler.scheduler;

import org.javawebstack.scheduler.job.JobContext;
import org.javawebstack.scheduler.job.JobResult;

public interface SchedulerTask {

    JobResult perform(JobContext jobContext) throws Exception;

    default void onSuccess(JobContext jobContext) throws Exception {
    }

    default void onFailure(JobContext jobContext) throws Exception {
    }

}
