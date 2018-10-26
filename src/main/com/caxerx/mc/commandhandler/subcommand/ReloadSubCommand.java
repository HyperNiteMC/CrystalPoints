package main.com.caxerx.mc.commandhandler.subcommand;

import main.com.caxerx.mc.commandhandler.SubCommand;
import main.com.caxerx.mc.crystalpoints.CrystalPoints;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by caxerx on 2017/4/1.
 */
public class ReloadSubCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        CrystalPoints.getInstance().reload();
    }

    @Override
    public List<String> getTabList(int arg) {
        return null;
    }

    @Override
    public String getPermission() {
        return "crystalpoints.reload";
    }

    @Override
    public String getName() {
        return "reload";
    }
}
