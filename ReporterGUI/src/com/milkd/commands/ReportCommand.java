package com.milkd.commands;

import com.ericlam.containers.ReportInventory;
import com.ericlam.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReportCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigManager.notPlayer);
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ConfigManager.requirePlayer);
            return false;
        }

        String reportPlayer = args[0];

        UUID reportUUID = Bukkit.getPlayerUniqueId(reportPlayer);
        if (reportUUID == null) {
            player.sendMessage(ConfigManager.noThisPlayer);
            return false;
        }

        OfflinePlayer reportedPlayer = Bukkit.getOfflinePlayer(reportUUID);

        player.openInventory(new ReportInventory(reportedPlayer).getInventory());
        return true;
    }

}
