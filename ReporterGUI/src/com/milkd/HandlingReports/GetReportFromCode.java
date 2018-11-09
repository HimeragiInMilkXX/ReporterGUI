package com.milkd.HandlingReports;

import com.milkd.reporter.ReporterGUI;
import mysql.hypernite.mc.SQLDataSourceManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetReportFromCode implements CommandExecutor {

    private ReporterGUI plugin = ReporterGUI.getPlugin( ReporterGUI.class );

    PreparedStatement ps;
    ResultSet rs;

    String Reporter;
    String Reported;
    String Reason;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args ) {

        Player player = ( Player ) sender;

        if( args.length == 0 ) {

            player.sendMessage( "§e[ReporterGUI] by MilkD" );

        } //End of 0if

        if( args.length == 1 )
            player.sendMessage( "§cUnknown Arguments! Type /reportergui for help" );

        if( args.length == 2 ) {

            String target = args[1];

            if( args[0].equalsIgnoreCase( "handle" ) ) {

                try {

                    ps = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement( "DELETE FROM " + plugin.table + " WHERE `CODE` = " + target );
                    ps.executeUpdate();
                    player.sendMessage( "§cThe Report of CODE §a" + target + " §chas been handled" );
                    player.sendMessage( "§a-----------------------------------------" +
                                            "§eReporter &c" + Reporter +
                                            "§eReported &c" + Reported +
                                            "§eReason &c" + Reason +
                                            "§a-----------------------------------------" );

                } catch( SQLException e ) {

                    e.printStackTrace();

                } //End of try-catch block;

            } //End of 2-r if

        } //End of 2if

        return false;

    } //End of onCommand

    public void setInfo( String target ) {

        try {

            ps = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement( "SELECT Reporter FROM " + plugin.table + " WHERE `CODE` = " + target );
            rs = ps.executeQuery();
            Reporter = rs.getString( "Reporter" );

            ps = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement( "SELECT Reported FROM " + plugin.table + " WHERE `CODE` = " + target );
            rs = ps.executeQuery();
            Reported = rs.getString( "Reported" );

            ps = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement( "SELECT Reason FROM " + plugin.table + " WHERE `CODE` = " + target );
            rs = ps.executeQuery();
            Reason = rs.getString( "Reason" );

        } catch( SQLException e ) {

            e.printStackTrace();

        } //End of try-catch block

    } //End of setInfo

}
