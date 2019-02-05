package com.milkd.reportfunctions;

import com.milkd.reporter.ReporterGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUI {

    private ReporterGUI plugin = ReporterGUI.getPlugin( ReporterGUI.class );

    private FileConfiguration config = plugin.getConfig();

    public String translateColor( String msg ) {

        msg = msg.replaceAll("&", "§");

        return msg;

    }

    private static GUI gui;

    public static GUI getInstance() {

        if( gui == null )
            gui = new GUI();
        return gui;

    }

    Inventory report;

    public void createGUI() {

        report = Bukkit.createInventory( null, config.getInt( "GUI.Size" ), translateColor( config.getString( "GUI.Title" ) ) );

        for( String item : config.getConfigurationSection( "GUI.Items" ).getKeys( false ) ) {

            Material material = Material.getMaterial( config.getString( "GUI.Items." + item + ".item" ) );
            String name = translateColor( config.getString( "GUI.Items."  + item + ".Name" ) );
            List< String > lores = config.getStringList( "GUI.Items." + item + ".Lores" );
            for( int i = 0; i < lores.size(); i++ ) {

                lores.set( i ,translateColor( lores.get( i ) ) );

            }
            int slot = config.getInt( "GUI.Items." + item + "Slot" );

            ItemStack ItemS = new ItemStack( material );
            ItemMeta ItemM = ItemS.getItemMeta();
            //------------ItemSettings------------
            ItemM.setDisplayName( name );
            ItemM.setLore( lores );
            ItemS.setItemMeta( ItemM );
            //------------------------------------
            report.setItem( slot, ItemS );

        }

    }

    public Inventory getReporter() {

        return report;

    }

}
