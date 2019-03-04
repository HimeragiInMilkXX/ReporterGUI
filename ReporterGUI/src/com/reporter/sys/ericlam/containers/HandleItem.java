package com.reporter.sys.ericlam.containers;

import com.reporter.sys.ericlam.enums.ReportState;
import org.bukkit.Material;

import java.util.List;

public class HandleItem {
    private Material material;
    private String title;
    private List<String> lores;
    private int slot;
    private ReportState state;

    public HandleItem(Material material, String title, List<String> lores, int slot, ReportState state) {
        this.material = material;
        this.title = title;
        this.lores = lores;
        this.slot = slot;
        this.state = state;
    }

    public Material getMaterial() {
        return material;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLores() {
        return lores;
    }

    public int getSlot() {
        return slot;
    }

    public ReportState getState() {
        return state;
    }
}
