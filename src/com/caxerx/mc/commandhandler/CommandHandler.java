package com.caxerx.mc.commandhandler;

import com.caxerx.mc.crystalpoints.CrystalPoints;
import com.caxerx.mc.crystalpoints.CrystalPointsConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * Created by caxerx on 2017/3/31.
 */
public class CommandHandler implements CommandExecutor {
    public void executeSubCommand(CommandSender sender, String subcommand, int arg, String[] args) throws CommandNotFoundException, CommandArgsErrorException, PermissionInsufficientException {
        SubCommand sub = CommandManager.getInstance().getSubCommand(subcommand, arg);
        if (sub.getPermission() == null || sender.hasPermission(sub.getPermission())) {
            new CommandExecuteRunnable(sender, sub, args).runTaskAsynchronously(CrystalPoints.getInstance());
        } else {
            throw new PermissionInsufficientException();
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            try {
                executeSubCommand(commandSender, "balance", 0, null);
            } catch (Exception ignore) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageCommandNotFound));
            }
            return true;
        }
        String sub = strings[0];
        try {
            executeSubCommand(commandSender, sub, strings.length - 1, strings.length == 1 ? null : Arrays.copyOfRange(strings, 1, strings.length));
        } catch (CommandNotFoundException e) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageCommandNotFound));
        } catch (CommandArgsErrorException e) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageCommandArgsError));
        } catch (PermissionInsufficientException e) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', CrystalPointsConfig.getInstance().messageCommandPermissionInsufficient));
        } catch (Exception e) {
            CrystalPoints.getInstance().getLogger().log(Level.SEVERE, e.getMessage(), e);

        }
        return true;
    }
}
