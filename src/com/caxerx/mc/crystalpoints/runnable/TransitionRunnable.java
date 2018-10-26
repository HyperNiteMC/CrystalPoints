package com.caxerx.mc.crystalpoints.runnable;

import com.caxerx.mc.crystalpoints.CrystalPoints;
import com.caxerx.mc.crystalpoints.cache.TransitionAction;
import com.caxerx.mc.crystalpoints.cache.TransitionalType;
import com.caxerx.mc.crystalpoints.sql.MYSQLController;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;

/**
 * Created by caxerx on 2016/6/28.
 */
public class TransitionRunnable extends BukkitRunnable {

    private final Queue<TransitionAction> trans;

    public TransitionRunnable(Queue<TransitionAction> trans) {
        this.trans = trans;
    }

    @Override
    public void run() {
        TransitionAction action = trans.poll();
        if (action != null) {
            double value = action.getValue();
            TransitionalType type = action.getType();
            OfflinePlayer player = action.getPlayer();
            if (type == TransitionalType.WITHDRAW) {
                value = -value;
            }
            MYSQLController.getInstance().updatePlayer(player, value, type == TransitionalType.SET);
            if (player.isOnline()) {
                new CacheUpdateRunnable(player).runTaskAsynchronously(CrystalPoints.getInstance());
            }
            //sqlController.logTransition(player.getUniqueId().toString(), operator, "WITHDRAW", value, System.currentTimeMillis(), connection, true);
        }
    }
}
