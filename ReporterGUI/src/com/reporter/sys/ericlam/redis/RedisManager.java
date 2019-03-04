package com.reporter.sys.ericlam.redis;

import com.hypernite.config.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {
    private static RedisManager redisManager;
    private static JedisPool pool;

    private RedisManager() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(20);
        config.setMaxTotal(60);
        config.setMaxWaitMillis(7000);
        config.setBlockWhenExhausted(false);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestWhileIdle(true);
        config.setMinEvictableIdleTimeMillis(500);
        config.setSoftMinEvictableIdleTimeMillis(1000);
        config.setTimeBetweenEvictionRunsMillis(1000);
        config.setNumTestsPerEvictionRun(100);
        FileConfiguration db = ConfigManager.getInstance().getDatabase();
        String host = db.getString("Redis.ip");
        int port = db.getInt("Redis.port");
        String pw = db.getString("Redis.password");
        int timeout = db.getInt("Redis.timeout") * 1000;
        boolean usePassword = db.getBoolean("Redis.use-password");
        if (usePassword) pool = new JedisPool(config, host, port, timeout, pw);
        else pool = new JedisPool(config, host, port, timeout);
    }

    public static synchronized RedisManager getInstance() {
        if (redisManager == null) redisManager = new RedisManager();
        return redisManager;
    }

    public Jedis getRedis() {
        return pool.getResource();
    }

    public void closePool() {
        if (pool == null) return;
        pool.close();
    }
}

