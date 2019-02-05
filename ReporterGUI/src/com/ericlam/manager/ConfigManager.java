package com.ericlam.manager;

import com.ericlam.containers.ReportItem;
import com.ericlam.enums.ReasonType;
import com.milkd.reporter.ReporterGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashSet;
import java.util.List;

public class ConfigManager {
    //for config
    public static String server, reportGuiTitle, handleGuiTitle;
    public static int reportGuiSize, handleGuiSize;
    //for messages
    public static String reportHints, notValue, notOpen, handled, notPlayer, noPerm, requirePlayer, noThisPlayer, noThisReport, handleFail;
    public static String[] helps, details;
    private static ConfigManager configManager;
    private File configFile;
    private FileConfiguration config;
    private File reportFile;
    private FileConfiguration report;
    private File messageFile;
    private FileConfiguration message;
    private HashSet<ReportItem> reportItems = new HashSet<>();

    private ConfigManager() {
        Plugin plugin = ReporterGUI.plugin;
        configFile = new File(plugin.getDataFolder(), "config.yml");
        messageFile = new File(plugin.getDataFolder(), "messages.yml");
        reportFile = new File(plugin.getDataFolder(), "report-items.yml");
        if (!configFile.exists()) plugin.saveResource("config.yml", true);
        if (!messageFile.exists()) plugin.saveResource("messages.yml", true);
        if (!reportFile.exists()) plugin.saveResource("report-items.yml", true);
        config = YamlConfiguration.loadConfiguration(configFile);
        message = YamlConfiguration.loadConfiguration(messageFile);
    }

    public static ConfigManager getInstance() {
        if (configManager == null) configManager = new ConfigManager();
        return configManager;
    }

    //get report items
    public HashSet<ReportItem> getReportItems() {
        return reportItems;
    }

    private String translate(String path) {
        return ChatColor.translateAlternateColorCodes('&', message.getString(path));
    }

    private String[] translate(List<String> lists) {
        return lists.stream().map(msg -> ChatColor.translateAlternateColorCodes('&', msg)).toArray(String[]::new);
    }

    public void loadConfig() {
        //load report items
        for (String key : report.getKeys(false)) {
            Material material = Material.valueOf(report.getString(key + ".material"));
            String title = report.getString(key + ".name");
            List<String> lores = report.getStringList(key + ".lores");
            int slot = report.getInt(key + ".slot");
            ReasonType reason = ReasonType.valueOf(key);
            reportItems.add(new ReportItem(material, title, lores, slot, reason));
        }
        //load config
        server = config.getString("server");
        reportGuiTitle = config.getString("report-gui.title").replaceAll("&", "§");
        reportGuiSize = config.getInt("report-gui.size");
        handleGuiSize = config.getInt("handle-gui.size");
        handleGuiTitle = config.getString("handle-gui.title").replaceAll("&", "§");

        //load messages
        reportHints = translate("report-hints");
        notValue = translate("not-value");
        notOpen = translate("not-open");
        handled = translate("handled");
        handleFail = translate("handled-fail");
        notPlayer = translate("not-player");
        noPerm = translate("no-perm");
        requirePlayer = translate("require-player");
        noThisPlayer = translate("no-this-player");
        noThisReport = translate("no-this-report");

        helps = translate(report.getStringList("help"));
        details = translate(report.getStringList("details"));
    }
}
