package com.milkd.reportfunctions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class GUIClickEvent implements Listener {

    @EventHandler
    public void onReportClick(InventoryClickEvent event) {

        GUI report = GUI.getInstance();
        ReportCommand reportCommand = ReportCommand.getInstance();
        Player player = (Player) event.getWhoClicked();
        ClickType clicktype = event.getClick();
        Inventory using = event.getClickedInventory();
        String[] target = report.getreportGUI().getName().split( "- " );

        if ( event.getSlotType() == InventoryType.SlotType.OUTSIDE )
            return;
        if ( using.getName().equals( report.getreportGUI().getName() ) ) {

            switch (event.getCurrentItem().getType()) {

                case DIAMOND_SWORD:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target[1] + ChatColor.GREEN + "!" );
                    event.setCancelled( true );
                    break;

                case SKELETON_SKULL:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target[1] + ChatColor.GREEN + "!" );
                    event.setCancelled( true );
                    break;

                default:
                    event.setCancelled( true );
                    break;

            } //End of switch

        } //End of getItem if

    }
}
