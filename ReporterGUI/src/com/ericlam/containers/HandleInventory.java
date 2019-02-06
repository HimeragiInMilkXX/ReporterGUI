package com.ericlam.containers;

import com.caxerx.builders.InventoryBuilder;
import com.caxerx.builders.ItemStackBuilder;
import com.ericlam.exceptions.ReportNotOpenException;
import com.ericlam.manager.ConfigManager;
import com.ericlam.manager.ReportManager;
import com.milkd.main.ReporterGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HandleInventory {
    private Inventory inventory;

    public HandleInventory(int id) {
        Inventory inventory = new InventoryBuilder(ConfigManager.handleGuiSize, ConfigManager.handleGuiTitle.replace("<id>", id + "")).build();
        for (HandleItem item : ConfigManager.getInstance().getHandleItems()) {
            ItemStack stack = new ItemStackBuilder(item.getMaterial()).displayName(item.getTitle()).lore(item.getLores()).onClick(e -> {
                Player player = (Player) e.getWhoClicked();
                Bukkit.getScheduler().runTaskAsynchronously(ReporterGUI.plugin, () -> {
                    try {
                        boolean success = ReportManager.getInstance().handleReport(id, item.getState());
                        player.sendMessage(success ? ConfigManager.handled : ConfigManager.handleFail);
                    } catch (ReportNotOpenException e1) {
                        player.sendMessage(e1.getMessage());
                    }
                });
            }).build();
            inventory.setItem(item.getSlot(), stack);
        }
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
