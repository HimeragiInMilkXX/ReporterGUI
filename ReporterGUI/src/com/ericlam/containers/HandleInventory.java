package com.ericlam.containers;

import com.caxerx.builders.InventoryBuilder;
import com.caxerx.builders.ItemStackBuilder;
import com.ericlam.exceptions.ReportNonExistException;
import com.ericlam.exceptions.ReportNotOpenException;
import com.ericlam.manager.ConfigManager;
import com.ericlam.manager.ReportManager;
import com.milkd.main.ReportSystem;
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
                e.setCancelled(true);
                Bukkit.getScheduler().runTaskAsynchronously(ReportSystem.plugin, () -> {
                    try {
                        boolean success = ReportManager.getInstance().handleReport(id, item.getState(), player);
                        player.sendMessage(success ? ConfigManager.handled.replace("<id>", id + "").replace("<state>", item.getState().getTitle()) : ConfigManager.handleFail);
                    } catch (ReportNotOpenException | ReportNonExistException e1) {
                        player.sendMessage(e1.getMessage());
                    }
                });
                player.closeInventory();
            }).build();
            inventory.setItem(item.getSlot(), stack);
        }
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
