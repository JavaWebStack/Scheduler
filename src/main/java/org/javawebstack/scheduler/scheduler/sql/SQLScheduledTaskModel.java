package org.javawebstack.scheduler.scheduler.sql;

import org.javawebstack.abstractdata.AbstractObject;
import org.javawebstack.orm.Model;
import org.javawebstack.orm.annotation.Column;
import org.javawebstack.orm.annotation.Table;

@Table("scheduled_tasks")
public class SQLScheduledTaskModel extends Model {

    @Column(id = true, size = 255)
    private String name;
    @Column
    private String schedule;
    @Column
    private String interval;
    @Column
    private long nextExecutionAt;
    @Column
    private int maxAttempts;
    @Column
    private String type;
    @Column
    private AbstractObject data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
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
}
