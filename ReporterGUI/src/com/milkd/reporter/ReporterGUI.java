package com.milkd.reporter;

import com.hypernite.mysql.SQLDataSourceManager;
import com.milkd.chats.ChatFilter;
import com.milkd.commands.AdminReportCommand;
import com.milkd.commands.ReportCommand;
import com.milkd.reportfunctions.GUIClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReporterGUI extends JavaPlugin {

    public static String server;
    public static Plugin plugin;

    public static String table;

    public void onEnable() {
        plugin = this;

        server = this.getConfig().getString( "server" );
        table = this.getConfig().getString( "table" );

        loadconfig();

        String create = "CREATE TABLE IF NOT EXISTS `Report_sys` ( " +
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

        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement statement = connection.prepareStatement(create)) {
            statement.execute();
        } catch( SQLException e ) {
            e.printStackTrace();
        }

        Bukkit.getPluginManager().registerEvents(new GUIClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ChatFilter(), this);

        getCommand("reports").setExecutor(new AdminReportCommand(this));
        getCommand("report").setExecutor(new ReportCommand());


        getServer().getConsoleSender().sendMessage(ChatColor.RED + "ReporterGUI enabled!");

    }

    public void loadconfig() {

        this.getConfig().options().copyDefaults( true );
        saveConfig();

    } //End of loadconfig

}
