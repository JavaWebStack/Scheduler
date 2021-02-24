package org.javawebstack.scheduler.scheduler.redis;

import org.javawebstack.scheduler.scheduler.Schedule;
import org.javawebstack.scheduler.scheduler.SchedulerContext;
import redis.clients.jedis.Jedis;

public class RedisSchedule implements Schedule {

    private final Jedis jedis;
    private final String key;

    public RedisSchedule(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

    public void schedule(SchedulerContext context) {
        unschedule(context.getName());
        jedis.zadd(key, context.getNextExecutionAt(), context.toString());
    }

    public void unschedule(String name) {
        jedis.eval("local a=redis.call('zrange',KEYS[1],'0','+inf')for b,c in ipairs(a)do local d=cjson.decode(c)if d.name==ARGV[1]then redis.call('zrem',KEYS[1],c)end end", 1, key, name);
    }

    public SchedulerContext next(long time) {
        Object nextJob = jedis.eval("local a=redis.call('zpopmin',KEYS[1],1)if a==false or#a==0 then return end;if tonumber(a[2])>tonumber(ARGV[1])then redis.call('zadd',KEYS[1],a[2],a[1])return end;return a[1]", 1, key, String.valueOf(time));
        if(!(nextJob instanceof String))
            return null;
        return SchedulerContext.fromString((String) nextJob);
    }

}
