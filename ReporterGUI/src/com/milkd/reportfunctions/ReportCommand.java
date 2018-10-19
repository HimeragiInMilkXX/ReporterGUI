package com.milkd.reportfunctions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class ReportCommand implements CommandExecutor {

    GUI reportgui = new GUI();

    public String target;

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

            } //else if

        } //End of instanceof if

        return false;

    }

}
