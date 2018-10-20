package com.milkd.reporter;

import com.milkd.chats.ChatFilter;
import com.milkd.reportfunctions.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ReporterGUI extends JavaPlugin {

    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(new GUIClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ChatFilter(), this);
        getCommand("report").setExecutor(new ReportCommand());

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "ReporterGUI enabled!");

    }

}
