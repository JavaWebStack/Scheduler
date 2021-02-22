package org.javawebstack.scheduler.job;

import java.io.PrintWriter;
import java.io.StringWriter;

public class JobWorker implements Runnable {

    private final JobQueue queue;
    private boolean stopRequested = false;

    public JobWorker(JobQueue queue) {
        this.queue = queue;
    }

    public void run() {
        while(!stopRequested) {
            JobContext context = queue.pop(System.currentTimeMillis());
            if(context == null) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            try {
                Job job = context.getJob();
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
                        queue.schedule(context);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        stopRequested = true;
    }

}
