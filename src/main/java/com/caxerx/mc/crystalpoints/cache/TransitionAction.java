package com.caxerx.mc.crystalpoints.cache;

import org.bukkit.OfflinePlayer;

/**
 * Created by caxerx on 2016/6/28.
 */
public class TransitionAction {
    private OfflinePlayer player;
    private TransitionalType type;
    private double value;
    private String operator;

    public TransitionAction(OfflinePlayer player, TransitionalType type, double value, String operator) {
        this.player = player;
        this.type = type;
        this.value = value;
        this.operator = operator;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public TransitionalType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public String getOperator() {
        return operator;
    }

}