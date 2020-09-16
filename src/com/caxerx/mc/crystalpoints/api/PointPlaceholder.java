package com.caxerx.mc.crystalpoints.api;

import com.caxerx.mc.crystalpoints.cache.DataCachingException;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PointPlaceholder extends PlaceholderExpansion {

    private final Plugin plugin;
    private final CrystalPointsAPI api;

    public PointPlaceholder(Plugin plugin) {
        this.plugin = plugin;
        this.api = CrystalPointsAPI.getInstance();
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        try {
            if ("points".equals(params.toLowerCase())) {
                return api.getBalance(p) + "";
            }
        } catch (DataCachingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getName().toLowerCase();
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
