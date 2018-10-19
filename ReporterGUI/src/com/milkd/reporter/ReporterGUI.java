package com.milkd.reporter;

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

public class ReporterGUI extends JavaPlugin implements Listener {

    public Inventory report;

    ReportCommand reportcommand = new ReportCommand();

    public String replacement( String msg ) {

        msg = msg.replaceAll( "(X)", "▁" );
        msg = msg.replaceAll( "&", "§" );

        return msg;

    }

    public void onEnable() {

        getCommand( "report" ).setExecutor( new ReportCommand() );

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "ReporterGUI enabled!" );

    }

    public void createGUI( Player player, String title ) {

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

    }

    @EventHandler
    public void onReportClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ClickType clicktype = event.getClick();

        if ( event.getClickedInventory().equals( report ) ) {

            switch (event.getCurrentItem().getType()) {

                case DIAMOND_SWORD:
                    player.sendMessage( ChatColor.GREEN + "You have reported " + ChatColor.BLUE + reportcommand.target + ChatColor.GREEN + "!" );
                    break;

            } //End of switch

        } //End of getItem if

    }

}
