package main.com.caxerx.mc.commandhandler.subcommand;

import main.com.caxerx.mc.commandhandler.SubCommand;
import main.com.caxerx.mc.crystalpoints.cache.TransitionalType;
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
        return "crystalpoints.modify";
    }

    @Override
    public String getName() {
        return "set";
    }
}
