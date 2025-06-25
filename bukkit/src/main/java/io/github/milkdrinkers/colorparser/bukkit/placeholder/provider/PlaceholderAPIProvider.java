package io.github.milkdrinkers.colorparser.bukkit.placeholder.provider;

import io.github.milkdrinkers.colorparser.bukkit.placeholder.BukkitPlaceholderContext;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProvider;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class PlaceholderAPIProvider implements PlaceholderProvider<BukkitPlaceholderContext> {
    @Override
    public @NotNull Type getType() {
        return Type.STRING;
    }

    @Override
    public boolean isAvailable() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        return pluginManager.getPlugin(getName()) != null && pluginManager.isPluginEnabled(getName());
    }

    @Override
    public @NotNull String getName() {
        return "PlaceholderAPI";
    }

    @Override
    public @NotNull BiFunction<@NotNull BukkitPlaceholderContext, @NotNull String, @NotNull String> getStringResolver() {
        return this::getStringResolver;
    }

    public @NotNull String getStringResolver(@NotNull BukkitPlaceholderContext context, @NotNull String text) {
        switch (context.getType()) {
            case GLOBAL:
                return global(text);
            case PLAYER:
                return player(context, text);
            default:
                return relational(context, text);
        }
    }

    private @NotNull String global(final @NotNull String text) {
        return PlaceholderAPI.setPlaceholders(null, text);
    }

    private @NotNull String player(final @NotNull BukkitPlaceholderContext context, final @NotNull String text) {
        if (context.getPlatform1Player().isPresent()) {
            if (context.getPlatform1Player().get().getOfflinePlayer().isOnline()) {
                return PlaceholderAPI.setPlaceholders(context.getPlatform1Player().get().getOfflinePlayer().getPlayer(), text);
            } else {
                return PlaceholderAPI.setPlaceholders(context.getPlatform1Player().get().getOfflinePlayer(), text);
            }
        } else {
            return global(text);
        }
    }

    private @NotNull String relational(final @NotNull BukkitPlaceholderContext context, final @NotNull String text) {
        if (context.getPlatform1Player().isPresent() && context.getPlatform2Player().isPresent() && context.getPlatform1Player().get().getOfflinePlayer().isOnline() && context.getPlatform2Player().get().getOfflinePlayer().isOnline()) {
            return PlaceholderAPI.setRelationalPlaceholders(context.getPlatform1Player().get().getOfflinePlayer().getPlayer(), context.getPlatform2Player().get().getOfflinePlayer().getPlayer(), text);
        } else {
            return player(context, text);
        }
    }

    @Override
    public int getPriority() {
        return 100;
    }
}
