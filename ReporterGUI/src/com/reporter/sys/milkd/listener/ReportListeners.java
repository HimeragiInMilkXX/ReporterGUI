package com.reporter.sys.milkd.listener;

import com.reporter.sys.ericlam.manager.ConfigManager;
import com.reporter.sys.ericlam.manager.ReportManager;
import com.reporter.sys.milkd.main.ReportSystem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ReportListeners implements Listener {

    @EventHandler
    public void chat( AsyncPlayerChatEvent event ) {
        String msg = event.getMessage();

        if (!ConfigManager.chatContains.contains(msg)) return;

        event.getPlayer().sendMessage(ConfigManager.reportHints);
    }

    @EventHandler
    public void giveback(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(ReportSystem.plugin, () -> ReportManager.getInstance().getThanksGiving(e.getPlayer()));
    }

}
