package org.javawebstack.scheduler.job.redis;

import org.javawebstack.scheduler.job.JobContext;
import org.javawebstack.scheduler.job.JobQueue;
import redis.clients.jedis.Jedis;

public class RedisJobQueue implements JobQueue {

    private final Jedis jedis;
    private final String key;

    public RedisJobQueue(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

    public void dispatch(JobContext job) {
        jedis.zadd(key, job.getAvailableAt(), job.toString());
    }

    public JobContext next(long time) {
        Object nextJob = jedis.eval("local a=redis.call('zpopmin',KEYS[1],1)if a==false or#a==0 then return end;if tonumber(a[2])>tonumber(ARGV[1])then redis.call('zadd',KEYS[1],a[2],a[1])return end;return a[1]", 1, key, String.valueOf(time));
        if(!(nextJob instanceof String))
            return null;
        return JobContext.fromString((String) nextJob);
    }

}
