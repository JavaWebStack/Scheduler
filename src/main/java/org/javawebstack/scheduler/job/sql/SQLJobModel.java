package org.javawebstack.scheduler.job.sql;

import org.javawebstack.abstractdata.AbstractObject;
import org.javawebstack.orm.Model;
import org.javawebstack.orm.annotation.Column;
import org.javawebstack.orm.annotation.Table;

import java.util.UUID;

@Table("jobs")
public class SQLJobModel extends Model {

    @Column
    private UUID id;
    @Column
    private String queue;
    @Column
    private long availableAt;
    @Column
    private int attempts;
    @Column
    private int maxAttempts;
    @Column
    private String error;
    @Column
    private String type;
    @Column
    private AbstractObject data;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public long getAvailableAt() {
        return availableAt;
    }

    public void setAvailableAt(long availableAt) {
        this.availableAt = availableAt;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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
