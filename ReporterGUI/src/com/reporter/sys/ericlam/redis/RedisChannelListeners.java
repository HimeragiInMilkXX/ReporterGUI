package com.reporter.sys.ericlam.redis;

import redis.clients.jedis.JedisPubSub;

public class RedisChannelListeners extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals("Report-Bungee")) return;
        String[] msg = message.split("_");
    }
}
