package com.caxerx.mc.crystalpoints.cache;

import com.caxerx.mc.crystalpoints.CrystalPoints;
import com.caxerx.mc.crystalpoints.CrystalPointsConfig;
import com.caxerx.mc.crystalpoints.UpdateResult;
import com.caxerx.mc.crystalpoints.runnable.CacheUpdateRunnable;
import org.bukkit.OfflinePlayer;

import java.util.concurrent.ConcurrentHashMap;

import static com.caxerx.mc.crystalpoints.UpdateResult.*;

/**
 * Created by caxerx on 2016/8/13.
 */
public class CacheManager {
    private static CacheManager instance;
    private CrystalPoints plugin;
    private ConcurrentHashMap<OfflinePlayer, CrystalPointsUser> users;

    public CacheManager(CrystalPoints plugin) {
        instance = this;
        this.plugin = plugin;
        users = new ConcurrentHashMap<>();
    }

    public static CacheManager getInstance() {
        return instance;
    }

    public CrystalPointsUser getPlayer(OfflinePlayer player) throws DataCachingException {
        if (playerCached(player)) {
            return users.get(player);
        }
        CrystalPointsUser user = new CrystalPointsUser(player);
        users.put(player, user);
        new CacheUpdateRunnable(player).runTaskAsynchronously(plugin);
        throw new DataCachingException();
    }

    public boolean playerCached(OfflinePlayer player) {
        return users.containsKey(player);
    }


    public UpdateResult withdrawPlayer(OfflinePlayer player, double value, String operator) {
        CrystalPointsUser cacheUser = null;
        try {
            cacheUser = getPlayer(player);
        } catch (DataCachingException e) {
            return DATA_CACHING;
        }

        if (cacheUser.hasInitialize()) {
            double cachedBalance = cacheUser.getCachedBalance();
            if (cachedBalance >= value) {
                cacheUser.cacheBalance(cachedBalance - value);
                TransitionManager.getInstance().offer(new TransitionAction(player, TransitionalType.WITHDRAW, value, operator));
                return SUCCESS;
            } else {
                return BALANCE_INSUFFICIENT;
            }
        } else {
            return DATA_CACHING;
        }
    }


    public UpdateResult depositPlayer(OfflinePlayer player, double value, String operator) {
        CrystalPointsUser cacheUser = null;
        try {
            cacheUser = getPlayer(player);
        } catch (DataCachingException e) {
            return DATA_CACHING;
        }

        if (cacheUser.hasInitialize()) {
            double cachedBalance = cacheUser.getCachedBalance();
            cacheUser.cacheBalance(cachedBalance - value);
            TransitionManager.getInstance().offer(new TransitionAction(player, TransitionalType.DEPOSIT, value, operator));
            return SUCCESS;
        } else {
            return DATA_CACHING;
        }
    }


    public UpdateResult setPlayer(OfflinePlayer player, double value, String operator) {
        CrystalPointsUser cacheUser = null;
        try {
            cacheUser = getPlayer(player);
        } catch (DataCachingException e) {
            return DATA_CACHING;
        }

        if (cacheUser.hasInitialize()) {
            double cachedBalance = cacheUser.getCachedBalance();
            cacheUser.cacheBalance(cachedBalance);
            TransitionManager.getInstance().offer(new TransitionAction(player, TransitionalType.SET, value, operator));
            return SUCCESS;
        } else {
            return DATA_CACHING;
        }
    }



    public void removeOfflinePlayer() {
        users.keySet().forEach(user -> {
            if (!user.isOnline() && System.currentTimeMillis() - users.get(user).getLastCacheUpdate() > CrystalPointsConfig.getInstance().updateTimeout) {
                users.remove(user);
            }
        });
    }
}
