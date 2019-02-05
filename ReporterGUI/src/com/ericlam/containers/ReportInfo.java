package com.ericlam.containers;

import com.ericlam.enums.ReasonType;
import com.ericlam.enums.ReportState;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class ReportInfo {
    private int reportID;
    private UUID reporterUUID;
    private OfflinePlayer reporter;
    private UUID reportedUUID;
    private OfflinePlayer reported;
    private ReasonType reason;
    private long timestamp;
    private ReportState state;
    private String operator;

    public ReportInfo(int reportID, UUID reporterUUID, UUID reportedUUID, ReasonType reason, long timestamp, ReportState state, String operator) {
        this.reportID = reportID;
        this.reporterUUID = reporterUUID;
        this.reporter = Bukkit.getOfflinePlayer(reporterUUID);
        this.reportedUUID = reportedUUID;
        this.reported = Bukkit.getOfflinePlayer(reportedUUID);
        this.reason = reason;
        this.timestamp = timestamp;
        this.state = state;
        this.operator = operator;
    }

    public int getReportID() {
        return reportID;
    }

    public UUID getReporterUUID() {
        return reporterUUID;
    }

    public OfflinePlayer getReporter() {
        return reporter;
    }

    public UUID getReportedUUID() {
        return reportedUUID;
    }

    public OfflinePlayer getReported() {
        return reported;
    }

    public ReasonType getReason() {
        return reason;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public ReportState getState() {
        return state;
    }

    public String getOperator() {
        return operator;
    }
}
