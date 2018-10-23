package com.caxerx.mc.commandhandler.subcommand;

import com.caxerx.mc.commandhandler.SubCommand;
import com.caxerx.mc.interconomy.InterConomy;
import com.caxerx.mc.interconomy.InterConomyConfig;
import com.caxerx.mc.interconomy.api.InterConomyAPI;
import com.caxerx.mc.interconomy.cache.DataCachingException;
import com.caxerx.mc.interconomy.cache.TransitionalType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by caxerx on 2017/4/1.
 */
public class BalanceSetSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        BalanceModifyCommand.execute(TransitionalType.SET, sender, args);
    }

    @Override
    public List<String> getTabList(int arg) {
        return null;
    }

    @Override
    public String getPermission() {
        return "interconomy.modify";
    }

    @Override
    public String getName() {
        return "set";
    }
}