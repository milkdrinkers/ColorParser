package io.github.milkdrinkers.colorparser.paper.placeholder.provider;

import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProvider;
import io.github.milkdrinkers.colorparser.common.placeholder.provider.MiniPlaceholdersReflectionImpl;
import io.github.milkdrinkers.colorparser.paper.placeholder.PaperPlaceholderContext;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class MiniPlaceholdersProvider extends MiniPlaceholdersReflectionImpl implements PlaceholderProvider<PaperPlaceholderContext> {
    @Override
    public @NotNull Type getType() {
        return Type.MINIMESSAGE;
    }

    @Override
    public boolean isAvailable() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        return pluginManager.getPlugin(getName()) != null && pluginManager.isPluginEnabled(getName());
    }

    @Override
    public @NotNull String getName() {
        return "MiniPlaceholders";
    }

    @Override
    public int getPriority() {
        return 50;
    }

    @Override
    public @NotNull TagResolver getTagResolver(@NotNull PaperPlaceholderContext context) {
        // Lazily do reflection
        lazyLoad();

        switch (context.getType()) {
            case GLOBAL:
                return global();
            case PLAYER:
                return player(context);
            case RELATIONAL:
                return relational(context);
            default:
                return TagResolver.empty();
        }
    }

    private TagResolver global() {
        return resolveGlobal();
    }

    private TagResolver player(final @NotNull PaperPlaceholderContext context) {
        if (context.getPlatform1Player().isPresent()) {
            return resolveAudience(context.getPlatform1Player().get().getOfflinePlayer().getPlayer());
        } else {
            return global();
        }
    }

    private TagResolver relational(final @NotNull PaperPlaceholderContext context) {
        if (context.getPlatform1Player().isPresent() && context.getPlatform2Player().isPresent()) {
            return resolveRelational(
                context.getPlatform1Player().get().getOfflinePlayer().getPlayer(),
                context.getPlatform2Player().get().getOfflinePlayer().getPlayer()
            );
        } else {
            return player(context);
        }
    }
}
