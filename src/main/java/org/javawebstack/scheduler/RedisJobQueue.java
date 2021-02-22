package org.javawebstack.scheduler;

import org.javawebstack.abstractdata.AbstractElement;
import org.javawebstack.abstractdata.AbstractMapper;
import redis.clients.jedis.Jedis;

public class RedisJobQueue implements JobQueue {

    private static final AbstractMapper mapper = new AbstractMapper();

    private final Jedis jedis;
    private final String key;

    public RedisJobQueue(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

    public void schedule(JobContext job) {
        jedis.zadd(key, job.getAvailableAt(), mapper.toAbstract(job).toJsonString());
    }

    public JobContext pop(long time) {
        Object nextJob = jedis.eval("local a=redis.call('zpopmin',KEYS[1],1)if a==false or#a==0 then return end;if tonumber(a[2])>tonumber(ARGV[1])then redis.call('zadd',KEYS[1],a[2],a[1])return end;return a[1]", 1, key, String.valueOf(time));
        if(!(nextJob instanceof String))
            return null;
        return mapper.fromAbstract(AbstractElement.fromJson((String) nextJob), JobContext.class);
    }

}
