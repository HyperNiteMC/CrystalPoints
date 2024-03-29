package com.caxerx.mc.crystalpoints.commandhandler.subcommand;

import com.caxerx.mc.crystalpoints.cache.TransitionalType;
import com.caxerx.mc.crystalpoints.commandhandler.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by caxerx on 2017/4/1.
 */
public class BalanceDepositSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        BalanceModifyCommand.execute(TransitionalType.DEPOSIT, sender, args);
    }

    @Override
    public List<String> getTabList(int arg) {
        return null;
    }

    @Override
    public String getPermission() {
        return "crystalpoints.modify";
    }

    @Override
    public String getName() {
        return "deposit";
    }
}
