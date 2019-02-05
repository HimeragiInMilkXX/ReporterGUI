package com.ericlam.manager;

import com.ericlam.state.ReportState;
import com.milkd.reporter.ReporterGUI;
import mysql.hypernite.mc.SQLDataSourceManager;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class ReportManager {

    //Default values
    ReportState Default = ReportState.OPEN;
    String server = ReporterGUI.server;
    String table = ReporterGUI.table;

    public String translateColor( String msg ) {

        msg = msg.replaceAll("&", "§");

        return msg;

    }

    private ReporterGUI plugin = ReporterGUI.getPlugin( ReporterGUI.class );

    private static ReportManager manager;

    public static ReportManager getInstance() {
        if (manager == null) manager = new ReportManager();
        return manager;

    }

    public boolean hasReported( UUID Tuuid, UUID Puuid) {

        try {

            String reporteruuid;
            String state;

            PreparedStatement ps = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement( "SELECT * FROM " + table + " WHERE ReportedUUID = ?" );
            ps.setString( 1, Tuuid.toString() );
            ResultSet rs = ps.executeQuery();
            reporteruuid = rs.getString( "`ReporterUUID`" );
            state = rs.getString( "`State`" );

            if ( reporteruuid == Puuid.toString() & state == Default.toString() )
                return true;
            else return false;

        } catch( SQLException ex ) {

            ex.printStackTrace();

        }

        return false;

    }

    public void addReport( String Pname, String Tname,
                           UUID Puuid, UUID Tuuid,
                           String Reason, Timestamp time, Player player ) {

        String reporter = Puuid.toString();
        String target = Tuuid.toString();

        if( !( hasReported( Tuuid, Puuid) ) ) {

            try {

                PreparedStatement newreport = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement( "INSERT INTO " + ReporterGUI.table +
                                                                                                                             " ( ReportID," +
                                                                                                                             " ReporterName, ReporterUUID," +
                                                                                                                             " ReportedName, ReportedUUID," +
                                                                                                                             " Reason, TimeStamp, State, Server, Operator ) VALUE ( ?,?,?,?,?,?,?,?,?,? )" );

                newreport.setString( 2, Pname );
                newreport.setString( 3, reporter );
                newreport.setString( 4, Tname );
                newreport.setString( 5, target );
                newreport.setString( 6, Reason );
                newreport.setString( 7, time.toString() );
                newreport.setString( 8, Default.toString() );
                newreport.setString( 9, server );
                newreport.setString( 10, "---" );

                newreport.executeUpdate();

                player.closeInventory();
                player.sendMessage( translateColor( "&cYou have reported &a" + Tname + "&c with the reason of &e" + Reason ) );

            } catch( SQLException ex ) {

                ex.printStackTrace();

            }

        }

    }

}
