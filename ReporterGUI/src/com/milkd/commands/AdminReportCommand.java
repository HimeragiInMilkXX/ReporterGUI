package com.milkd.commands;

import com.ericlam.containers.HandleInventory;
import com.ericlam.containers.ReportInfo;
import com.ericlam.enums.ReportState;
import com.ericlam.exceptions.NoAvailableReportException;
import com.ericlam.exceptions.ReportNonExistException;
import com.ericlam.manager.ConfigManager;
import com.ericlam.manager.ReportManager;
import com.milkd.main.ReportSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;

public class AdminReportCommand implements CommandExecutor {

    private final ReportManager manager;
    private final Plugin plugin;

    public AdminReportCommand(ReportSystem plugin) {
        manager = ReportManager.getInstance();
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigManager.notPlayer);
            return false;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ConfigManager.helps);
            return false;
        }

        String method = args[0].toLowerCase();

        if (args.length == 1) {
            switch (method) {
                case "available":
                    try {
                        HashMap<Integer, ReportState> lists = manager.getAvaliableReports();
                        for (Integer integer : lists.keySet()) {
                            player.sendMessage(ConfigManager.prefix + "§f#" + integer + " §7- " + lists.get(integer).getTitle());
                        }
                        player.sendMessage(ConfigManager.prefix + "§7若舉報ID過多，請自行到舉報管理頁面查看。");
                    } catch (NoAvailableReportException e) {
                        player.sendMessage(ConfigManager.noAvaReports);
                        return false;
                    }
                    return true;
                default:
                    player.sendMessage(ConfigManager.helps);
                    return false;
            }
        }
        int id;
        String idStr = args[1];
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            player.sendMessage(ConfigManager.notValue);
            return false;
        }

        switch (method) {
            case "info":
                if (!sender.hasPermission("report.helper")) {
                    sender.sendMessage(ConfigManager.noPerm);
                    return false;
                }
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    try {
                        ReportInfo info = manager.getReportInfo(id);

                        if (info == null) {//sql error
                            player.sendMessage(ChatColor.RED + "SQL error, try again later.");
                            return;
                        }

                        player.sendMessage(Arrays.stream(ConfigManager.details).map(msg -> msg
                                .replace("<report-id>", id + "")
                                .replace("<main>", info.getReporter().getName())
                                .replace("<reported>", info.getReported().getName())
                                .replace("<reason>", info.getReason().toString())
                                .replace("<state>", info.getState().getTitle())).toArray(String[]::new));
                    } catch (ReportNonExistException e) {
                        player.sendMessage(e.getMessage()); //non exist
                    }
                });
                return true;
            case "handle":
                if (!sender.hasPermission("report.mod")) {
                    sender.sendMessage(ConfigManager.noPerm);
                    return false;
                }
                player.openInventory(new HandleInventory(id).getInventory());
                return true;
            default:
                player.sendMessage(ConfigManager.helps);
                break;
        }

        return true;
    }
}
