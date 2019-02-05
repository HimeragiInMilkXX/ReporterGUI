package com.milkd.commands;

import com.ericlam.containers.HandleInventory;
import com.ericlam.containers.ReportInfo;
import com.ericlam.manager.ConfigManager;
import com.ericlam.manager.ReportManager;
import com.milkd.reporter.ReporterGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class AdminReportCommand implements CommandExecutor {

    private final ReportManager manager;
    private final Plugin plugin;

    public AdminReportCommand(ReporterGUI plugin) {
        manager = ReportManager.getInstance();
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigManager.notPlayer);
            return false;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(ConfigManager.helps);
            return false;
        }
        int id;
        String idStr = args[1];
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            player.sendMessage(ConfigManager.notValue);
            return false;
        }
        String method = args[0];

        switch (method) {
            case "info":
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    ReportInfo info = manager.getReportInfo(id);
                    if (info == null) {
                        player.sendMessage(ConfigManager.noThisReport.replace("<id>", idStr));
                        return;
                    }
                    player.sendMessage(Arrays.stream(ConfigManager.details).map(msg -> msg
                            .replace("<report-id>", id + "")
                            .replace("<reporter>", info.getReporter().getName())
                            .replace("<reported>", info.getReported().getName())
                            .replace("<reason>", info.getReason().toString())
                            .replace("<state>", info.getState().toString())).toArray(String[]::new));
                });
                return true;
            case "handle":
                player.openInventory(new HandleInventory(id).getInventory());
                return true;

        }
        return true;
    }
}
