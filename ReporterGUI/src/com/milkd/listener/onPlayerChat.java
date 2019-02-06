package com.milkd.listener;

import com.ericlam.manager.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onPlayerChat implements Listener {

    @EventHandler
    public void chat( AsyncPlayerChatEvent event ) {
        String msg = event.getMessage();

        if (!ConfigManager.chatContains.contains(msg)) return;

        event.getPlayer().sendMessage(ConfigManager.reportHints);
    }

}
