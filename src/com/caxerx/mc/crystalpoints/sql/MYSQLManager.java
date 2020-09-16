package com.caxerx.mc.crystalpoints.sql;

import com.caxerx.mc.crystalpoints.CrystalPoints;
import com.caxerx.mc.crystalpoints.CrystalPointsConfig;
import com.hypernite.mc.hnmc.core.main.HyperNiteMC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class MYSQLManager {

    private final String userdataTable;


    public MYSQLManager(CrystalPointsConfig config) {
        userdataTable = config.mysqlUserdataTable;
        setupPool();
    }

    private void setupPool() {
        try (Connection connection = HyperNiteMC.getAPI().getSQLDataSource().getConnection()) {
            //PreparedStatement createLogTableStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + logTable + "` ( `uuid` TEXT NOT NULL , `operator` TEXT NOT NULL , `action` TEXT NOT NULL , `value` DOUBLE NOT NULL , `time` BIGINT NOT NULL )");
            PreparedStatement createUserdataTableStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + userdataTable + "` ( `uuid` TEXT NOT NULL , `name` TINYTEXT, `money` DOUBLE NOT NULL DEFAULT '0' , PRIMARY KEY (`uuid`(36)));");
            //createLogTableStatement.execute();
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
