package org.javawebstack.scheduler.job.local;

import org.javawebstack.scheduler.job.JobContext;
import org.javawebstack.scheduler.job.JobQueue;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class LocalJobQueue implements JobQueue {

    private final SortedSet<JobContext> jobs = new ConcurrentSkipListSet<>((o1, o2) -> (int)((o1.getAvailableAt() - o2.getAvailableAt()) / 1000));

    public void dispatch(JobContext job) {
        jobs.add(job);
    }

    public synchronized JobContext next(long time) {
        if(jobs.size() == 0)
            return null;
        JobContext context = jobs.first();
        if(context.getAvailableAt() > time)
            return null;
        jobs.remove(context);
        return context;
    }

}
