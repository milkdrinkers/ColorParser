package io.github.milkdrinkers.colorparser.velocity.placeholder.provider;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProvider;
import io.github.milkdrinkers.colorparser.velocity.placeholder.VelocityPlaceholderContext;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MiniPlaceholdersProvider implements PlaceholderProvider<VelocityPlaceholderContext> {
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

    private @Nullable ClassLoader classLoader = null;
    private @Nullable Method getGlobalPlaceholdersMethod = null;
    private @Nullable Method getAudiencePlaceholdersMethod = null;
    private @Nullable Method getRelationalPlaceholdersMethod = null;

    @Override
    public @NotNull TagResolver getTagResolver(@NotNull VelocityPlaceholderContext context) {
        try {
            // Lazily do reflection
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
                final Class<?> miniPlaceholdersClass = classLoader.loadClass("io.github.miniplaceholders.api.MiniPlaceholders");
                getGlobalPlaceholdersMethod = miniPlaceholdersClass.getMethod("getGlobalPlaceholders");
                getAudiencePlaceholdersMethod = miniPlaceholdersClass.getMethod("getAudiencePlaceholders", Audience.class);
                getRelationalPlaceholdersMethod = miniPlaceholdersClass.getMethod("getRelationalPlaceholders", Audience.class, Audience.class);
            }

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
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException |
                 ClassNotFoundException e) {
            return TagResolver.empty();
        }
    }

    private TagResolver global() throws InvocationTargetException, IllegalAccessException {
        if (getGlobalPlaceholdersMethod != null) {
            return (TagResolver) getGlobalPlaceholdersMethod.invoke(null);
        }
        return TagResolver.empty();
    }

    private TagResolver player(final @NotNull VelocityPlaceholderContext context) throws InvocationTargetException, IllegalAccessException {
        if (context.getPlatform1Player().isPresent()) {
            if (getAudiencePlaceholdersMethod != null) {
                return TagResolver.resolver(global(), (TagResolver) getAudiencePlaceholdersMethod.invoke(null, context.getPlatform1Player().get().getAudience()));
            }
            return TagResolver.empty();
        } else {
            return global();
        }
    }

    private TagResolver relational(final @NotNull VelocityPlaceholderContext context) throws InvocationTargetException, IllegalAccessException {
        if (context.getPlatform1Player().isPresent() && context.getPlatform2Player().isPresent()) {
            if (getRelationalPlaceholdersMethod != null) {
                return TagResolver.resolver(
                    player(context),
                    (TagResolver) getRelationalPlaceholdersMethod.invoke(
                        null,
                        context.getPlatform1Player().get().getAudience(),
                        context.getPlatform2Player().get().getAudience()
                    )
                );
            }
            return TagResolver.empty();
        } else {
            return player(context);
        }
    }
}
