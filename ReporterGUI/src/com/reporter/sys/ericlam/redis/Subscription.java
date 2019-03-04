package com.reporter.sys.ericlam.redis;

import redis.clients.jedis.JedisPubSub;

public class Subscription {
    private static Subscription subscribtion;
    private JedisPubSub jedisPubSub;

    public static Subscription getInstance() {
        if (subscribtion == null) subscribtion = new Subscription();
        return subscribtion;
    }

    public JedisPubSub getJedisPubSub() {
        if (jedisPubSub == null) jedisPubSub = new RedisChannelListeners();
        return jedisPubSub;
    }

    public void setJedisPubSub(JedisPubSub jedisPubSub) {
        this.jedisPubSub = jedisPubSub;
    }
}
