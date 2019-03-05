package com.reporter.sys.ericlam.manager;

import com.hypernite.mysql.SQLDataSourceManager;
import com.reporter.sys.ericlam.containers.ReportInfo;
import com.reporter.sys.ericlam.enums.ReasonType;
import com.reporter.sys.ericlam.enums.ReportState;
import com.reporter.sys.ericlam.exceptions.NoAvailableReportException;
import com.reporter.sys.ericlam.exceptions.ReportNonExistException;
import com.reporter.sys.ericlam.exceptions.ReportNotOpenException;
import com.reporter.sys.ericlam.redis.RedisChannel;
import com.reporter.sys.milkd.main.ReportSystem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

public class ReportManager {

    //Default values
    private ReportState Default = ReportState.OPEN;
    private String server = ConfigManager.server;
    private static ReportManager manager;

    private HashMap<UUID, Set<ReasonType>> duplicateds = new HashMap<>();


    public HashMap<UUID, Set<ReasonType>> getDuplicateds() {
        return duplicateds;
    }

    public static ReportManager getInstance() {
        if (manager == null) manager = new ReportManager();
        return manager;

    }

    public void getThanksGiving(Player player) {
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT `Reason`,`ReportedName` FROM `ReportSystem` WHERE `ReporterUUID`=? AND `State`=?");
             PreparedStatement update = connection.prepareStatement("UPDATE `ReportSystem` SET `State`=? WHERE `ReporterUUID`=? AND `State`=?")) {
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, ReportState.PROVED.toString());
            update.setString(1, ReportState.THANKED.toString());
            update.setString(2, player.getUniqueId().toString());
            update.setString(3, ReportState.PROVED.toString());
            ResultSet resultSet = statement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                ReasonType reason = ReasonType.valueOf(resultSet.getString("Reason"));
                String reportedname = resultSet.getString("ReportedName");
                player.sendMessage(ConfigManager.thankGive.replace("<player>", reportedname).replace("<reason>", reason.getTitle()));
                i++;
            }
            if (i > 0) {
                Bukkit.getScheduler().runTask(ReportSystem.plugin, () -> player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1));
                update.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String[] updateName(UUID target, UUID reporter, String oTargetN, String oReportN) {
        String targetName = Bukkit.getOfflinePlayer(target).getName();
        String reporterName = Bukkit.getOfflinePlayer(reporter).getName();
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection();
             PreparedStatement updateReporter = connection.prepareStatement("UPDATE `ReportSystem` SET `ReporterName`=? WHERE `ReporterUUID`=?");
             PreparedStatement updateTarget = connection.prepareStatement("UPDATE `ReportSystem` SET `ReportedName`=? WHERE `ReportedUUID`=?")) {
            targetName = getString(target, oTargetN, targetName, updateTarget);
            reporterName = getString(reporter, oReportN, reporterName, updateReporter);
            return new String[]{reporterName, targetName};
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{oReportN, oTargetN};
        }
    }

    private String getString(UUID reporter, String oReportN, String reporterName, PreparedStatement updateReporter) throws SQLException {
        if (reporterName != null && !reporterName.equals(oReportN)) {
            updateReporter.setString(1, reporterName);
            updateReporter.setString(2, reporter.toString());
            updateReporter.execute();
        } else {
            reporterName = oReportN;
        }
        return reporterName;
    }

    /*public void updateReportsTimeStamp() {
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection();
             PreparedStatement check = connection.prepareStatement("SELECT `ReportID`,`TimeStamp`,`ReportedName`,`ReportedUUID`,`ReporterName`,`ReporterUUID` FROM `ReportSystem` WHERE `State`=?");
             PreparedStatement update = connection.prepareStatement("UPDATE `ReportSystem` SET `State`=? WHERE `ReportID`=?")) {
            check.setString(1, ReportState.OPEN.toString());
            ResultSet resultSet = check.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ReportID");
                long time = resultSet.getLong("TimeStamp");
                String oReportN = resultSet.getString("ReporterName");
                String oTargetN = resultSet.getString("ReportedName");
                UUID reporter = UUID.fromString(resultSet.getString("ReporterUUID"));
                UUID target = UUID.fromString(resultSet.getString("ReportedUUID"));
                updateName(target, reporter, oTargetN, oReportN);
                LocalDateTime reportTime = new Timestamp(time).toLocalDateTime();
                LocalDateTime now = Timestamp.from(Instant.now()).toLocalDateTime();
                Duration duration = Duration.between(reportTime, now);
                if (duration.toDays() > ConfigManager.expireDays) {
                    update.setString(1, ReportState.EXPIRE.toString());
                    update.setInt(2, id);
                    update.execute();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/ // it should be done by bungeecord

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
                String oReportN = report.getString("ReporterName");
                String oTargetN = report.getString("ReportedName");
                String[] name = updateName(target, reporter, oTargetN, oReportN);
                String reporterN = name[0];
                String reportedN = name[1];
                ReasonType reason = ReasonType.valueOf(report.getString("Reason"));
                ReportState state = ReportState.valueOf(report.getString("State"));
                int reportid = report.getInt("ReportID");
                long timestamp = report.getLong("TimeStamp");
                Optional<String> fakeoperator = Optional.ofNullable(report.getString("Operator"));
                String operator = fakeoperator.orElse("NONE");
                String server = report.getString("Server");
                return new ReportInfo(reportid, reporter, target, reporterN, reportedN, reason, timestamp, state, operator, server);
            } else {
                throw new ReportNonExistException(ConfigManager.noThisReport.replace("<id>", ID + ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean handleReport(int id, ReportState state, Player operator) throws ReportNotOpenException, ReportNonExistException {
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection();
             PreparedStatement query = connection.prepareStatement("SELECT `State`,`ReportedUUID`,`ReportedName`,`ReporterUUID`,`ReporterName` FROM`ReportSystem` WHERE `ReportID` =?");
             PreparedStatement execute = connection.prepareStatement("UPDATE `ReportSystem` SET `State` = ? WHERE `ReportID` = ?")) {
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            if (!resultSet.next()) {
                throw new ReportNonExistException(ConfigManager.noThisReport.replace("<id>", id + ""));
            }
            ReportState currentState = ReportState.valueOf(resultSet.getString("state"));
            UUID target = UUID.fromString(resultSet.getString("ReportedUUID"));
            String originalReporterName = resultSet.getString("ReporterName");
            String originalReportedName = resultSet.getString("ReportedName");
            UUID reporter = UUID.fromString(resultSet.getString("ReporterUUID"));
            String[] name = updateName(target, reporter, originalReportedName, originalReporterName);
            String reportedName = name[1];
            if (currentState == ReportState.OPEN || currentState == ReportState.HANDLING || state == ReportState.HANDLING) {
                execute.setString(1, state.toString());
                execute.setInt(2, id);
                duplicateds.remove(target); //also remove cache
                RedisChannel.clearCache(target); // also call other server to remove cache
                execute.execute();
                RedisChannel.uploadHandle(reportedName, target, id, state, currentState, operator);
                return true;
            } else {
                throw new ReportNotOpenException(ConfigManager.notOpen.replace("<id>", id + "").replace("<state>", currentState.getTitle()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasReportID(int id) {
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement ps = connection.prepareStatement("SELECT `ReportID` FROM `ReportSystem` WHERE `ReportID`=?")) {
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public void addReport(Player player, OfflinePlayer reportedTarget,
                          ReasonType reason, Timestamp time) {
        String reporterUUID = player.getUniqueId().toString();
        String targetUUID = reportedTarget.getUniqueId().toString();
        if (hasReported(reportedTarget.getUniqueId(), reason))
            return; //if duplicated, show player report success and avoid the duplication silently

        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement newreport = connection.prepareStatement("INSERT INTO `ReportSystem` (ReporterName, ReporterUUID,ReportedName, ReportedUUID,Reason, `TimeStamp`, `State`, `Server`, Operator ) VALUE (?,?,?,?,?,?,?,?,? )")) {
            newreport.setString(1, player.getName());
            newreport.setString(2, reporterUUID);
            newreport.setString(3, reportedTarget.getName());
            newreport.setString(4, targetUUID);
            newreport.setString(5, reason.toString());
            newreport.setLong(6, time.getTime());
            newreport.setString(7, Default.toString());
            newreport.setString(8, server);
            newreport.setString(9, null);
            newreport.execute();
            RedisChannel.broadcastReport(player.getName(), reportedTarget.getName(), reason);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
