package com.reporter.sys.ericlam.redis;

import com.reporter.sys.ericlam.enums.ReasonType;
import com.reporter.sys.ericlam.enums.ReportState;
import com.reporter.sys.ericlam.manager.ConfigManager;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.UUID;

public class RedisChannel {

    public static void broadcastReport(String reporter, String reported, ReasonType reason) {
        String server = ConfigManager.server;
        String msg = server + "_" + reporter + "_" + reported + "_" + reason.getTitle();
        sendMessager(msg);
    }

    public static void uploadHandle(String reported, UUID reportedUUID, int reportID, ReportState afterState, ReportState beforeState, Player player) {
        String msg = reported + "_" + reportedUUID.toString() + "_" + reportID + "_" + beforeState.toString() + "_" + afterState.toString() + "_" + player.getName();
        sendMessager(msg);
    }

    private static void sendMessager(String msg) {
        try (Jedis redis = RedisManager.getInstance().getRedis()) {
            redis.publish("Report-Bungee", msg);
        } catch (JedisException e) {
            e.printStackTrace();
        }

    }
}
