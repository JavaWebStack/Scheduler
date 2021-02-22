package org.javawebstack.scheduler;

import java.time.Instant;
import java.util.Date;

public class JobResult {

    private final boolean success;
    private Date retryAt;

    public boolean isSuccess() {
        return success;
    }

    public Date getRetryAt() {
        return retryAt;
    }

    private JobResult(boolean success) {
        this.success = success;
    }

    public static JobResult success() {
        return new JobResult(true);
    }

    public static JobResult failed() {
        return new JobResult(false);
    }

    public static JobResult retry() {
        return retry(Date.from(Instant.now()));
    }

    public static JobResult retry(int seconds) {
        return retry(Date.from(Instant.now().plusSeconds(seconds)));
    }

    public static JobResult retry(Date date) {
        JobResult result = new JobResult(false);
        result.retryAt = date;
        return result;
    }

}
