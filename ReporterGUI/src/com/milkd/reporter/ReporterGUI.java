package com.milkd.reporter;

import com.milkd.reportfunctions.GUI;
import com.milkd.reportfunctions.ReportCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ReporterGUI extends JavaPlugin {

    public void onEnable() {

        Bukkit.getPluginManager().registerEvents( new GUI(), this );
        getCommand( "report" ).setExecutor( new ReportCommand() );

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "ReporterGUI enabled!" );

    }


}
