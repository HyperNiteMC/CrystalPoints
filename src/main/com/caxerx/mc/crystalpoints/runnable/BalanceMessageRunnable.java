package main.com.caxerx.mc.crystalpoints.runnable;

import main.com.caxerx.mc.crystalpoints.CrystalPoints;
import main.com.caxerx.mc.crystalpoints.CrystalPointsConfig;
import main.com.caxerx.mc.crystalpoints.cache.CacheManager;
import main.com.caxerx.mc.crystalpoints.cache.CrystalPointsUser;
import main.com.caxerx.mc.crystalpoints.cache.DataCachingException;
import main.com.caxerx.mc.crystalpoints.sql.MYSQLController;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by caxerx on 2016/6/28.
 */
public class BalanceMessageRunnable extends BukkitRunnable {
    OfflinePlayer player;
    CommandSender sender;

    public BalanceMessageRunnable(CommandSender sender, OfflinePlayer player) {
        this.player = player;
        this.sender = sender;
    }

    @Override
    public void run() {
        try {
            if (player != null) {
                if (player.isOnline() || MYSQLController.getInstance().hasAccount(player)) {
                    CrystalPointsUser user = CacheManager.getInstance().getPlayer(player);
                    if (user.hasInitialize()) {
                        String message = CrystalPointsConfig.getInstance().messageBalance.replace("{money}", user.getCachedBalance() + "").replace("{player}", player.getName());
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    } else {
                        throw new DataCachingException();
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageCommandArgsError));
                }
            }
        } catch (DataCachingException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageDataCaching));
            new BalanceMessageRunnable(sender, player).runTaskLaterAsynchronously(CrystalPoints.getInstance(), 20L);
        }
    }
}
