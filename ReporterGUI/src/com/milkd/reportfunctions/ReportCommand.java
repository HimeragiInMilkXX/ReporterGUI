package com.milkd.reportfunctions;

import com.milkd.reporter.ReporterGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class ReportCommand implements Listener, CommandExecutor {

    ReporterGUI reportgui = new ReporterGUI();

    String target;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 0) {

                player.sendMessage(ChatColor.RED + "Unknown arguments! /report <player>");

            } else if (args.length == 1) {

                target = args[0];

                if ( Bukkit.getPlayer(args[0]).isOnline() )
                    reportgui.createGUI(player, (ChatColor.DARK_RED + "檢舉玩家 " + args[0]));
                else if ( Bukkit.getPlayer(args[0]).isOnline() == false ) {
                    reportgui.createGUI(player, (ChatColor.DARK_RED + "檢舉玩家 " + args[0]));
                    player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "WARNING!" + ChatColor.RED + args[0] + " is not online!");

                }

            }

        } //End of instanceof if

        return false;

    }

    @EventHandler
    public void onReportClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ClickType clicktype = event.getClick();

        if (event.getClickedInventory().equals(reportgui.report)) {

            switch (event.getCurrentItem().getType()) {

                case DIAMOND_SWORD:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target + ChatColor.GREEN + "!" );
                    break;

            } //End of switch

        } //End of getItem if

    }

}
