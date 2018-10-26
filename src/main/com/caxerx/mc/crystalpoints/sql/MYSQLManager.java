package main.com.caxerx.mc.crystalpoints.sql;

import com.hypernite.mysql.SQLDataSourceManager;
import main.com.caxerx.mc.crystalpoints.CrystalPoints;
import main.com.caxerx.mc.crystalpoints.CrystalPointsConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class MYSQLManager {
    /*private String host, username, password, database, userdataTable, logTable;*/
    private String userdataTable, logTable;
    /*private int port, minimumConnections, maximumConnections;
    private long connectionTimeout;
    private Connection connection;
    private boolean sslEnabled;
    private static MYSQLManager instance;*/

    /*public MYSQLManager(String host, int port, String username, String password, String database, String userdataTable, String logTable, int minimumConnections, int maximumConnections, long connectionTimeout, boolean sslEnabled) {
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
    }*/

    public MYSQLManager(CrystalPointsConfig config) {
        /*host = config.mysqlHost;
        port = config.mysqlPort;
        username = config.mysqlUsername;
        password = config.mysqlPassword;
        database = config.mysqlDatabase;*/
        userdataTable = config.mysqlUserdataTable;
        logTable = config.mysqlLogTable;
        /*minimumConnections = config.connectionPoolMinConnections;
        maximumConnections = config.connectionPoolMaxConnections;
        connectionTimeout = config.connectionPoolTimeout;
        sslEnabled = config.mysqlSslEnable;*/
        setupPool();
    }

    private void setupPool() {
        /*HikariConfig sqlConfig = new HikariConfig();
        sqlConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=" + sslEnabled);
        sqlConfig.setDriverClassName("com.mysql.jdbc.Driver");
        sqlConfig.setUsername(username);
        sqlConfig.setPassword(password);
        sqlConfig.setMinimumIdle(minimumConnections);
        sqlConfig.setMaximumPoolSize(maximumConnections);
        sqlConfig.setConnectionTimeout(connectionTimeout);
        sqlConfig.setConnectionTestQuery("CREATE TABLE IF NOT EXISTS `" + userdataTable + "` ( `uuid` TEXT NOT NULL , `money` DOUBLE NOT NULL DEFAULT '0' , PRIMARY KEY (`uuid`(36)));");
        sqlConfig.addDataSourceProperty("autoReconnect", true);
        connection = new HikariDataSource(sqlConfig);*/
        try (Connection connection = SQLDataSourceManager.getInstance().getFuckingConnection()){
            PreparedStatement createLogTableStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + logTable + "` ( `uuid` TEXT NOT NULL , `operator` TEXT NOT NULL , `action` TEXT NOT NULL , `value` DOUBLE NOT NULL , `time` BIGINT NOT NULL )");
            PreparedStatement createUserdataTableStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + userdataTable + "` ( `uuid` TEXT NOT NULL , `money` DOUBLE NOT NULL DEFAULT '0' , PRIMARY KEY (`uuid`(36)));");
            createLogTableStatement.execute();
            createUserdataTableStatement.execute();
        } catch (SQLException e) {
            CrystalPoints.getInstance().getLogger().log(Level.SEVERE, e.getSQLState(), e);
        }
    }

    /*public Connection getConnection() {
        return connection;
        return null;
    }

    public void terminatePool() {
        connection.close();
    }*/

}
