package com.caxerx.mc.crystalpoints;

import com.caxerx.mc.crystalpoints.api.CrystalPointsAPI;
import com.caxerx.mc.crystalpoints.api.PointPlaceholder;
import com.caxerx.mc.crystalpoints.api.PointsHandler;
import com.caxerx.mc.crystalpoints.cache.CacheManager;
import com.caxerx.mc.crystalpoints.cache.TransitionManager;
import com.caxerx.mc.crystalpoints.commandhandler.CommandHandler;
import com.caxerx.mc.crystalpoints.commandhandler.CommandManager;
import com.caxerx.mc.crystalpoints.commandhandler.TabCompletion;
import com.caxerx.mc.crystalpoints.commandhandler.subcommand.*;
import com.caxerx.mc.crystalpoints.sql.MYSQLController;
import com.caxerx.mc.crystalpoints.sql.MYSQLManager;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.features.PointsManager;
import org.black_ixx.bossshop.pointsystem.BSPointsPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Created by caxerx on 2016/6/27.
 */
public class CrystalPoints extends JavaPlugin {
    static CacheManager cacheManager;
    static CrystalPointsAPI api;
    static TransitionManager transitionManager;
    static MYSQLManager sqlManager;
    static MYSQLController sqlController;
    static CrystalPointsConfig config;

    private static CrystalPoints instance;

    public static CrystalPoints getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        config = new CrystalPointsConfig(this);
        sqlManager = new MYSQLManager(config);
        sqlController = new MYSQLController(config);
        cacheManager = new CacheManager(this);
        transitionManager = new TransitionManager(this, config);
        api = new CrystalPointsAPI(cacheManager);
        Optional.ofNullable(getServer().getPluginCommand("crystal")).ifPresent(c -> c.setExecutor(new CommandHandler()));
        Optional.ofNullable(getServer().getPluginCommand("crystal")).ifPresent(c -> c.setTabCompleter(new TabCompletion()));
        CommandManager commandManager = new CommandManager();
        commandManager.registerCommand("info", 0, new BalanceInfoSubCommand());
        commandManager.registerCommand("balance", 0, new BalanceSelfSubCommand());
        commandManager.registerCommand("balance", 1, new BalanceOthersSubCommand());
        commandManager.registerCommand("set", 1, new BalanceSetSubCommand());
        commandManager.registerCommand("take", 2, new BalanceWithdrawSubCommand());
        commandManager.registerCommand("withdraw", 2, new BalanceWithdrawSubCommand());
        commandManager.registerCommand("add", 2, new BalanceDepositSubCommand());
        commandManager.registerCommand("deposit", 2, new BalanceDepositSubCommand());

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        BSPointsPlugin bsPointsPlugin = new PointsHandler();
        bsPointsPlugin.register();
        ClassManager.manager.getSettings().setPointsEnabled(true);
        PointsManager.PointsPlugin plugin = PointsManager.PointsPlugin.CUSTOM;
        plugin.setCustom(bsPointsPlugin.getName());
        ClassManager.manager.getSettings().setPointsPlugin(plugin);

        if (this.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.getLogger().info("Find PlaceholderAPI.");
            new PointPlaceholder(this).register();
        }


        /* Reflection bye bye
        try {
            registerPointsReflect(bsPointsPlugin);
            //checkPointsPlugin();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

         */
    }

    private void registerPointsReflect(BSPointsPlugin bsPointsPlugin) throws IllegalAccessException, NoSuchFieldException {
        ClassManager classManager = ClassManager.manager;
        PointsManager pointsManager = new PointsManager();
        Field pa = pointsManager.getClass().getDeclaredField("pa");
        pa.setAccessible(true);
        pa.set(pointsManager, bsPointsPlugin);
        Field field = classManager.getClass().getDeclaredField("pointsmanager");
        field.setAccessible(true);
        field.set(classManager, pointsManager);
    }

    private void checkPointsPlugin() throws IllegalAccessException {
        System.out.println();
        PointsManager pointsManager = ClassManager.manager.getPointsManager();
        if (pointsManager == null) {
            System.out.println("point manager is null");
            return;
        }
        for (Field field : pointsManager.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println("field: " + field.getName());
            System.out.println("value: " + field.get(pointsManager).getClass().getName());
        }
    }

    public void onDisable() {
        //sqlManager.terminatePool();
        instance = null;
    }

}
