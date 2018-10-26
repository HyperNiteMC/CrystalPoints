package main.com.caxerx.mc.crystalpoints.api;

import org.black_ixx.bossshop.pointsystem.BSPointsPlugin;
import org.bukkit.OfflinePlayer;

/**
 * Created by caxerx on 2016/6/27.
 */
public class PointsHandler extends BSPointsPlugin {

    public PointsHandler(String s, String... strings) {
        super(s, strings);
    }

    @Override
    public double getPoints(OfflinePlayer offlinePlayer) {
        return 0;
    }

    @Override
    public double setPoints(OfflinePlayer offlinePlayer, double v) {
        return 0;
    }

    @Override
    public double takePoints(OfflinePlayer offlinePlayer, double v) {
        return 0;
    }

    @Override
    public double givePoints(OfflinePlayer offlinePlayer, double v) {
        return 0;
    }

    @Override
    public boolean usesDoubleValues() {
        return false;
    }
}