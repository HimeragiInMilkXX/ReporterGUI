package com.milkd.reportfunctions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class ReportCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (!(sender instanceof Player)) { //End of instanceof if
            sender.sendMessage("not player");
            return false;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Unknown arguments! /report <player>");
            return false;
        }

        UUID targetUUID = Bukkit.getPlayerUniqueId(args[0]);
        if (targetUUID == null) {
            player.sendMessage("Not found this player");
            return false;
        }

        GUI reportgui = GUI.getInstance();
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetUUID);

        player.openInventory(reportgui.makeReportGUI(target));
        reportgui.addReport(player,target);
        return false;

    }

}
