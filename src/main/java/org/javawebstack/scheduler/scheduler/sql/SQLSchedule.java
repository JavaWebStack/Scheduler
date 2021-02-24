package org.javawebstack.scheduler.scheduler.sql;

import org.javawebstack.orm.ORM;
import org.javawebstack.orm.ORMConfig;
import org.javawebstack.orm.Repo;
import org.javawebstack.orm.exception.ORMConfigurationException;
import org.javawebstack.orm.mapper.AbstractDataTypeMapper;
import org.javawebstack.orm.wrapper.SQL;
import org.javawebstack.scheduler.scheduler.Schedule;
import org.javawebstack.scheduler.scheduler.SchedulerContext;

public class SQLSchedule implements Schedule {

    private Repo<SQLScheduledTaskModel> repo;
    private final String name;

    public SQLSchedule(SQL sql, String name) {
        this.repo = ORM.repo(SQLScheduledTaskModel.class);
        if(this.repo == null) {
            try {
                this.repo = ORM.register(SQLScheduledTaskModel.class, sql, new ORMConfig().addTypeMapper(new AbstractDataTypeMapper()));
            } catch (ORMConfigurationException ignored) { }
        }
        this.name = name;
    }

    public void schedule(SchedulerContext context) {
        unschedule(context.getName());
        SQLScheduledTaskModel model = new SQLScheduledTaskModel();
        model.setName(context.getName());
        model.setSchedule(name);
        model.setInterval(context.getInterval());
        model.setNextExecutionAt(context.getNextExecutionAt());
        model.setMaxAttempts(context.getMaxAttempts());
        model.setType(context.getType());
        model.setData(context.getData());
        model.save();
    }

    public void unschedule(String name) {
        repo.where("schedule", this.name).where("name", name).delete();
    }

    public SchedulerContext next(long time) {
        SQLScheduledTaskModel model = repo.where("schedule", name).where("nextExecutionAt", "<=", time).order("nextExecutionAt").first();
        if(model == null)
            return null;
        model.delete();
        SchedulerContext context = new SchedulerContext();
        context.setName(model.getName());
        context.setInterval(model.getInterval());
        context.setNextExecutionAt(model.getNextExecutionAt());
        context.setMaxAttempts(model.getMaxAttempts());
        context.setType(model.getType());
        context.setData(model.getData());
        return context;
    }

}
