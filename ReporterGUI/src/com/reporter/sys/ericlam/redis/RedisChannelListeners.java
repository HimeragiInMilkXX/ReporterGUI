package com.reporter.sys.ericlam.redis;

import com.reporter.sys.ericlam.manager.ProvedCountManager;
import com.reporter.sys.ericlam.manager.ReportManager;
import org.bukkit.Bukkit;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;

public class RedisChannelListeners extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals("Report-Spigot")) return;
        String[] msg = message.split("_");
        String method = msg[0].toLowerCase();
        String uuidStr = msg[1].toLowerCase();
        UUID uuid = UUID.fromString(uuidStr);
        switch (method) {
            case "del":
                ReportManager reportManager = ReportManager.getInstance();
                reportManager.getDuplicateds().remove(uuid);
                break;
            case "clear-proved":
                ProvedCountManager provedCountManager = ProvedCountManager.getInstance();
                provedCountManager.clearCache(uuid);
                break;
            default:
                Bukkit.getServer().getLogger().info("Unknown redis msg, ignored.");
        }
    }
}
