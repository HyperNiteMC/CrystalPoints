package com.caxerx.mc.crystalpoints;

import com.caxerx.mc.crystalpoints.runnable.CacheUpdateRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by caxerx on 2017/4/1.
 */
public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        new CacheUpdateRunnable(e.getPlayer()).runTaskAsynchronously(CrystalPoints.getInstance());
    }
}
