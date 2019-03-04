package com.reporter.sys.ericlam.containers;

import com.reporter.sys.ericlam.enums.ReasonType;
import com.reporter.sys.ericlam.enums.ReportState;

import java.util.UUID;

public class ReportInfo {
    private int reportID;
    private UUID reporterUUID;
    private String reporter;
    private UUID reportedUUID;
    private String reported;
    private ReasonType reason;
    private String server;
    private long timestamp;
    private ReportState state;
    private String operator;

    public ReportInfo(int reportID, UUID reporterUUID, UUID reportedUUID, String reporter, String reported, ReasonType reason, long timestamp, ReportState state, String operator, String server) {
        this.reportID = reportID;
        this.reporterUUID = reporterUUID;
        this.reporter = reporter;
        this.reportedUUID = reportedUUID;
        this.reported = reported;
        this.reason = reason;
        this.timestamp = timestamp;
        this.state = state;
        this.operator = operator;
        this.server = server;
    }

    public String getServer() {
        return server;
    }

    public int getReportID() {
        return reportID;
    }

    public UUID getReporterUUID() {
        return reporterUUID;
    }

    public String getReporter() {
        return reporter;
    }

    public UUID getReportedUUID() {
        return reportedUUID;
    }

    public String getReported() {
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
