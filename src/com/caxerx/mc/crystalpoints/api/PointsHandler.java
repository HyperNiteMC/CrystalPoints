package com.caxerx.mc.crystalpoints.api;

import com.caxerx.mc.crystalpoints.cache.DataCachingException;
import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.pointsystem.BSPointsPlugin;
import org.bukkit.OfflinePlayer;

/**
 * Created by caxerx on 2016/6/27.
 */
public class PointsHandler extends BSPointsPlugin {
    private CrystalPointsAPI api;
    private BossShop plugin;

    public PointsHandler() {
        super("CrystalPoints");
        plugin = ClassManager.manager.getPlugin();
        api = CrystalPointsAPI.getInstance();
    }

    @Override
    public double getPoints(OfflinePlayer offlinePlayer) {
        try {
            return api.getBalance(offlinePlayer);
        } catch (DataCachingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double setPoints(OfflinePlayer offlinePlayer, double v) {
        try {
            api.set(offlinePlayer,v,"console");
        } catch (DataCachingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double takePoints(OfflinePlayer offlinePlayer, double v) {
        try {
            api.withdraw(offlinePlayer,v,"console");
        } catch (DataCachingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double givePoints(OfflinePlayer offlinePlayer, double v) {
        try {
            api.deposit(offlinePlayer,v,"console");
        } catch (DataCachingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean usesDoubleValues() {
        return false;
    }
}