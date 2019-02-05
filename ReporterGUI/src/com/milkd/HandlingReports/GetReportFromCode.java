package com.milkd.HandlingReports;

import com.ericlam.state.ReportState;
import com.milkd.reporter.ReporterGUI;
import mysql.hypernite.mc.SQLDataSourceManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetReportFromCode implements CommandExecutor {

    public String translateColor( String msg ) {

        msg = msg.replaceAll("&", "§");

        return msg;

    }

    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args ) {

        if( sender instanceof Player ) {

            Player player = ( Player ) sender;

            if( args.length == 2 ) {

                if( args[0].equals( "info" ) ) {

                    try {

                        String ID = args[1];

                        //DEBUG
                        Integer.parseInt( ID );

                        PreparedStatement ps = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement( "SELECT * FROM " + ReporterGUI.table + " WHERE ReportID = ?" );
                        ps.setString( 1, ID );

                        ResultSet report = ps.executeQuery();
                        String reporter = report.getString( "`ReporterName`" );
                        String target = report.getString( "`ReportedName`" );
                        String reason = report.getString( "`Reason`" );
                        String state = report.getString( "`State`" );
                        int reportid = report.getInt( "ReportID" );

                        player.sendMessage( translateColor( "&eReportId: " + reportid ) +
                                                translateColor("\n&aReporter: " + reporter ) +
                                                translateColor( "\n&aReported: " + target ) +
                                                translateColor( "\nReason: " + reason ) +
                                                translateColor( "\nState: &c&l" + state ) );

                    } catch( NumberFormatException e ) {

                        player.sendMessage( translateColor( "&4Please enter an Integer!" ) );

                    } catch( SQLException e ) {

                        e.printStackTrace();

                    }

                } else if( args[0].equals( "handle" ) ) {

                    try {

                        String ID = args[1];

                        //DEBUG
                        Integer.parseInt( ID );

                        String current = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement( "SELECT `State` FROM " + ReporterGUI.table + " WHERE `ReportID` = " + ID ).executeQuery().getString(  "`State`" );

                        if( current == ReportState.OPEN.toString() ) {

                            PreparedStatement ps = SQLDataSourceManager.getInstance().getFuckingConnection().prepareStatement("UPDATE " + ReporterGUI.table + " SET `State` = ? WHERE `ReportID` = ?");
                            ps.setString(1, ReportState.HANDLING.toString());
                            ps.setString(2, ID);
                            ps.executeUpdate();

                        } else {

                            player.sendMessage( translateColor( "&c&lReport " + ID + "&eis " + "&b&l" + current ) );

                        }

                        player.sendMessage( translateColor( "&eReport " + ID + "&ahave been handled!" ) );


                    } catch( NumberFormatException e ) {

                        player.sendMessage( translateColor( "&4Please enter an Integer!" ) );

                    } catch ( SQLException e ) {

                        e.printStackTrace();

                    }

                }

            } else {

                player.sendMessage( translateColor( "&c&m------------------------------------" +
                                    translateColor( "\n&7&l/reports info <ID> &e&m- &r&aGet the of Report <id>" +
                                    translateColor( "\n&7&l/reports handle <ID> &e&m- &r&aHandle the of Report <id>" +
                                    translateColor( "\n&c&m------------------------------------" ) ) ) ) );

            }

        } else {

            sender.sendMessage( translateColor( "&4You must be a player to use this command" ) );

        }

        return false;

    }

}
