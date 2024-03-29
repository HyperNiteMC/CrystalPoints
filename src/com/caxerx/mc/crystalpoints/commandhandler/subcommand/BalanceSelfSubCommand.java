package com.caxerx.mc.crystalpoints.commandhandler.subcommand;

import com.caxerx.mc.crystalpoints.CrystalPoints;
import com.caxerx.mc.crystalpoints.CrystalPointsConfig;
import com.caxerx.mc.crystalpoints.commandhandler.SubCommand;
import com.caxerx.mc.crystalpoints.runnable.BalanceMessageRunnable;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by caxerx on 2017/4/1.
 */
public class BalanceSelfSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            new BalanceMessageRunnable(sender, (Player) sender).runTaskAsynchronously(CrystalPoints.getInstance());
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageCommandArgsError));
        }

    }

    @Override
    public List<String> getTabList(int arg) {
        return null;
    }

    @Override
    public String getPermission() {
        return "crystalpoints.check";
    }

    @Override
    public String getName() {
        return "balance";
    }
}
