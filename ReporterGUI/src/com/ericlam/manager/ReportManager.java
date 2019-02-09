package com.ericlam.manager;

import com.ericlam.containers.ReportInfo;
import com.ericlam.enums.ReasonType;
import com.ericlam.enums.ReportState;
import com.ericlam.exceptions.ReportNonExistException;
import com.ericlam.exceptions.ReportNotOpenException;
import com.hypernite.mysql.SQLDataSourceManager;
import com.milkd.main.ReportSystem;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ReportManager {

    //Default values
    private ReportState Default = ReportState.OPEN;
    private String server = ConfigManager.server;
    private static ReportManager manager;

    public static ReportManager getInstance() {
        if (manager == null) manager = new ReportManager();
        return manager;

    }

    public ReportInfo getReportInfo(int ID) throws ReportNonExistException {
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + ReportSystem.table + " WHERE ReportID = ?")) {

            ps.setInt(1, ID);

            ResultSet report = ps.executeQuery();
            if (report.next()) {
                UUID reporter = UUID.fromString(report.getString("`ReporterUUID`"));
                UUID target = UUID.fromString(report.getString("`ReportedUUID`"));
                ReasonType reason = ReasonType.valueOf(report.getString("`Reason`"));
                ReportState state = ReportState.valueOf(report.getString("`State`"));
                int reportid = report.getInt("ReportID");
                long timestamp = report.getLong("TimeStamp");
                String operator = report.getString("Operator");

                return new ReportInfo(reportid, reporter, target, reason, timestamp, state, operator);
            } else {
                throw new ReportNonExistException(ConfigManager.noThisReport.replace("<id>", ID + ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private HashMap<UUID, Set<ReasonType>> duplicateds = new HashMap<>();

    public boolean handleReport(int id, ReportState state) throws ReportNotOpenException, ReportNonExistException {
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection();
             PreparedStatement query = connection.prepareStatement("SELECT `State`,`ReportedUUID` FROM`ReportSystem` WHERE `ReportID` =?");
             PreparedStatement execute = connection.prepareStatement("UPDATE `ReportSystem` SET `State` = ? WHERE `ReportID` = ?")) {
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            if (!resultSet.next())
                throw new ReportNonExistException(ConfigManager.noThisReport.replace("<id>", id + ""));
            ReportState currentState = ReportState.valueOf(resultSet.getString("state"));
            UUID reportedUUID = UUID.fromString(resultSet.getString("ReportedUUID"));
            if (currentState == ReportState.OPEN || currentState == ReportState.HANDLING) {
                execute.setString(1, state.toString());
                execute.setInt(2, id);
                duplicateds.remove(reportedUUID); //also remove cache
                return execute.execute();
            } else {
                throw new ReportNotOpenException(ConfigManager.notOpen.replace("<id>", id + "").replace("<state>", currentState.toString()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean hasReported(UUID Tuuid, ReasonType reason) {

        if (duplicateds.containsKey(Tuuid) && duplicateds.get(Tuuid).contains(reason)) return true; //cache

        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement ps = connection.prepareStatement("SELECT `ReportedUUID` FROM `ReportSystem` WHERE `ReportedUUID` = ? AND `State`=? AND `Server`=? AND `Reason`=?")) {

            ps.setString( 1, Tuuid.toString() );
            ps.setString(2, ReportState.OPEN.toString());
            ps.setString(3, ConfigManager.server);
            ps.setString(4, reason.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (!duplicateds.containsKey(Tuuid)) duplicateds.put(Tuuid, new HashSet<>());
                duplicateds.get(Tuuid).add(reason);
                return true;
            } else {
                return false;
            }

        } catch( SQLException ex ) {

            ex.printStackTrace();
            return false;

        }

    }

    public void addReport(String Pname, String Tname,
                          UUID Puuid, UUID Tuuid,
                          ReasonType reason, Timestamp time) {

        String reporter = Puuid.toString();
        String target = Tuuid.toString();
        if (hasReported(Tuuid, reason))
            return; //if duplicated, show player report success and avoid the duplication silently

        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement newreport = connection.prepareStatement("INSERT INTO `ReportSystem` ( ReportID, ReporterName, ReporterUUID,ReportedName, ReportedUUID,Reason, `TimeStamp`, State, `Server`, Operator ) VALUE ( ?,?,?,?,?,?,?,?,?,? )")) {

            newreport.setString(2, Pname);
            newreport.setString(3, reporter);
            newreport.setString(4, Tname);
            newreport.setString(5, target);
            newreport.setString(6, reason.toString());
            newreport.setLong(7, time.getTime());
            newreport.setString(8, Default.toString());
            newreport.setString(9, server);
            newreport.setString(10, null);

            newreport.execute();

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

    }

}
