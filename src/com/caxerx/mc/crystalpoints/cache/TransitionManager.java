package com.caxerx.mc.crystalpoints.cache;

import com.caxerx.mc.crystalpoints.CrystalPoints;
import com.caxerx.mc.crystalpoints.CrystalPointsConfig;
import com.caxerx.mc.crystalpoints.runnable.TransitionRunnable;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by caxerx on 2016/8/13.
 */
public class TransitionManager {
    private static TransitionManager instance;
    private ConcurrentLinkedQueue<TransitionAction> transitional;
    private CrystalPointsConfig config;
    private CrystalPoints plugin;

    public TransitionManager(CrystalPoints plugin, CrystalPointsConfig config) {
        instance = this;
        this.config = config;
        this.plugin = plugin;
        transitional = new ConcurrentLinkedQueue<>();
    }

    public static TransitionManager getInstance() {
        return instance;
    }

    public void offer(TransitionAction action) {
        transitional.offer(action);
        TransitionRunnable runnable = new TransitionRunnable(transitional);
        runnable.runTaskAsynchronously(plugin);
    }

}
