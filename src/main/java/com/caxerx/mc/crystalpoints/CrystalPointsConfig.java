package com.caxerx.mc.crystalpoints;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;

/**
 * Created by caxerx on 2016/6/27.
 */
public class CrystalPointsConfig {
    public final String mysqlHost;
    public final String mysqlDatabase;
    public final int mysqlPort;
    public final String mysqlLogTable;
    public final String mysqlUsername;
    public final String mysqlPassword;
    public final String mysqlUserdataTable;

    public final int connectionPoolMaxConnections;
    public final int connectionPoolMinConnections;
    public final long connectionPoolTimeout;

    public final double defaultBalance;
    public final double updateTimeout;

    public final boolean mysqlSslEnable;

    public final String messagePrefix;
    public final String messageBalance;
    public final String messageBalanceInsufficient;
    public final String messageTransitionalFailure;
    public final String messageTransitionalSuccess;
    public final String messageDataCaching;

    public final String messageCommandPermissionInsufficient;
    public final String messageCommandNotFound;
    public final String messageCommandArgsError;

    public FileConfiguration config;
    public FileConfiguration message;

    private static CrystalPointsConfig instance;

    public CrystalPointsConfig(Plugin plugin) {
        instance = this;

        File msgFile = new File(plugin.getDataFolder().getPath() + File.separator + "message.yml");
        File configFile = new File(plugin.getDataFolder().getPath() + File.separator + "config.yml");
        YamlConfiguration defaultmsg = new YamlConfiguration();
        message = new YamlConfiguration();
        try {
            defaultmsg.loadFromString(IOUtils.toString(plugin.getResource("message.yml"), Charset.defaultCharset()));
            if (!configFile.exists()) {
                FileUtils.copyInputStreamToFile(plugin.getResource("config.yml"), configFile);
            }
            if (!msgFile.exists()) {
                FileUtils.copyInputStreamToFile(plugin.getResource("message.yml"), msgFile);
            }
            message.load(msgFile);
        } catch (IOException | InvalidConfigurationException e) {
            CrystalPoinrts.getInstance().getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        config = plugin.getConfig();
        config.options().copyDefaults(true);
        plugin.saveConfig();
        message.setDefaults(defaultmsg);

        mysqlHost = config.getString("mysql-config.host");
        mysqlPort = config.getInt("mysql-config.port");
        mysqlUsername = config.getString("mysql-config.username");
        mysqlPassword = config.getString("mysql-config.password");
        mysqlDatabase = config.getString("mysql-config.database");
        mysqlUserdataTable = config.getString("mysql-config.userdata-table");
        mysqlLogTable = config.getString("mysql-config.log-table");
        mysqlSslEnable = config.getBoolean("mysql-config.ssl");
        connectionPoolMaxConnections = config.getInt("connection-pool.minimum-connections");
        connectionPoolMinConnections = config.getInt("connection-pool.maximum-connections");
        connectionPoolTimeout = config.getLong("connection-pool.timeout-millis");

        defaultBalance = config.getDouble("default-balance");
        updateTimeout = config.getDouble("update-timeout");

        messagePrefix = message.getString("prefix");
        messageBalance = messagePrefix + message.getString("balance-message");
        messageBalanceInsufficient = messagePrefix + message.getString("balance-insufficient-message");
        messageTransitionalFailure = messagePrefix + message.getString("transitional-failure-message");
        messageTransitionalSuccess = messagePrefix + message.getString("transitional-success-message");
        messageDataCaching = messagePrefix + message.getString("data-caching-message");

        messageCommandPermissionInsufficient = messagePrefix + message.getString("command-permission-insufficient-message");
        messageCommandArgsError = messagePrefix + message.getString("command-augs-error-message");
        messageCommandNotFound = messagePrefix + message.getString("command-not-found-message");
    }


    public static CrystalPointsConfig getInstance() {
        return instance;
    }

}
