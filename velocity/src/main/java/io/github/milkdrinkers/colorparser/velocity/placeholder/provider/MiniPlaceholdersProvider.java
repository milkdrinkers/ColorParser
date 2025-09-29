package io.github.milkdrinkers.colorparser.velocity.placeholder.provider;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProvider;
import io.github.milkdrinkers.colorparser.common.placeholder.provider.MiniPlaceholdersReflectionImpl;
import io.github.milkdrinkers.colorparser.velocity.placeholder.VelocityPlaceholderContext;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public class MiniPlaceholdersProvider extends MiniPlaceholdersReflectionImpl implements PlaceholderProvider<VelocityPlaceholderContext> {
    @Inject
    private ProxyServer server;

    @Override
    public @NotNull Type getType() {
        return Type.MINIMESSAGE;
    }

    @Override
    public boolean isAvailable() {
        final PluginManager pluginManager = server.getPluginManager();
        return pluginManager.getPlugin(getName()).isPresent();
    }

    @Override
    public @NotNull String getName() {
        return "miniplaceholders";
    }

    @Override
    public int getPriority() {
        return 50;
    }

    @Override
    public @NotNull TagResolver getTagResolver(@NotNull VelocityPlaceholderContext context) {
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

    private TagResolver player(final @NotNull VelocityPlaceholderContext context) {
        if (context.getPlatform1Player().isPresent()) {
            return resolveAudience(context.getPlatform1Player().get().getAudience());
        } else {
            return global();
        }
    }

    private TagResolver relational(final @NotNull VelocityPlaceholderContext context) {
        if (context.getPlatform1Player().isPresent() && context.getPlatform2Player().isPresent()) {
            return resolveRelational(
                context.getPlatform1Player().get().getAudience(),
                context.getPlatform2Player().get().getAudience()
            );
        } else {
            return player(context);
        }
    }
}
