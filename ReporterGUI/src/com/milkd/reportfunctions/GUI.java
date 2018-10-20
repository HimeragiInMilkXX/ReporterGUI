package com.milkd.reportfunctions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUI {

    public String replacement( String msg ) {

        msg = msg.replaceAll("X", "▁");
        msg = msg.replaceAll("&", "§");

        return msg;

    }

    private static GUI gui;

    public static GUI getInstance() {

        if( gui == null )
            gui = new GUI();
        return gui;

    }

    private Inventory report;

    public void createGUI(Player player, String title ) {

        report = Bukkit.createInventory( null, 18, title );

        ItemStack illegal1 = new ItemStack( Material.DIAMOND_SWORD, 1 );
        ItemMeta illegal1meta = illegal1.getItemMeta();
        //ADD ENCHANTMENTS
        illegal1meta.addEnchant( Enchantment.KNOCKBACK, 2, true );
        //LORE SETTINGS
        List< String > illegal1lore = new ArrayList< String >();
        illegal1lore.add( "§c// §e檢舉該名玩家觸犯伺服器守則 §a第一條" );
        illegal1lore.add( "§e詳情請到伺服器官網查看伺服器守則。" );
        illegal1lore.add( "§e檢舉後請到伺服器論壇發佈實質證據。" );
        illegal1lore.add( "§b§o一旦檢舉成功,該名玩家將會根據罰則進行處罰。" );
        illegal1lore.add( replacement( "&r&cXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" ) );
        illegal1lore.add( "" );
        illegal1lore.add( "§9請確保你在檢舉前熟讀 伺服器玩家守則，" );
        illegal1lore.add( "§9若胡亂檢舉可能會被永久禁用檢舉功能。" );
        illegal1meta.setLore( illegal1lore );
        //ITEM SETTINGS
        illegal1meta.setDisplayName( replacement( "&c&o觸犯 「禁止」 之 第一條" ) );
        illegal1.setItemMeta( illegal1meta );
        //PLACEMENT
        report.setItem( 0, illegal1 );

        ItemStack illegal2347 = new ItemStack( Material.SKELETON_SKULL, 1 );
        ItemMeta illegal2347Meta = illegal2347.getItemMeta();
        //LORE SETTINGS
        List< String > illegal2347lore = new ArrayList< String >();
        illegal2347lore.add( "" );
        illegal2347lore.add( replacement( "&c// &e檢舉該名玩家觸犯伺服器守則 &a第二、三、四、七條" ) );
        illegal2347lore.add( replacement("&e詳情請到伺服器官網查看伺服器守則。" ) );
        illegal2347lore.add( replacement("&e檢舉後請到伺服器論壇發佈實質證據。" ) );
        illegal2347lore.add( replacement("&b&o一旦檢舉成功,該名玩家將會根據罰則進行處罰。" ) );
        illegal2347lore.add( replacement("&r&cXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" ) );
        illegal2347lore.add( "" );
        illegal2347lore.add( replacement("&9請確保你在檢舉前熟讀 伺服器玩家守則，" ) );
        illegal2347lore.add( replacement("&9若胡亂檢舉可能會被永久禁用檢舉功能。" ) );
        illegal2347Meta.setLore( illegal2347lore );
        //ITEM SETTINGS
        illegal2347Meta.setDisplayName( replacement( "&b&o觸犯 「禁止」 之 第二、三、四、七條" ) );
        illegal2347.setItemMeta( illegal2347Meta );
        //PLACEMENT
        report.setItem( 2, illegal2347 );

        ItemStack illegal5 = new ItemStack( Material.WHITE_BANNER, 1 );
        ItemMeta illegal5meta = illegal5.getItemMeta();
        //LORE SETTINGS
        List< String > illegal5lore = new ArrayList< String >();
        illegal5lore.add( "" );
        illegal5lore.add( replacement( "&c// &e檢舉該名玩家觸犯伺服器守則 &a第五條" ) );
        illegal5lore.add( replacement( "&e詳情請到伺服器官網查看伺服器守則。" ) );
        illegal5lore.add( replacement( "&e檢舉後請到伺服器論壇發佈實質證據。" ) );
        illegal5lore.add( replacement( "&b&o一旦檢舉成功,該名玩家將會根據罰則進行處罰。" ) );
        illegal5lore.add( replacement( "&r&cXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" ) );
        illegal5lore.add( "" );
        illegal5lore.add( replacement( "&9請確保你在檢舉前熟讀 伺服器玩家守則，" ) );
        illegal5lore.add( replacement( "&9若胡亂檢舉可能會被永久禁用檢舉功能。" ) );
        illegal5meta.setLore( illegal5lore );
        //ITEM SETTINGS
        illegal5meta.setDisplayName( replacement( "&b&o觸犯 「禁止」 之 第五條" ) );
        illegal5.setItemMeta( illegal5meta );
        //PLACEMENT
        report.setItem( 4, illegal5 );

        ItemStack illegal6 = new ItemStack( Material.TOTEM_OF_UNDYING, 1 );
        ItemMeta illegal6meta = illegal6.getItemMeta();
        //LORE SETTINGS
        List< String > illegal6lore = new ArrayList< String >();
        illegal6lore.add( "" );
        illegal6lore.add( replacement( "&c// &e檢舉該名玩家觸犯伺服器守則 &a牌位戰之第一條" ) );
        illegal6lore.add( replacement( "&e詳情請到伺服器官網查看伺服器守則。" ) );
        illegal6lore.add( replacement( "&e檢舉後請到伺服器論壇發佈實質證據。" ) );
        illegal6lore.add( replacement( "&b&o一旦檢舉成功,該名玩家將會根據罰則進行處罰。" ) );
        illegal6lore.add( replacement( "&r&cXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" ) );
        illegal6lore.add( "" );
        illegal6lore.add( replacement( "'&9請確保你在檢舉前熟讀 伺服器玩家守則，" ) );
        illegal6lore.add( replacement( "&9若胡亂檢舉可能會被永久禁用檢舉功能。" ) );
        illegal6meta.setLore( illegal6lore );
        //ITEM SETTINGS
        illegal6meta.setDisplayName( replacement( "&b&o觸犯 「牌位戰」 之 第一條" ) );
        illegal6.setItemMeta( illegal6meta );
        //PLACEMENT
        report.setItem( 6, illegal6 );

        ItemStack illegal8 = new ItemStack( Material.NAME_TAG, 1 );
        ItemMeta illegal8meta = illegal8.getItemMeta();
        //LORE SETTINGS
        List< String > illegal8lore = new ArrayList< String >();
        illegal8lore.add( "" );
        illegal8lore.add( replacement( "&c// &e檢舉該名玩家觸犯伺服器守則 &a牌位戰之第一條" ) );
        illegal8lore.add( replacement( "&e詳情請到伺服器官網查看伺服器守則。" ) );
        illegal8lore.add( replacement( "&e檢舉後請到伺服器論壇發佈實質證據。" ) );
        illegal8lore.add( replacement( "&b&o一旦檢舉成功,該名玩家將會根據罰則進行處罰。" ) );
        illegal8lore.add( replacement( "&r&cXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" ) );
        illegal8lore.add( "" );
        illegal8lore.add( replacement( "&9請確保你在檢舉前熟讀 伺服器玩家守則，" ) );
        illegal8lore.add( replacement( "&9若胡亂檢舉可能會被永久禁用檢舉功能。" ) );
        illegal8meta.setLore( illegal8lore );
        //ITEM SETTINGS
        illegal8meta.setDisplayName( replacement( "&b&o觸犯 「牌位戰」 之 第一條" ) );
        illegal8.setItemMeta( illegal8meta );
        //PLACEMENT
        report.setItem( 8, illegal8 );

        player.openInventory( report );

    }

    public Inventory getreportGUI() {

        return report;

    }

}
