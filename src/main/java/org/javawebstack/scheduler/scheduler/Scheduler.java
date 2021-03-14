package org.javawebstack.scheduler.scheduler;

import org.javawebstack.injector.Inject;
import org.javawebstack.injector.Injector;
import org.javawebstack.scheduler.Work;
import org.javawebstack.scheduler.job.Job;
import org.javawebstack.scheduler.job.JobContext;
import org.javawebstack.scheduler.job.JobQueue;
import org.javawebstack.scheduler.job.JobResult;

public class Scheduler extends Work {

    private final Schedule schedule;
    private final JobQueue queue;

    public Scheduler(Schedule schedule, JobQueue queue) {
        this.schedule = schedule;
        this.queue = queue;
    }

    public boolean runOnce() {
        SchedulerContext context = schedule.next(System.currentTimeMillis());
        if(context == null)
            return false;
        queue.dispatch(new SchedulerJob(context), 0, context.getMaxAttempts());
        context.setNextExecutionAt(new SchedulerInterval(context.getInterval()).next(context.getNextExecutionAt()));
        schedule.schedule(context);
        return true;
    }

    public static class SchedulerJob implements Job {

        private SchedulerContext context;

        public SchedulerJob() {
        }

        @Inject
        Injector injector;

        public SchedulerJob(SchedulerContext context) {
            this.context = context;
        }

        private SchedulerTask inject(SchedulerTask task) {
            if(injector != null)
                injector.inject(task);
            return task;
        }

        public JobResult perform(JobContext jobContext) throws Exception {
            return inject(context.getTask()).perform(jobContext);
        }

        public void onSuccess(JobContext jobContext) throws Exception {
            inject(context.getTask()).onSuccess(jobContext);
        }

        public void onFailure(JobContext jobContext) throws Exception {
            inject(context.getTask()).onFailure(jobContext);
        }

    }

}
