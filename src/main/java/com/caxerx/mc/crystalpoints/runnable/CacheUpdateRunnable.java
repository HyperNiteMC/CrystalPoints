package com.caxerx.mc.crystalpoints.runnable;

import com.caxerx.mc.crystalpoints.CrystalPoinrts;
import com.caxerx.mc.crystalpoints.cache.CacheManager;
import com.caxerx.mc.crystalpoints.cache.DataCachingException;
import com.caxerx.mc.crystalpoints.sql.MYSQLController;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by caxerx on 2016/6/28.
 */
public class CacheUpdateRunnable extends BukkitRunnable {
    OfflinePlayer player;
    CacheManager cacheManager = CacheManager.getInstance();
    MYSQLController sql = MYSQLController.getInstance();
    CrystalPoinrts plugin = CrystalPoinrts.getInstance();

    public CacheUpdateRunnable(OfflinePlayer player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            cacheManager.getPlayer(player).cacheBalance(sql.getBalance(player));
            cacheManager.removeOfflinePlayer();
        } catch (DataCachingException ex) {
            new CacheUpdateRunnable(player).runTaskAsynchronously(plugin);
        }
    }
}
