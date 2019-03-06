package com.reporter.sys.ericlam.manager;

import com.hypernite.mysql.SQLDataSourceManager;
import com.reporter.sys.ericlam.containers.ProvedReport;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class ProvedCountManager {
    private static ProvedCountManager provedCountManager;
    private HashMap<UUID, ProvedReport> reportsProved = new HashMap<>();

    public static ProvedCountManager getInstance() {
        if (provedCountManager == null) provedCountManager = new ProvedCountManager();
        return provedCountManager;
    }

    int getProved(Player player) {
        UUID uuid = player.getUniqueId();
        if (reportsProved.containsKey(uuid)) return reportsProved.get(uuid).getProved();
        return getStats(uuid)[0];
    }

    int getbeProved(Player player) {
        UUID uuid = player.getUniqueId();
        if (reportsProved.containsKey(uuid)) return reportsProved.get(uuid).getBeProved();
        return getStats(uuid)[1];
    }

    private int[] getStats(UUID uuid) {
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection(); PreparedStatement statement = connection.prepareStatement("SELECT `ProvedReports`,`beProvedReported` FROM `ReportSystem_Leader` WHERE `ReporterUUID`=?")) {
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int proved = resultSet.getInt("ProvedReports");
                int beProved = resultSet.getInt("beProvedReported");
                reportsProved.putIfAbsent(uuid, new ProvedReport(proved, beProved));
                return new int[]{proved, beProved};
            } else {
                reportsProved.putIfAbsent(uuid, new ProvedReport(0, 0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[]{0, 0};
    }

    public void clearCache(UUID uuid) { // for redis channel listener
        reportsProved.remove(uuid);
    }

}
