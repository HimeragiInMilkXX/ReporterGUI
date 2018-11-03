package com.milkd.reporter;

import com.milkd.chats.ChatFilter;
import com.milkd.reportfunctions.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public class ReporterGUI extends JavaPlugin {

    private Connection connection;
    public String host, database, username, password, table;
    public int port;

    public void onEnable() {

        loadconfig();

        mysqlSetup();

        String create = "CREATE TABLE IF NOT EXISTS `Report_sys` ( `CODE` VARCHAR( 6 ) NOT NULL, `Reporter` VARCHAR( 40 ) NOT NULL, `Reported` VARCHAR( 40 ) NOT NULL, `Reason` VARCHAR( 200 ) NOT NULL, `Time` VARCHAR( 20 ) )";

        try {

            PreparedStatement statement = getConnection().prepareStatement( create );
            statement.executeUpdate();

        } catch( SQLException e ) {

            e.printStackTrace();

        }
        Bukkit.getPluginManager().registerEvents(new GUIClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ChatFilter(), this);
        getCommand("report").setExecutor(new ReportCommand());

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "ReporterGUI enabled!");

    }

    public void loadconfig() {

        this.getConfig().options().copyDefaults( true );
        saveConfig();

    } //End of loadconfig

    public void mysqlSetup() {

        host = this.getConfig().getString( "host" );
        port = this.getConfig().getInt( "port" );
        database = this.getConfig().getString( "database" );
        password = this.getConfig().getString( "password" );
        username = this.getConfig().getString( "username" );
        table = this.getConfig().getString( "table" );

        try {

            synchronized ( this ) {

                if( getConnection() != null && !getConnection().isClosed() ) {

                    return;

                } //End of if

            } //End of synchronized

            Class.forName( "com.mysql.jdbc.Driver" );

            setConnection( DriverManager.getConnection( "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                    this.username, this.password ) );

            Bukkit.getConsoleSender().sendMessage( ChatColor.GREEN + "MySQL connected" );

        } catch( SQLException e ) {

            e.printStackTrace();

        } catch( ClassNotFoundException e ) {

            e.printStackTrace();

        } //End of catches

    } //End of mysqlSetup

    public Connection getConnection() {

        return this.connection;

    } //End of getConnection

    public Connection setConnection( Connection connection ) {

        return this.connection = connection;

    } //End of setConnection

    public long timestamp() {

        Timestamp timestamp = new Timestamp( System.currentTimeMillis() );

        return timestamp.getTime();

    } //End of timestamp

    public void setCODE( PreparedStatement ps ) {

        int count = this.getConfig().getInt( "report_value_count" );

        try {

            ps.setString(1, "0" + count);
            this.getConfig().set( "report_value_count", count++ );

        } catch( SQLException e ) {

            e.printStackTrace();

        }

    } //End of setCODE

}
