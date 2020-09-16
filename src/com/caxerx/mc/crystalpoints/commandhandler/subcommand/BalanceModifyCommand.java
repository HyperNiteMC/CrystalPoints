package com.caxerx.mc.crystalpoints.commandhandler.subcommand;

import com.caxerx.mc.crystalpoints.CrystalPointsConfig;
import com.caxerx.mc.crystalpoints.UpdateResult;
import com.caxerx.mc.crystalpoints.api.CrystalPointsAPI;
import com.caxerx.mc.crystalpoints.cache.DataCachingException;
import com.caxerx.mc.crystalpoints.cache.TransitionalType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * Created by caxerx on 2017/4/1.
 */
public class BalanceModifyCommand {
    public static void execute(TransitionalType type, CommandSender sender, String[] args) {
        try {
            String operator = sender.getName();
            UUID uuid = Bukkit.getPlayerUniqueId(args[0]);
            if (uuid == null) return;
            OfflinePlayer user = Bukkit.getOfflinePlayer(uuid);
            double value = Double.parseDouble(args[1]);
            UpdateResult result;
            switch (type) {
                case WITHDRAW:
                    result = CrystalPointsAPI.getInstance().withdraw(user, value, operator);
                    break;
                case SET:
                    result = CrystalPointsAPI.getInstance().set(user, value, operator);
                    break;
                case DEPOSIT:
                    result = CrystalPointsAPI.getInstance().deposit(user, value, operator);
                    break;
                default:
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageCommandArgsError));
                    return;
            }
            if (result == UpdateResult.SUCCESS) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageTransitionalSuccess));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageTransitionalFailure));
            }

        } catch (DataCachingException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageDataCaching));
        } catch (Exception e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageCommandArgsError));
        }
    }
}
