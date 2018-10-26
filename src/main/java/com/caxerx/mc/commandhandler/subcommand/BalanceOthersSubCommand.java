package com.caxerx.mc.commandhandler.subcommand;

import com.caxerx.mc.commandhandler.SubCommand;
import com.caxerx.mc.crystalpoints.CrystalPoinrts;
import com.caxerx.mc.crystalpoints.runnable.BalanceMessageRunnable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by caxerx on 2017/4/1.
 */
public class BalanceOthersSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        new BalanceMessageRunnable(sender, Bukkit.getOfflinePlayer(args[0])).runTaskAsynchronously(CrystalPoinrts.getInstance());
    }

    @Override
    public List<String> getTabList(int arg) {
        return null;
    }

    @Override
    public String getPermission() {
        return "crystalpoints.check.other";
    }

    @Override
    public String getName() {
        return "balance";
    }
}
