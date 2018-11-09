package com.milkd.reportfunctions;

import com.milkd.reporter.ReporterGUI;
import com.mysql.fabric.xmlrpc.base.Data;
import mysql.hypernite.mc.SQLDataSourceManager;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

public class GUIClickEvent implements Listener {

    private Player reportone;
    private String[] target;

    ReporterGUI plugin = ReporterGUI.getPlugin( ReporterGUI.class );

    public String gettarget() {

        return target[2];

    }

    public Player getReportone() {

        return reportone;

    }

    private static GUIClickEvent guievent;

    public static GUIClickEvent getInstance() {

        if( guievent == null )
            guievent = new GUIClickEvent();

            return guievent;

    }

    @EventHandler
    public void onReportClick(InventoryClickEvent event) {

        GUI report = GUI.getInstance();
        Player player = (Player) event.getWhoClicked();
        if (report.reporting(player) == null) return;
        OfflinePlayer target = report.reporting(player);
        //reportone = player;
        //ClickType clicktype = event.getClick();
        Inventory using = event.getClickedInventory();
        //target = report.getreportGUI().getName().split( "- " );
        if ( event.getSlotType() == InventoryType.SlotType.OUTSIDE )
            return;
        if ( using.getName().equals( report.getTargetInventroy(target).getName()) ) {

            switch (event.getCurrentItem().getType()) {

                case DIAMOND_SWORD:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target.getName() + ChatColor.GREEN + "!" );

                    try {

                        PreparedStatement ps1 = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement(
                                "INSERT INTO " + plugin.table + " ( CODE, Reporter, Reported ) VALUE (?,?,?)");
                        plugin.setCODE( ps1 );
                        ps1.setString( 2, reportone.getName() );
                        ps1.setString( 3, target.getUniqueId().toString() );
                        ps1.setString( 4, "1_"+ Date.from(Instant.now()));
                        ps1.setLong( 5, plugin.timestamp() );
                        ps1.executeUpdate();

                    } catch( SQLException e ) {

                        e.printStackTrace();

                    } //End of try/catch block

                    player.closeInventory();
                    event.setCancelled( true );
                    break;

                case SKELETON_SKULL:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target.getName() + ChatColor.GREEN + "!" );

                    try {

                        PreparedStatement ps2 = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement(
                                "INSERT INTO " + plugin.table + " ( CODE, Reporter, Reported ) VALUE (?,?,?)");
                        plugin.setCODE( ps2 );
                        ps2.setString( 2, reportone.getName() );
                        ps2.setString( 3, target.getUniqueId().toString() );
                        ps2.setString( 4, "2347_"+ Date.from(Instant.now()));
                        ps2.setLong( 5, plugin.timestamp() );
                        ps2.executeUpdate();

                    } catch( SQLException e ) {

                        e.printStackTrace();

                    } //End of try/catch block

                    player.closeInventory();
                    event.setCancelled( true );
                    break;
                case WHITE_BANNER:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target.getName() + ChatColor.GREEN + "!" );

                    try {

                        PreparedStatement ps3 = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement(
                                "INSERT INTO " + plugin.table + " ( CODE, Reporter, Reported ) VALUE (?,?,?)");
                        plugin.setCODE( ps3 );
                        ps3.setString( 2, reportone.getName() );
                        ps3.setString( 3, target.getUniqueId().toString() );
                        ps3.setString( 4, "5_"+ Date.from(Instant.now()));
                        ps3.setLong( 5, plugin.timestamp() );
                        ps3.executeUpdate();

                    } catch( SQLException e ) {

                        e.printStackTrace();

                    } //End of try/catch block

                    player.closeInventory();
                    event.setCancelled( true );
                    break;
                case TOTEM_OF_UNDYING:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target.getName() + ChatColor.GREEN + "!" );

                    try {

                        PreparedStatement ps4 = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement(
                                "INSERT INTO " + plugin.table + " ( CODE, Reporter, Reported ) VALUE (?,?,?)");
                        plugin.setCODE( ps4 );
                        ps4.setString( 2, reportone.getName() );
                        ps4.setString( 3, target.getUniqueId().toString() );
                        ps4.setString( 4, "6_"+ Date.from(Instant.now()));
                        ps4.setLong( 5, plugin.timestamp() );
                        ps4.executeUpdate();

                    } catch( SQLException e ) {

                        e.printStackTrace();

                    } //End of try/catch block

                    player.closeInventory();
                    event.setCancelled( true );
                    break;
                case NAME_TAG:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target.getName() + ChatColor.GREEN + "!" );

                    try {

                        PreparedStatement ps5 = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement(
                                "INSERT INTO " + plugin.table + " ( CODE, Reporter, Reported ) VALUE (?,?,?)");
                        plugin.setCODE( ps5 );
                        ps5.setString( 2, reportone.getName() );
                        ps5.setString( 3, target.getUniqueId().toString() );
                        ps5.setString( 4, "8_"+ Date.from(Instant.now()));
                        ps5.setLong( 5, plugin.timestamp() );
                        ps5.executeUpdate();

                    } catch( SQLException e ) {

                        e.printStackTrace();

                    } //End of try/catch block

                    player.closeInventory();
                    event.setCancelled( true );
                    break;

                default:
                    event.setCancelled( true );
                    break;

            } //End of switch

        } //End of getItem if

    }

}
