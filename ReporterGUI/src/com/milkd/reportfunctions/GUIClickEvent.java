package com.milkd.reportfunctions;

import com.milkd.reporter.ReporterGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GUIClickEvent implements Listener {

    Player reportone;
    String[] target;

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
        ReportCommand reportCommand = ReportCommand.getInstance();
        Player player = (Player) event.getWhoClicked();
        reportone = player;
        ClickType clicktype = event.getClick();
        Inventory using = event.getClickedInventory();
        target = report.getreportGUI().getName().split( "- " );

        if ( event.getSlotType() == InventoryType.SlotType.OUTSIDE )
            return;
        if ( using.getName().equals( report.getreportGUI().getName() ) ) {

            switch (event.getCurrentItem().getType()) {

                case DIAMOND_SWORD:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target[1] + ChatColor.GREEN + "!" );

                    try {

                        PreparedStatement ps1 = plugin.getConnection().prepareStatement(
                                "INSERT INTO " + plugin.table + " ( CODE, Reporter, Reported, Reason, Time) VALUE (?,?,?,?)");
                        plugin.setCODE( ps1 );
                        ps1.setString( 2, reportone.getName() );
                        ps1.setString( 3, target[1] );
                        ps1.setString( 4, "Broke 'blocked' law number 1" );
                        ps1.setLong( 5, plugin.timestamp() );
                        ps1.executeUpdate();

                        System.out.print( "INSERTED" ); //testing

                    } catch( SQLException e ) {

                        e.printStackTrace();

                    } //End of try/catch block

                    player.closeInventory();
                    event.setCancelled( true );
                    break;

                case SKELETON_SKULL:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target[1] + ChatColor.GREEN + "!" );

                    try {

                        PreparedStatement ps2 = plugin.getConnection().prepareStatement(
                                "INSERT INTO " + plugin.table + " ( CODE, Reporter, Reported, Reason, Time) VALUE (?,?,?,?)");
                        plugin.setCODE( ps2 );
                        ps2.setString( 2, reportone.getName() );
                        ps2.setString( 3, target[1] );
                        ps2.setString( 4, "Broke 'blocked' law number 2-3-4-7" );
                        ps2.setLong( 5, plugin.timestamp() );
                        ps2.executeUpdate();

                        System.out.print( "INSERTED" ); //testing

                    } catch( SQLException e ) {

                        e.printStackTrace();

                    } //End of try/catch block

                    player.closeInventory();
                    event.setCancelled( true );
                    break;
                case WHITE_BANNER:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target[1] + ChatColor.GREEN + "!" );

                    try {

                        PreparedStatement ps3 = plugin.getConnection().prepareStatement(
                                "INSERT INTO " + plugin.table + " ( CODE, Reporter, Reported, Reason, Time) VALUE (?,?,?,?)");
                        plugin.setCODE( ps3 );
                        ps3.setString( 2, reportone.getName() );
                        ps3.setString( 3, target[1] );
                        ps3.setString( 4, "Broke 'blocked' law number 5" );
                        ps3.setLong( 5, plugin.timestamp() );
                        ps3.executeUpdate();

                        System.out.print( "INSERTED" ); //testing

                    } catch( SQLException e ) {

                        e.printStackTrace();

                    } //End of try/catch block

                    player.closeInventory();
                    event.setCancelled( true );
                    break;
                case TOTEM_OF_UNDYING:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target[1] + ChatColor.GREEN + "!" );

                    try {

                        PreparedStatement ps4 = plugin.getConnection().prepareStatement(
                                "INSERT INTO " + plugin.table + " ( CODE, Reporter, Reported, Reason, Time) VALUE (?,?,?,?)");
                        plugin.setCODE( ps4 );
                        ps4.setString( 2, reportone.getName() );
                        ps4.setString( 3, target[1] );
                        ps4.setString( 4, "Broke 'blocked' law number 6" );
                        ps4.setLong( 5, plugin.timestamp() );
                        ps4.executeUpdate();

                        System.out.print( "INSERTED" ); //testing

                    } catch( SQLException e ) {

                        e.printStackTrace();

                    } //End of try/catch block

                    player.closeInventory();
                    event.setCancelled( true );
                    break;
                case NAME_TAG:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target[1] + ChatColor.GREEN + "!" );

                    try {

                        PreparedStatement ps5 = plugin.getConnection().prepareStatement(
                                "INSERT INTO " + plugin.table + " ( CODE, Reporter, Reported, Reason, Time) VALUE (?,?,?,?)");
                        plugin.setCODE( ps5 );
                        ps5.setString( 2, reportone.getName() );
                        ps5.setString( 3, target[1] );
                        ps5.setString( 4, "Broke 'ranking match' law number 1" );
                        ps5.setLong( 5, plugin.timestamp() );
                        ps5.executeUpdate();

                        System.out.print( "INSERTED" ); //testing

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
