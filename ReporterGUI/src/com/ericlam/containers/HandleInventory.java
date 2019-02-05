package com.ericlam.containers;

import com.caxerx.builders.InventoryBuilder;
import com.caxerx.builders.ItemStackBuilder;
import com.ericlam.enums.ReportState;
import com.ericlam.exceptions.ReportNotOpenException;
import com.ericlam.manager.ConfigManager;
import com.ericlam.manager.ReportManager;
import com.milkd.reporter.ReporterGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HandleInventory {
    private int id;
    private Inventory inventory;

    public HandleInventory(int id) {
        this.id = id;
        Inventory inventory = new InventoryBuilder(ConfigManager.handleGuiSize, ConfigManager.handleGuiTitle.replace("<id>", id + "")).build();
        for (ReportState state : ReportState.values()) {
            Material material;
            String string;
            int slot;
            switch (state) {
                case OPEN:
                    material = Material.BLACK_WOOL;
                    string = "§a設為開放審查";
                    slot = 0;
                    break;
                case DENIED:
                    material = Material.RED_WOOL;
                    string = "§a設為否定舉報";
                    slot = 2;
                    break;
                case EXPIRE:
                    material = Material.WHITE_WOOL;
                    string = "§f設為已過期";
                    slot = 4;
                    break;
                case PROVED:
                    material = Material.GREEN_WOOL;
                    string = "§a設為舉報屬實";
                    slot = 6;
                    break;
                case HANDLING:
                    material = Material.BLUE_WOOL;
                    string = "§b設為審查中";
                    slot = 8;
                default:
                    continue;
            }


            ItemStack stack = new ItemStackBuilder(material).displayName(string).lore("§e點擊以設置").onClick(e -> {
                Player player = (Player) e.getWhoClicked();
                Bukkit.getScheduler().runTaskAsynchronously(ReporterGUI.plugin, () -> {
                    try {
                        boolean success = ReportManager.getInstance().handleReport(id, state);
                        player.sendMessage(success ? ConfigManager.handled : ConfigManager.handleFail);
                    } catch (ReportNotOpenException e1) {
                        player.sendMessage(e1.getMessage());
                    }
                });
            }).build();
            inventory.setItem(slot, stack);
        }
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
