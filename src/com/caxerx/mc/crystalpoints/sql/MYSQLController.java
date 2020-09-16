package com.caxerx.mc.crystalpoints.sql;

import com.caxerx.mc.crystalpoints.CrystalPoints;
import com.caxerx.mc.crystalpoints.CrystalPointsConfig;
import com.caxerx.mc.crystalpoints.UpdateResult;
import com.hypernite.mc.hnmc.core.main.HyperNiteMC;
import com.hypernite.mc.hnmc.core.managers.SQLDataSource;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Created by caxerx on 2016/6/28.
 */
public class MYSQLController {
    private static MYSQLController instance;
    private final String getAccountStatement;
    private final String getBalanceStatement;
    private final String createAccountStatement;
    private final String updateBalanceStatement;
    private final String setBalanceStatement;
    //private final String logStatement;
    private final double defaultBalance = CrystalPointsConfig.getInstance().defaultBalance;
    private final SQLDataSource sqlDataSource;
    private final CrystalPointsConfig config;

    public MYSQLController(CrystalPointsConfig config) {
        this.config = config;
        this.sqlDataSource = HyperNiteMC.getAPI().getSQLDataSource();
        String userdataTable = config.mysqlUserdataTable;
        getAccountStatement = "SELECT * FROM `" + userdataTable + "` WHERE `uuid`=?";
        getBalanceStatement = "SELECT `money` FROM `" + userdataTable + "` WHERE `uuid` = ?";
        createAccountStatement = "INSERT IGNORE INTO `" + userdataTable + "` VALUES (?,?,?)";
        updateBalanceStatement = "INSERT INTO " + userdataTable + " VALUES(?,?,?) ON DUPLICATE KEY UPDATE name = ?, money = money + ?";
        setBalanceStatement = "INSERT INTO " + userdataTable + " VALUES(?,?,?) ON DUPLICATE KEY UPDATE name = ?, money = ?";
        //logStatement = "INSERT INTO `" + logTable + "`(`uuid`, `operator`, `action`, `value`,`time`) VALUES (?,?,?,?,?)";
        instance = this;
    }

    public static MYSQLController getInstance() {
        return instance;
    }

    public double getBalance(OfflinePlayer player) {
        double result = defaultBalance;
        String uuid = player.getUniqueId().toString();
        try (Connection connection = sqlDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(getBalanceStatement)) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getDouble("money");
            } else {
                createAccount(player);
            }
        } catch (SQLException e) {
            CrystalPoints.getInstance().getLogger().log(Level.SEVERE, e.getSQLState(), e);
        }
        return result;
    }


    public boolean hasAccount(OfflinePlayer player) {
        String uuid = player.getUniqueId().toString();
        try (Connection connection = sqlDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(getAccountStatement)) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            CrystalPoints.getInstance().getLogger().log(Level.SEVERE, e.getSQLState(), e);
        }
        return false;
    }

    public void createAccount(OfflinePlayer player) {
        String uuid = player.getUniqueId().toString();
        try (Connection connection = sqlDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(createAccountStatement)
        ) {
            statement.setString(1, uuid);
            statement.setString(2, player.getName());
            statement.setDouble(3, defaultBalance);
            statement.execute();
        } catch (SQLException e) {
            CrystalPoints.getInstance().getLogger().log(Level.SEVERE, e.getSQLState(), e);
        }
    }


    public UpdateResult updatePlayer(OfflinePlayer player, double value, boolean set) {
        String uuid = player.getUniqueId().toString();
        UpdateResult result = UpdateResult.UNKNOWN;
        try (Connection connection = sqlDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(getBalanceStatement);
             PreparedStatement statement2 = connection.prepareStatement(set ? setBalanceStatement : updateBalanceStatement)) {
            if (!set && value < 0) {
                statement.setString(1, uuid);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    if (resultSet.getDouble("money") < Math.abs(value)) {
                        return UpdateResult.BALANCE_INSUFFICIENT;
                    }
                }
            }
            statement2.setString(1, uuid);
            statement2.setString(2, player.getName());
            statement2.setDouble(3, set ? value : config.defaultBalance + value);
            statement2.setString(4, player.getName());
            statement2.setDouble(5, value);
            if (statement2.executeUpdate() > 0) {
                result = UpdateResult.SUCCESS;
            }
        } catch (SQLException e) {
            CrystalPoints.getInstance().getLogger().log(Level.SEVERE, e.getSQLState(), e);
        }
        return result;
    }



    /*
    public boolean logTransition(String uuid, String operator, String action, double value, long time) {
        return logTransition(uuid, operator, action, value, time, getConnection(), true);
    }

    public boolean logTransition(String uuid, String operator, String action, double value, long time, Connection connection) {
        return logTransition(uuid, operator, action, value, time, connection, false);
    }

    public boolean logTransition(String uuid, String operator, String action, double value, long time, Connection connection, boolean closeConnection) {
        PreparedStatement statement = null;
        boolean result = false;
        try {
            statement = connection.prepareStatement(logStatement);
            statement.setString(1, uuid);
            statement.setString(2, operator);
            statement.setString(3, action);
            statement.setDouble(4, value);
            statement.setLong(5, time);
            if (statement.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (closeConnection) {
                closeConnection(connection);
            }
            closeStatement(statement);
            return result;
        }
    }
*/
}
