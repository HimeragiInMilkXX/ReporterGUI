package com.reporter.sys.ericlam.redis;

import com.reporter.sys.ericlam.manager.ReportManager;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;

public class RedisChannelListeners extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals("Report-Spigot")) return;
        String[] msg = message.split("_");
        String method = msg[0].toLowerCase();
        String uuidStr = msg[1].toLowerCase();
        switch (method) {
            case "del":
                UUID uuid = UUID.fromString(uuidStr);
                ReportManager reportManager = ReportManager.getInstance();
                reportManager.getDuplicateds().remove(uuid);
                break;
        }
    }
}
