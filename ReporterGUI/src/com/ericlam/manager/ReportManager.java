package com.ericlam.manager;

import com.ericlam.containers.ReportInfo;
import com.ericlam.enums.ReasonType;
import com.ericlam.enums.ReportState;
import com.ericlam.exceptions.ReportNotOpenException;
import com.hypernite.mysql.SQLDataSourceManager;
import com.milkd.reporter.ReporterGUI;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class ReportManager {

    //Default values
    private ReportState Default = ReportState.OPEN;
    private String server = ReporterGUI.server;
    private static ReportManager manager;

    public static ReportManager getInstance() {
        if (manager == null) manager = new ReportManager();
        return manager;

    }

    public ReportInfo getReportInfo(int ID) {
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + ReporterGUI.table + " WHERE ReportID = ?")) {

            ps.setInt(1, ID);

            ResultSet report = ps.executeQuery();
            UUID reporter = UUID.fromString(report.getString("`ReporterUUID`"));
            UUID target = UUID.fromString(report.getString("`ReportedUUID`"));
            ReasonType reason = ReasonType.valueOf(report.getString("`Reason`"));
            ReportState state = ReportState.valueOf(report.getString("`State`"));
            int reportid = report.getInt("ReportID");
            long timestamp = report.getLong("TimeStamp");
            String operator = report.getString("Operator");

            return new ReportInfo(reportid, reporter, target, reason, timestamp, state, operator);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean handleReport(int id, ReportState state) throws ReportNotOpenException {
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection();
             PreparedStatement query = connection.prepareStatement("SELECT `State` FROM`ReportSystem` WHERE `ReportID` =?");
             PreparedStatement execute = connection.prepareStatement("UPDATE `ReportSystem` SET `State` = ? WHERE `ReportID` = ?")) {
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();

            ReportState currentState = ReportState.valueOf(resultSet.getString("state"));

            if (currentState == ReportState.OPEN) {
                execute.setString(1, state.toString());
                execute.setInt(2, id);
                return execute.execute();
            } else {
                throw new ReportNotOpenException(ConfigManager.notOpen.replace("<id>", id + "").replace("<state>", currentState.toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasReported( UUID Tuuid, UUID Puuid) {

        try {

            String reporteruuid;
            String state;

            PreparedStatement ps = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement("SELECT * FROM `ReportSystem` WHERE ReportedUUID = ?");
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

                PreparedStatement newreport = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement("INSERT INTO `ReportSystem`" +
                                                                                                                             " ( ReportID," +
                                                                                                                             " ReporterName, ReporterUUID," +
                                                                                                                             " ReportedName, ReportedUUID," +
                                                                                                                             " Reason, TimeStamp, State, Server, Operator ) VALUE ( ?,?,?,?,?,?,?,?,?,? )" );

                newreport.setString( 2, Pname );
                newreport.setString( 3, reporter );
                newreport.setString( 4, Tname );
                newreport.setString( 5, target );
                newreport.setString( 6, Reason );
                newreport.setLong(7, time.getTime());
                newreport.setString( 8, Default.toString() );
                newreport.setString( 9, server );
                newreport.setString( 10, "---" );

                newreport.executeUpdate();

                player.closeInventory();
                player.sendMessage("&cYou have reported &a" + Tname + "&c with the reason of &e" + Reason);

            } catch( SQLException ex ) {

                ex.printStackTrace();

            }

        }

    }

}
