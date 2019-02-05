package com.ericlam.containers;

import com.ericlam.enums.ReasonType;
import org.bukkit.Material;

import java.util.List;

public class ReportItem {
    private Material material;
    private String title;
    private List<String> lores;
    private int slot;
    private ReasonType reason;

    public ReportItem(Material material, String title, List<String> lores, int slot, ReasonType reason) {
        this.material = material;
        this.title = title;
        this.lores = lores;
        this.slot = slot;
        this.reason = reason;
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

    public ReasonType getReason() {
        return reason;
    }
}
