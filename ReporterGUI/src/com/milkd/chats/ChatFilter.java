package com.milkd.chats;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFilter implements Listener {

    @EventHandler
    public void chat( AsyncPlayerChatEvent event ) {

        String msg = event.getMessage();

        if( msg.contains( "hax" ) | msg.contains( "hack" ) | msg.contains( "外掛" ) ) {

            event.getPlayer().sendMessage( "§7使用§e /report §7來檢舉不當玩家。");
            event.setCancelled( false );

        } else {

            return;

        }

    }

}
