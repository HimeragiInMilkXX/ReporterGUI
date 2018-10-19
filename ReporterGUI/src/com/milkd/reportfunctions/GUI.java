package com.milkd.reportfunctions;

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

import java.util.ArrayList;
import java.util.List;

public class GUI extends ReportCommand implements Listener {

    public String replacement( String msg ) {

        msg = msg.replaceAll("(X)", "▁");
        msg = msg.replaceAll("&", "§");

        return msg;

    }

    public Inventory report;

    public void createGUI(Player player, String title ) {

        report = Bukkit.createInventory( null, 18, title );

        ItemStack illegal1 = new ItemStack( Material.DIAMOND_SWORD, 0 );
        ItemMeta illegal1meta = illegal1.getItemMeta();
        //ADD ENCHANTMENTS
        illegal1meta.addEnchant( Enchantment.KNOCKBACK, 2, true );
        //LORE SETTINGS
        List< String > illegal1lore = new ArrayList< String >();
        illegal1lore.add( "§c// §e檢舉該名玩家觸犯伺服器守則 §a第一條" );
        illegal1lore.add( "§e詳情請到伺服器官網查看伺服器守則。" );
        illegal1lore.add( "§e檢舉後請到伺服器論壇發佈實質證據。" );
        illegal1lore.add( "§b§o一旦檢舉成功,該名玩家將會根據罰則進行處罰。" );
        illegal1lore.add( replacement( "&r&c(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)" ) );
        illegal1lore.add( "" );
        illegal1lore.add( "§9請確保你在檢舉前熟讀 伺服器玩家守則，" );
        illegal1lore.add( "§9若胡亂檢舉可能會被永久禁用檢舉功能。" );
        illegal1meta.setLore( illegal1lore );
        //ITEM SETTINGS
        illegal1meta.setDisplayName( replacement( "&c&o觸犯 「禁止」 之 第一條" ) );
        illegal1.setItemMeta( illegal1meta );
        //PLACEMENT
        report.setItem( 0, illegal1 );

        ItemStack illegal2347 = new ItemStack( Material.SKELETON_SKULL, 0 );
        ItemMeta illegal2347Meta = illegal2347.getItemMeta();
        //LORE SETTINGS
        List< String > illegal2347lore = new ArrayList< String >();
        illegal2347lore.add( "" );
        illegal2347lore.add( replacement( "&c// &e檢舉該名玩家觸犯伺服器守則 &a第二、三、四、七條" ) );
        illegal2347lore.add( replacement("&e詳情請到伺服器官網查看伺服器守則。" ) );
        illegal2347lore.add( replacement("&e檢舉後請到伺服器論壇發佈實質證據。" ) );
        illegal2347lore.add( replacement("&b&o一旦檢舉成功,該名玩家將會根據罰則進行處罰。" ) );
        illegal2347lore.add( replacement("&r&c(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)(X)" ) );
        illegal2347lore.add( "" );
        illegal2347lore.add( replacement("&9請確保你在檢舉前熟讀 伺服器玩家守則，" ) );
        illegal2347lore.add( replacement("&9若胡亂檢舉可能會被永久禁用檢舉功能。" ) );
        illegal2347Meta.setLore( illegal2347lore );
        //ITEM SETTINGS
        illegal2347Meta.setDisplayName( replacement( "&b&o觸犯 「禁止」 之 第二、三、四、七條" ) );
        illegal2347.setItemMeta( illegal2347Meta );
        //PLACEMENT
        report.setItem( 2, illegal2347 );


    }

    @EventHandler
    public void onReportClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ClickType clicktype = event.getClick();

        if ( event.getClickedInventory().equals( report ) ) {

            switch (event.getCurrentItem().getType()) {

                case DIAMOND_SWORD:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target + ChatColor.GREEN + "!" );
                    break;

                case SKELETON_SKULL:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + target + ChatColor.GREEN + "!" );
                    break;

            } //End of switch

        } //End of getItem if

    }

}
