package com.caxerx.mc.interconomy.runnable;

import com.caxerx.mc.interconomy.InterConomy;
import com.caxerx.mc.interconomy.cache.CacheManager;
import com.caxerx.mc.interconomy.cache.DataCachingException;
import com.caxerx.mc.interconomy.sql.MYSQLController;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by caxerx on 2016/6/28.
 */
public class CacheUpdateRunnable extends BukkitRunnable {
    OfflinePlayer player;
    CacheManager cacheManager = CacheManager.getInstance();
    MYSQLController sql = MYSQLController.getInstance();
    InterConomy plugin = InterConomy.getInstance();

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
