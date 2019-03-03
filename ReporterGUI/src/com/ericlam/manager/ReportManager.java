package com.ericlam.manager;

import com.ericlam.containers.ReportInfo;
import com.ericlam.enums.ReasonType;
import com.ericlam.enums.ReportState;
import com.ericlam.exceptions.NoAvailableReportException;
import com.ericlam.exceptions.ReportNonExistException;
import com.ericlam.exceptions.ReportNotOpenException;
import com.hypernite.mysql.SQLDataSourceManager;

import java.sql.*;
import java.util.*;

public class ReportManager {

    //Default values
    private ReportState Default = ReportState.OPEN;
    private String server = ConfigManager.server;
    private static ReportManager manager;

    public static ReportManager getInstance() {
        if (manager == null) manager = new ReportManager();
        return manager;

    }

    public HashMap<Integer, ReportState> getAvaliableReports() throws NoAvailableReportException {
        HashMap<Integer, ReportState> reports = new HashMap<>();
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement ps = connection.prepareStatement("SELECT `ReportID`,`State` FROM `ReportSystem` WHERE `State`=? OR `State`=? LIMIT 99")) {
            ps.setString(1, ReportState.OPEN.toString());
            ps.setString(2, ReportState.HANDLING.toString());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int reportID = resultSet.getInt("ReportID");
                ReportState state = ReportState.valueOf(resultSet.getString("State"));
                reports.putIfAbsent(reportID, state);
            }
            if (reports.size() == 0) throw new NoAvailableReportException();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public ReportInfo getReportInfo(int ID) throws ReportNonExistException {
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM `ReportSystem` WHERE ReportID = ?")) {

            ps.setInt(1, ID);

            ResultSet report = ps.executeQuery();
            if (report.next()) {
                UUID reporter = UUID.fromString(report.getString("ReporterUUID"));
                UUID target = UUID.fromString(report.getString("ReportedUUID"));
                ReasonType reason = ReasonType.valueOf(report.getString("Reason"));
                ReportState state = ReportState.valueOf(report.getString("State"));
                int reportid = report.getInt("ReportID");
                long timestamp = report.getLong("TimeStamp");
                Optional<String> fakeoperator = Optional.ofNullable(report.getString("Operator"));
                String operator = fakeoperator.orElse("NONE");
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
            if (currentState == ReportState.OPEN || currentState == ReportState.HANDLING || state == ReportState.HANDLING) {
                execute.setString(1, state.toString());
                execute.setInt(2, id);
                duplicateds.remove(reportedUUID); //also remove cache
                execute.execute();
                return true;
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

        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement newreport = connection.prepareStatement("INSERT INTO `ReportSystem` (ReporterName, ReporterUUID,ReportedName, ReportedUUID,Reason, `TimeStamp`, `State`, `Server`, Operator ) VALUE (?,?,?,?,?,?,?,?,? )")) {
            newreport.setString(1, Pname);
            newreport.setString(2, reporter);
            newreport.setString(3, Tname);
            newreport.setString(4, target);
            newreport.setString(5, reason.toString());
            newreport.setLong(6, time.getTime());
            newreport.setString(7, Default.toString());
            newreport.setString(8, server);
            newreport.setString(9, null);

            newreport.execute();

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

    }

}
