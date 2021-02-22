package org.javawebstack.scheduler;

import com.sun.corba.se.impl.io.TypeMismatchException;
import org.javawebstack.abstractdata.AbstractMapper;
import org.javawebstack.abstractdata.AbstractObject;

import java.util.UUID;

public class JobContext {

    private static final AbstractMapper mapper = new AbstractMapper();

    private UUID id;
    private long availableAt;
    private int attempts;
    private int maxAttempts;
    private String type;
    private AbstractObject data;

    public UUID getId() {
        return id;
    }

    public int getAttempts() {
        return attempts;
    }

    public long getAvailableAt() {
        return availableAt;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setAvailableAt(long availableAt) {
        this.availableAt = availableAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public void setJob(Job job) {
        this.type = job.getClass().getName();
        this.data = mapper.toAbstract(job).object();
    }

    public Job getJob() throws ClassNotFoundException, TypeMismatchException {
        Class<?> clazz = Class.forName(type);
        if(clazz.isAssignableFrom(Job.class))
            throw new TypeMismatchException("The job's type is not a Job class");
        return mapper.fromAbstract(data, (Class<? extends Job>) clazz);
    }

}
