package org.javawebstack.scheduler.scheduler;

import com.google.gson.JsonParseException;
import org.javawebstack.abstractdata.AbstractElement;
import org.javawebstack.abstractdata.AbstractMapper;
import org.javawebstack.abstractdata.AbstractObject;
import org.javawebstack.scheduler.job.Job;

public class SchedulerContext {

    private static final AbstractMapper mapper = new AbstractMapper();

    private String name;
    private String interval;
    private long nextExecutionAt;
    private int maxAttempts;
    private String type;
    private AbstractObject data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public long getNextExecutionAt() {
        return nextExecutionAt;
    }

    public void setNextExecutionAt(long nextExecutionAt) {
        this.nextExecutionAt = nextExecutionAt;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AbstractObject getData() {
        return data;
    }

    public void setData(AbstractObject data) {
        this.data = data;
    }

    public void setTask(SchedulerTask task) {
        this.type = task.getClass().getName();
        this.data = mapper.toAbstract(task).object();
    }

    public SchedulerTask getTask() throws ClassNotFoundException, JsonParseException {
        Class<?> clazz = Class.forName(type);
        if(clazz.isAssignableFrom(Job.class))
            throw new JsonParseException("The tasks's type is not a SchedulerTask class");
        return mapper.fromAbstract(data, (Class<? extends SchedulerTask>) clazz);
    }

    public static SchedulerContext fromString(String json) {
        return mapper.fromAbstract(AbstractElement.fromJson(json), SchedulerContext.class);
    }

    public String toString() {
        return mapper.toAbstract(this).toJsonString();
    }

}
