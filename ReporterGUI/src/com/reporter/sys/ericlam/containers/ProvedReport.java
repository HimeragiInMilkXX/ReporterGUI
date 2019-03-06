package com.reporter.sys.ericlam.containers;

public class ProvedReport {
    private int proved;
    private int beProved;

    public ProvedReport(int proved, int beProved) {
        this.proved = proved;
        this.beProved = beProved;
    }

    public int getProved() {
        return proved;
    }

    public int getBeProved() {
        return beProved;
    }
}
