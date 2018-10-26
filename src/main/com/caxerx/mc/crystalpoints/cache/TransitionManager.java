package main.com.caxerx.mc.crystalpoints.cache;

import main.com.caxerx.mc.crystalpoints.CrystalPoints;
import main.com.caxerx.mc.crystalpoints.CrystalPointsConfig;
import main.com.caxerx.mc.crystalpoints.runnable.TransitionRunnable;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by caxerx on 2016/8/13.
 */
public class TransitionManager {
    private ConcurrentLinkedQueue<TransitionAction> transitional;
    private static TransitionManager instance;
    private CrystalPointsConfig config;
    private CrystalPoints plugin;

    public TransitionManager(CrystalPoints plugin, CrystalPointsConfig config) {
        instance = this;
        this.config = config;
        this.plugin = plugin;
        transitional = new ConcurrentLinkedQueue<>();
    }

    public void offer(TransitionAction action) {
        transitional.offer(action);
        TransitionRunnable runnable = new TransitionRunnable(transitional);
        runnable.runTaskAsynchronously(plugin);
    }

    public static TransitionManager getInstance() {
        return instance;
    }

}
