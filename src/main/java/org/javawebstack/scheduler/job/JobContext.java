package org.javawebstack.scheduler.job;

import com.google.gson.JsonParseException;
import org.javawebstack.abstractdata.AbstractElement;
import org.javawebstack.abstractdata.AbstractMapper;
import org.javawebstack.abstractdata.AbstractObject;

import java.util.UUID;

public class JobContext {

    private static final AbstractMapper mapper = new AbstractMapper();

    private UUID id;
    private long availableAt;
    private int attempts;
    private int maxAttempts;
    private String error;
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

    public String getType() {
        return type;
    }

    public AbstractObject getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public void setData(AbstractObject data) {
        this.data = data;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setJob(Job job) {
        this.type = job.getClass().getName();
        this.data = mapper.toAbstract(job).object();
    }

    public Job getJob() throws ClassNotFoundException, JsonParseException {
        Class<?> clazz = Class.forName(type);
        if(clazz.isAssignableFrom(Job.class))
            throw new JsonParseException("The job's type is not a Job class");
        return mapper.fromAbstract(data, (Class<? extends Job>) clazz);
    }

    public void setError(String error) {
        this.error = error;
    }

    public static JobContext fromString(String json) {
        return mapper.fromAbstract(AbstractElement.fromJson(json), JobContext.class);
    }

    public String toString() {
        return mapper.toAbstract(this).toJsonString();
    }

}
