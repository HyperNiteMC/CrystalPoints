package com.caxerx.mc.interconomy.cache;

import com.caxerx.mc.interconomy.InterConomy;
import com.caxerx.mc.interconomy.InterConomyConfig;
import com.caxerx.mc.interconomy.runnable.TransitionRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by caxerx on 2016/8/13.
 */
public class TransitionManager {
    private ConcurrentLinkedQueue<TransitionAction> transitional;
    private static TransitionManager instance;
    private InterConomyConfig config;
    private InterConomy plugin;

    public TransitionManager(InterConomy plugin, InterConomyConfig config) {
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
