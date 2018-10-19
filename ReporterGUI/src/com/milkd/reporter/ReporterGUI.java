package com.milkd.reporter;

import com.milkd.reportfunctions.GUI;
import com.milkd.reportfunctions.ReportCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ReporterGUI extends JavaPlugin {

    public void onEnable() {

        Bukkit.getPluginManager().registerEvents( new GUI(), this );
        getCommand( "report" ).setExecutor( new ReportCommand() );

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "ReporterGUI enabled!" );

    }


}
