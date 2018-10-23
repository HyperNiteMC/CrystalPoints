package com.caxerx.mc.interconomy.runnable;

import com.caxerx.mc.interconomy.InterConomy;
import com.caxerx.mc.interconomy.InterConomyConfig;
import com.caxerx.mc.interconomy.api.InterConomyAPI;
import com.caxerx.mc.interconomy.cache.CacheManager;
import com.caxerx.mc.interconomy.cache.DataCachingException;
import com.caxerx.mc.interconomy.cache.InterConomyUser;
import com.caxerx.mc.interconomy.sql.MYSQLController;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
                    InterConomyUser user = CacheManager.getInstance().getPlayer(player);
                    if (user.hasInitialize()) {
                        String message = InterConomyConfig.getInstance().messageBalance.replace("{money}", user.getCachedBalance() + "").replace("{player}", player.getName());
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    } else {
                        throw new DataCachingException();
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', InterConomyConfig.getInstance().messageCommandArgsError));
                }
            }
        } catch (DataCachingException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', InterConomyConfig.getInstance().messageDataCaching));
            new BalanceMessageRunnable(sender, player).runTaskLaterAsynchronously(InterConomy.getInstance(), 20L);
        }
    }
}
