package com.milkd.reportfunctions;

import com.milkd.reporter.ReporterGUI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.Timestamp;

public class ReportCommand implements CommandExecutor {

    private ReporterGUI plugin = ReporterGUI.getPlugin(ReporterGUI.class);

    Player player;
    OfflinePlayer target;
    Timestamp timestamp;

    private static ReportCommand reportcmd;

    public static ReportCommand getInstance() {

        if (reportcmd == null)
            reportcmd = new ReportCommand();

        return reportcmd;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        timestamp = new Timestamp( System.currentTimeMillis() );

        if (sender instanceof Player) {

            if(args.length == 1 ) {

                target = plugin.getServer().getPlayer(args[0]);

                player = (Player) sender;

                Inventory report = GUI.getInstance().getReporter();
                player.openInventory(report);

            } else {

                player.sendMessage( ChatColor.RED + "Please enter the player that you want to report after /reprot!" );

            }

        } else {

            sender.sendMessage(ChatColor.RED + "You must be a player to issue the command" );

        }

        return false;

    }

    public Player getReporter() {

        return this.player;

    }

    public OfflinePlayer getReported() {

        return this.target;

    }

    public Timestamp gettime() {

        return timestamp;

    }

}
