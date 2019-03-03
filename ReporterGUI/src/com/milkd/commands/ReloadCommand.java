package com.milkd.commands;

import com.ericlam.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("report.admin")) {
            commandSender.sendMessage(ConfigManager.noPerm);
            return false;
        }

        ConfigManager.getInstance().reloadConfig();
        commandSender.sendMessage(ConfigManager.prefix + ChatColor.GREEN + "Reload Successfully");
        return true;
    }
}
