package com.reporter.sys.ericlam.enums;

import java.util.ArrayList;
import java.util.List;

public enum ReportState {
    OPEN("§e開放審查"), EXPIRE("§b已過期"), DENIED("§c否定舉報"), DUPLICATED("§c重複檢舉"), PROVED("§a舉報屬實"), HANDLING("§e審查中"), THANKED("§a舉報屬實");

    private String title;

    ReportState(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getItemName() {
        return "§7設置為 " + title;
    }

    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("§8點擊以設置為 " + title);
        return lore;
    }
}
