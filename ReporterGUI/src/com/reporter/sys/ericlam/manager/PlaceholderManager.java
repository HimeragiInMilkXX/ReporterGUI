package com.reporter.sys.ericlam.manager;

import com.reporter.sys.milkd.main.ReportSystem;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlaceholderManager extends PlaceholderExpansion {

    private Plugin plugin;
    private ProvedCountManager countManager;

    public PlaceholderManager(ReportSystem plugin) {
        this.plugin = plugin;
        countManager = ProvedCountManager.getInstance();
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        switch (params) {
            case "proved":
                return countManager.getProved(p) + "";
            case "be_proved":
                return countManager.getbeProved(p) + "";
            default:
                return null;
        }
    }

    @Override
    public String getIdentifier() {
        return plugin.getName();
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
