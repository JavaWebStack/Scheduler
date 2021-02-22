package org.javawebstack.scheduler.job.sql;

import org.javawebstack.orm.ORM;
import org.javawebstack.orm.ORMConfig;
import org.javawebstack.orm.Repo;
import org.javawebstack.orm.exception.ORMConfigurationException;
import org.javawebstack.orm.mapper.AbstractDataTypeMapper;
import org.javawebstack.orm.wrapper.SQL;
import org.javawebstack.scheduler.job.JobContext;
import org.javawebstack.scheduler.job.JobQueue;

public class SQLJobQueue implements JobQueue {

    private Repo<SQLJobModel> repo;
    private final String name;

    public SQLJobQueue(SQL sql, String name) {
        this.repo = ORM.repo(SQLJobModel.class);
        if(this.repo == null) {
            try {
                this.repo = ORM.register(SQLJobModel.class, sql, new ORMConfig().addTypeMapper(new AbstractDataTypeMapper()));
            } catch (ORMConfigurationException ignored) { }
        }
        this.name = name;
    }

    public void schedule(JobContext job) {
        SQLJobModel model = new SQLJobModel();
        model.setQueue(name);
        model.setId(job.getId());
        model.setAvailableAt(job.getAvailableAt());
        model.setAttempts(job.getAttempts());
        model.setMaxAttempts(job.getMaxAttempts());
        model.setType(job.getType());
        model.setData(job.getData());
        model.setError(job.getError());
        model.save();
    }

    public JobContext pop(long time) {
        SQLJobModel model = repo.where("queue", name).where("availableAt", "<=", time).order("availableAt").first();
        if(model == null)
            return null;
        model.delete();
        JobContext context = new JobContext();
        context.setId(model.getId());
        context.setAvailableAt(model.getAvailableAt());
        context.setAttempts(model.getAttempts());
        context.setMaxAttempts(model.getMaxAttempts());
        context.setError(model.getError());
        context.setType(model.getType());
        context.setData(model.getData());
        return context;
    }

}
