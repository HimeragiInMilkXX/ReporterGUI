package com.ericlam.containers;

import com.caxerx.builders.InventoryBuilder;
import com.caxerx.builders.ItemStackBuilder;
import com.ericlam.manager.ConfigManager;
import com.ericlam.manager.ReportManager;
import com.milkd.main.ReportSystem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class ReportInventory {
    private Inventory inventory;

    public ReportInventory(OfflinePlayer reportedPlayer) {
        UUID reportedUUID = reportedPlayer.getUniqueId();
        Inventory inventory = new InventoryBuilder(ConfigManager.reportGuiSize, ConfigManager.reportGuiTitle.replace("<player>", reportedPlayer.getName())).build();
        for (ReportItem item : ConfigManager.getInstance().getReportItems()) {
            ItemStack stack = new ItemStackBuilder(item.getMaterial()).displayName(item.getTitle()).lore(item.getLores()).onClick(e -> {
                Player player = (Player) e.getWhoClicked();
                Timestamp time = Timestamp.from(Instant.now());
                if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
                e.setCancelled(true);
                Bukkit.getScheduler().runTaskAsynchronously(ReportSystem.plugin, () -> {
                    ReportManager.getInstance().addReport(player.getName(), reportedPlayer.getName(), player.getUniqueId(), reportedUUID, item.getReason(), time);
                    player.sendMessage(ConfigManager.reported.replace("<player>", reportedPlayer.getName()).replace("<reason>", item.getTitle().replace("&", "§")));
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
