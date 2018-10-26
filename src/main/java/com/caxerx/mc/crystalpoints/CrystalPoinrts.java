package com.caxerx.mc.crystalpoints;

import com.caxerx.mc.commandhandler.CommandHandler;
import com.caxerx.mc.commandhandler.CommandManager;
import com.caxerx.mc.commandhandler.TabCompletion;
import com.caxerx.mc.commandhandler.subcommand.*;
import com.caxerx.mc.crystalpoints.api.CrystalPointsAPI;
import com.caxerx.mc.crystalpoints.api.PointsHandler;
import com.caxerx.mc.crystalpoints.cache.CacheManager;
import com.caxerx.mc.crystalpoints.cache.TransitionManager;
import com.caxerx.mc.crystalpoints.sql.MYSQLController;
import com.caxerx.mc.crystalpoints.sql.MYSQLManager;
import org.black_ixx.bossshop.BossShop;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by caxerx on 2016/6/27.
 */
public class CrystalPoinrts extends JavaPlugin {
    static BossShop bs;
    static CacheManager cacheManager;
    static CrystalPointsAPI api;
    static TransitionManager transitionManager;
    static MYSQLManager sqlManager;
    static MYSQLController sqlController;
    static CrystalPointsConfig config;

    private static CrystalPoinrts instance;

    public void onEnable() {
        instance = this;
        config = new CrystalPointsConfig(this);
        sqlManager = new MYSQLManager(config);
        sqlController = new MYSQLController(sqlManager, config);
        cacheManager = new CacheManager(this);
        transitionManager = new TransitionManager(this, config);
        api = new CrystalPointsAPI(this, config, cacheManager);

        getServer().getPluginCommand("crystal").setExecutor(new CommandHandler());
        getServer().getPluginCommand("crystal").setTabCompleter(new TabCompletion());
        CommandManager commandManager = new CommandManager();
        commandManager.registerCommand("info", 0, new BalanceInfoSubCommand());
        commandManager.registerCommand("balance", 0, new BalanceSelfSubCommand());
        commandManager.registerCommand("balance", 1, new BalanceOthersSubCommand());
        commandManager.registerCommand("set", 1, new BalanceSetSubCommand());
        commandManager.registerCommand("take", 2, new BalanceWithdrawSubCommand());
        commandManager.registerCommand("withdraw", 2, new BalanceWithdrawSubCommand());
        commandManager.registerCommand("add", 2, new BalanceDepositSubCommand());
        commandManager.registerCommand("deposit", 2, new BalanceDepositSubCommand());
        commandManager.registerCommand("reload", 0, new ReloadSubCommand());

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        setupEconomy();
        new PointsHandler("CrystalPoints").register();
}

    public void reload() {
        sqlManager.terminatePool();
        config = new CrystalPointsConfig(this);
        sqlManager = new MYSQLManager(config);
        sqlController = new MYSQLController(sqlManager, config);
        cacheManager = new CacheManager(this);
        transitionManager = new TransitionManager(this, config);
        api = new CrystalPointsAPI(this, config, cacheManager);
    }

    public static CrystalPoinrts getInstance() {
        return instance;
    }

    public void onDisable() {
        sqlManager.terminatePool();
        instance = null;
    }

    private boolean setupEconomy() {
        Plugin plugin = getServer().getPluginManager().getPlugin("BossShopPro");
        if(plugin==null){ //Not installed?
            System.out.print("[BSP Hook] BossShopPro was not found...");
            return false;
        }

        bs = (BossShop) plugin;
        return true;
    }

}
