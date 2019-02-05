package com.milkd.reportfunctions;

import com.ericlam.manager.ReportManager;
import com.milkd.reporter.ReporterGUI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Timestamp;
import java.util.UUID;

public class GUIClickEvent implements Listener {

    private ReporterGUI plugin = ReporterGUI.getPlugin( ReporterGUI.class );

    Player player = ReportCommand.getInstance().getReporter();
    OfflinePlayer target = ReportCommand.getInstance().getReported();

    //Players information
    UUID Puuid = player.getUniqueId();
    UUID Tuuid = target.getUniqueId();
    String Pname = player.getName();
    String Tname = target.getName();

    //Report Information
    String Reason;
    Timestamp time = ReportCommand.getInstance().gettime();

    private static GUIClickEvent guievent;

    public static GUIClickEvent getInstance() {

        if( guievent == null )
            guievent = new GUIClickEvent();

            return guievent;

    }

    @EventHandler
    public void onReport(InventoryClickEvent e) {

        Player player = ( Player ) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        ItemMeta itemM = item.getItemMeta();
        Reason = itemM.getDisplayName();

        if( e.getSlotType() == InventoryType.SlotType.OUTSIDE )
            return;

        ReportManager.getInstance().addReport( Pname, Tname, Puuid, Tuuid, Reason, time, player );
        e.setCancelled( true );

    }

}
