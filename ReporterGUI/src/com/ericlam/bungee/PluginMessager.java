package com.ericlam.bungee;

import com.ericlam.enums.ReasonType;
import com.ericlam.enums.ReportState;
import com.ericlam.manager.ConfigManager;
import com.google.common.io.ByteArrayDataOutput;
import com.milkd.main.ReportSystem;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import static com.google.common.io.ByteStreams.newDataOutput;

public class PluginMessager {
    public static void broadcastReport(String reporter, String reported, ReasonType reason, Player player) {
        String server = ConfigManager.server;
        String msg = server + "_" + reporter + "_" + reported + "_" + reason.getTitle();
        sendMessager(player, msg);
    }

    public static void uploadHandle(String reported, UUID reportedUUID, int reportID, ReportState afterState, ReportState beforeState, Player player) {
        String msg = reported + "_" + reportedUUID.toString() + "_" + reportID + "_" + beforeState.toString() + "_" + afterState.toString() + "_" + player.getName();
        sendMessager(player, msg);
    }

    private static void sendMessager(Player player, String msg) {
        ByteArrayDataOutput out = newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ONLINE");
        out.writeUTF("ReporterSystem");
        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            msgout.writeUTF(msg);
            msgout.writeShort(msg.length());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());
        player.sendPluginMessage(ReportSystem.plugin, "BungeeCord", out.toByteArray());
    }
}
