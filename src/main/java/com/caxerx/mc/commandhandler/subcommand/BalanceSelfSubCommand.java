package com.caxerx.mc.commandhandler.subcommand;

import com.caxerx.mc.commandhandler.SubCommand;
import com.caxerx.mc.crystalpoints.CrystalPoinrts;
import com.caxerx.mc.crystalpoints.CrystalPointsConfig;
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
            new BalanceMessageRunnable(sender, (Player) sender).runTaskAsynchronously(CrystalPoinrts.getInstance());
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
