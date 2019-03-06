package com.reporter.sys.milkd.main;

import com.hypernite.mysql.SQLDataSourceManager;
import com.reporter.sys.ericlam.manager.ConfigManager;
import com.reporter.sys.ericlam.manager.PlaceholderManager;
import com.reporter.sys.ericlam.redis.RedisChannelListeners;
import com.reporter.sys.ericlam.redis.RedisManager;
import com.reporter.sys.ericlam.redis.Subscription;
import com.reporter.sys.milkd.commands.AdminReportCommand;
import com.reporter.sys.milkd.commands.ReloadCommand;
import com.reporter.sys.milkd.commands.ReportCommand;
import com.reporter.sys.milkd.listener.ReportListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportSystem extends JavaPlugin {

    public static Plugin plugin;

    public void onEnable() {
        plugin = this;

        String create = "CREATE TABLE IF NOT EXISTS `ReportSystem` ( " +
                        "`ReportID` int NOT NULL AUTO_INCREMENT, " +
                        "`ReporterName` varchar(50), " +
                        "`ReporterUUID` varchar(100) NOT NULL, " +
                        "`ReportedName` varchar(50), " +
                        "`ReportedUUID` varchar(100) NOT NULL, " +
                        "`Reason` varchar(50) NOT NULL, " +
                "`TimeStamp` varchar(200) NOT NULL," +
                "`State` TINYTEXT NOT NULL," +
                "`Server` TINYTEXT NOT NULL," +
                "`Operator` varchar(50)," +
                        "PRIMARY KEY (ReportID) " +
                ")";
        String createRank = "CREATE TABLE IF NOT EXISTS `ReportSystem_Leader` (`ReporterUUID` VARCHAR(40) NOT NULL PRIMARY KEY , `ReporterName` TINYTEXT NOT NULL, `ProvedReports` INT NOT NULL, `beProvedReported` INT NOT NULL)";
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection();
             PreparedStatement statement = connection.prepareStatement(create);
             PreparedStatement statement2 = connection.prepareStatement(createRank)) {
            statement.execute();
            statement2.execute();
        } catch( SQLException e ) {
            e.printStackTrace();
        }

        ConfigManager.getInstance().loadConfig();

        Bukkit.getPluginManager().registerEvents(new ReportListeners(), this);

        getCommand("reportadmin").setExecutor(new AdminReportCommand(this));
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("reportreload").setExecutor(new ReloadCommand());

        if (getServer().getPluginManager().getPlugin("PlaceHolderAPI") != null) {
            getLogger().info("Found PlaceHolderAPI ! Hooking into it ....");
            new PlaceholderManager(this).register();
        }

        getServer().getLogger().info("Connecting to redis server...");
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try (Jedis redis = RedisManager.getInstance().getRedis()) {
                if (redis.isConnected()) this.getLogger().info("Successfully connected to redis server.");
                Subscription subscription = Subscription.getInstance();
                subscription.setJedisPubSub(new RedisChannelListeners());
                JedisPubSub jedisPubSub = subscription.getJedisPubSub();
                redis.subscribe(jedisPubSub, "Report-Spigot");
            } catch (JedisException e) {
                e.printStackTrace();
                getLogger().info("Cannot connect to redis server! disabling plugin....");
                getPluginLoader().disablePlugin(this);
            }
        });

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "===== " + ChatColor.AQUA + "ReportSystem enabled! " + ChatColor.GREEN + "=====");
    }

    @Override
    public void onDisable() {
        RedisManager.getInstance().closePool();
    }
}
