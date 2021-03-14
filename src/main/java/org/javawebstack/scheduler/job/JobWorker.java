package org.javawebstack.scheduler.job;

import com.google.gson.JsonParseException;
import org.javawebstack.injector.Injector;
import org.javawebstack.scheduler.Work;

import java.io.PrintWriter;
import java.io.StringWriter;

public class JobWorker extends Work {

    private final JobQueue queue;
    private Injector injector;

    public JobWorker(JobQueue queue) {
        this.queue = queue;
    }

    public JobWorker setInjector(Injector injector) {
        this.injector = injector;
        return this;
    }

    public boolean runOnce() {
        JobContext context = queue.next(System.currentTimeMillis());
        if(context == null)
            return false;
        try {
            Job job = context.getJob();
            if(injector != null)
                injector.inject(job);
            JobResult result;
            try {
                result = job.perform(context);
            } catch (Throwable t) {
                StringWriter writer = new StringWriter();
                t.printStackTrace(new PrintWriter(writer));
                context.setError(writer.toString());
                result = JobResult.retry();
            }
            context.setAttempts(context.getAttempts() + 1);
            if(result.isSuccess()) {
                try {
                    job.onSuccess(context);
                }catch (Throwable t) {
                    t.printStackTrace();
                }
            } else {
                if(result.getRetryAt() == null || context.getAttempts() >= context.getMaxAttempts()) {
                    try {
                        job.onFailure(context);
                    }catch (Throwable t) {
                        t.printStackTrace();
                    }
                } else {
                    context.setAvailableAt(result.getRetryAt().getTime());
                    context.setJob(job);
                    queue.dispatch(context);
                }
            }
        } catch (ClassNotFoundException | JsonParseException e) {
            e.printStackTrace();
        }
        return true;
    }

}
