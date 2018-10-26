package com.caxerx.mc.crystalpoints.sql;

import com.caxerx.mc.crystalpoints.CrystalPoinrts;
import com.caxerx.mc.crystalpoints.CrystalPointsConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class MYSQLManager {
    private String host, username, password, database, userdataTable, logTable;
    private int port, minimumConnections, maximumConnections;
    private long connectionTimeout;
    private HikariDataSource connectionSource;
    private boolean sslEnabled;
    private static MYSQLManager instance;

    public MYSQLManager(String host, int port, String username, String password, String database, String userdataTable, String logTable, int minimumConnections, int maximumConnections, long connectionTimeout, boolean sslEnabled) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.userdataTable = userdataTable;
        this.logTable = logTable;
        this.minimumConnections = minimumConnections;
        this.maximumConnections = maximumConnections;
        this.connectionTimeout = connectionTimeout;
        instance = this;
        setupPool();
    }

    public static MYSQLManager getInstance() {
        return instance;
    }

    public MYSQLManager(CrystalPointsConfig config) {
        host = config.mysqlHost;
        port = config.mysqlPort;
        username = config.mysqlUsername;
        password = config.mysqlPassword;
        database = config.mysqlDatabase;
        userdataTable = config.mysqlUserdataTable;
        logTable = config.mysqlLogTable;
        minimumConnections = config.connectionPoolMinConnections;
        maximumConnections = config.connectionPoolMaxConnections;
        connectionTimeout = config.connectionPoolTimeout;
        sslEnabled = config.mysqlSslEnable;
        setupPool();
    }

    private void setupPool() {
        HikariConfig sqlConfig = new HikariConfig();
        sqlConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=" + sslEnabled);
        sqlConfig.setDriverClassName("com.mysql.jdbc.Driver");
        sqlConfig.setUsername(username);
        sqlConfig.setPassword(password);
        sqlConfig.setMinimumIdle(minimumConnections);
        sqlConfig.setMaximumPoolSize(maximumConnections);
        sqlConfig.setConnectionTimeout(connectionTimeout);
        sqlConfig.setConnectionTestQuery("CREATE TABLE IF NOT EXISTS `" + userdataTable + "` ( `uuid` TEXT NOT NULL , `money` DOUBLE NOT NULL DEFAULT '0' , PRIMARY KEY (`uuid`(36)));");
        sqlConfig.addDataSourceProperty("autoReconnect", true);
        connectionSource = new HikariDataSource(sqlConfig);
        try {
            PreparedStatement createLogTableStatement = connectionSource.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `" + logTable + "` ( `uuid` TEXT NOT NULL , `operator` TEXT NOT NULL , `action` TEXT NOT NULL , `value` DOUBLE NOT NULL , `time` BIGINT NOT NULL )");
            PreparedStatement createUserdataTableStatement = connectionSource.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `" + userdataTable + "` ( `uuid` TEXT NOT NULL , `money` DOUBLE NOT NULL DEFAULT '0' , PRIMARY KEY (`uuid`(36)));");
            createLogTableStatement.execute();
            createUserdataTableStatement.execute();
        } catch (SQLException e) {
            CrystalPoinrts.getInstance().getLogger().log(Level.SEVERE, e.getSQLState(), e);
        }
    }

    public Connection getConnection() {
        try {
            return connectionSource.getConnection();
        } catch (SQLException e) {
            CrystalPoinrts.getInstance().getLogger().log(Level.SEVERE, e.getSQLState(), e);
        }
        return null;
    }

    public void terminatePool() {
        connectionSource.close();
    }

}
