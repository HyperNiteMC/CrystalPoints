package com.caxerx.mc.interconomy.api;

import com.caxerx.mc.interconomy.InterConomy;
import com.caxerx.mc.interconomy.InterConomyConfig;
import com.caxerx.mc.interconomy.UpdateResult;
import com.caxerx.mc.interconomy.cache.DataCachingException;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caxerx on 2016/6/27.
 */
public class VaultHandler implements Economy {
    private InterConomyAPI api;
    private static InterConomy plugin = InterConomy.getInstance();
    private static InterConomyConfig config = InterConomyConfig.getInstance();
    DecimalFormat decFormatter;

    public VaultHandler(InterConomy plugin) {
        api = InterConomyAPI.getInstance();
        decFormatter = new DecimalFormat("0");
    }

    @Override
    public boolean isEnabled() {
        return plugin.isEnabled();
    }

    @Override
    public String getName() {
        return "InterConomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return decFormatter.format(v);
    }

    @Override
    public String currencyNamePlural() {
        return "$";
    }

    @Override
    public String currencyNameSingular() {
        return "$";
    }

    @Override
    @Deprecated
    public boolean hasAccount(String s) {
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return true;
    }

    @Override
    @Deprecated
    public boolean hasAccount(String s, String s1) {
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return true;
    }

    @Override
    @Deprecated
    public double getBalance(String s) {
        return getBalance(plugin.getServer().getOfflinePlayer(s));
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        try {
            return api.getBalance(offlinePlayer);
        } catch (DataCachingException e) {
            return 0.0d;
        }
    }

    @Override
    @Deprecated
    public double getBalance(String s, String s1) {
        return getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer);
    }

    @Override
    @Deprecated
    public boolean has(String s, double v) {
        return has(plugin.getServer().getOfflinePlayer(s), v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        try {
            return api.getBalance(offlinePlayer) >= v;
        } catch (DataCachingException e) {
            return false;
        }
    }

    @Override
    @Deprecated
    public boolean has(String s, String s1, double v) {
        return has(plugin.getServer().getOfflinePlayer(s), v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer, v);
    }

    @Override
    @Deprecated
    public EconomyResponse withdrawPlayer(String s, double v) {
        return withdrawPlayer(plugin.getServer().getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        try {
            UpdateResult result = api.withdraw(offlinePlayer, v, "Vault");
            return getResponse(result);
        } catch (DataCachingException e) {
            return new EconomyResponse(v, 0, EconomyResponse.ResponseType.FAILURE, config.messageDataCaching);
        }
    }

    private EconomyResponse getResponse(UpdateResult result) {
        switch (result) {
            case SUCCESS:
                return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, null);
            case BALANCE_INSUFFICIENT:
                return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, config.messageBalanceInsufficient);
            default:
                return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "UNKNOWN ERROR");
        }
    }

    @Override
    @Deprecated
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return withdrawPlayer(plugin.getServer().getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer, v);
    }

    @Override
    @Deprecated
    public EconomyResponse depositPlayer(String s, double v) {
        return depositPlayer(plugin.getServer().getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        try {
            UpdateResult result = api.deposit(offlinePlayer, v, "Vault");
            return getResponse(result);
        } catch (DataCachingException e) {
            return new EconomyResponse(v, 0, EconomyResponse.ResponseType.FAILURE, config.messageDataCaching);
        }
    }

    @Override
    @Deprecated
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return depositPlayer(plugin.getServer().getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer, v);
    }


    @Override
    public EconomyResponse createBank(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank is not supported");
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return true;
    }

}
