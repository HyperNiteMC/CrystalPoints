package main.com.caxerx.mc.commandhandler.subcommand;

import main.com.caxerx.mc.commandhandler.SubCommand;
import main.com.caxerx.mc.crystalpoints.CrystalPoints;
import main.com.caxerx.mc.crystalpoints.runnable.BalanceMessageRunnable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.UUID;

/**
 * Created by caxerx on 2017/4/1.
 */
public class BalanceOthersSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        UUID uuid = Bukkit.getPlayerUniqueId(args[0]);
        if (uuid == null) return;
        new BalanceMessageRunnable(sender, Bukkit.getOfflinePlayer(uuid)).runTaskAsynchronously(CrystalPoints.getInstance());
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
