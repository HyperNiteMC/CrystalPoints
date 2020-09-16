package com.caxerx.mc.crystalpoints.api;

import com.caxerx.mc.crystalpoints.UpdateResult;
import com.caxerx.mc.crystalpoints.cache.CacheManager;
import com.caxerx.mc.crystalpoints.cache.DataCachingException;
import org.bukkit.OfflinePlayer;

/**
 * Created by caxerx on 2016/6/28.
 */
public class CrystalPointsAPI {
    private static CrystalPointsAPI instance;
    private final CacheManager cacheManager;

    public CrystalPointsAPI(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        instance = this;
    }

    public static CrystalPointsAPI getInstance() {
        return instance;
    }

    public double getBalance(OfflinePlayer player) throws DataCachingException {
        return cacheManager.getPlayer(player).getCachedBalance();
    }

    public UpdateResult withdraw(OfflinePlayer player, double value, String operator) throws DataCachingException {
        return cacheManager.getPlayer(player).withdraw(value, operator);
    }

    public UpdateResult deposit(OfflinePlayer player, double value, String operator) throws DataCachingException {
        return cacheManager.getPlayer(player).deposit(value, operator);
    }


    public UpdateResult set(OfflinePlayer player, double value, String operator) throws DataCachingException {
        return cacheManager.getPlayer(player).set(value, operator);
    }

}
