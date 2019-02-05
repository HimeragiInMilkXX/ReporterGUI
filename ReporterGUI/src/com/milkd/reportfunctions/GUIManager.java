package com.milkd.reportfunctions;

import com.caxerx.builders.InventoryBuilder;
import com.ericlam.manager.ConfigManager;
import org.bukkit.inventory.Inventory;

public class GUIManager {
    private static GUIManager gui;
    private Inventory reportGUI;

    private GUIManager() {
        reportGUI = new InventoryBuilder(ConfigManager.reportGuiSize, ConfigManager.reportGuiTitle).build();


    }

    public static GUIManager getInstance() {
        if (gui == null) gui = new GUIManager();
        return gui;
    }

}
