package io.github.milkdrinkers.colorparser.sponge.placeholder.provider;

import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProvider;
import io.github.milkdrinkers.colorparser.common.placeholder.provider.MiniPlaceholdersReflectionImpl;
import io.github.milkdrinkers.colorparser.sponge.placeholder.SpongePlaceholderContext;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;

public class MiniPlaceholdersProvider extends MiniPlaceholdersReflectionImpl implements PlaceholderProvider<SpongePlaceholderContext> {
    @Override
    public @NotNull Type getType() {
        return Type.MINIMESSAGE;
    }

    @Override
    public boolean isAvailable() {
        if (!Sponge.isServerAvailable())
            return false;
        return Sponge.pluginManager().plugin(getName()).isPresent();
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
    public @NotNull TagResolver getTagResolver(@NotNull SpongePlaceholderContext context) {
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

    private TagResolver player(final @NotNull SpongePlaceholderContext context) {
        if (context.getPlatform1Player().isPresent() && context.getPlatform1Player().get().isOnline()) {
            return resolveAudience(context.getPlatform1Player().get().getAudience());
        } else {
            return global();
        }
    }

    private TagResolver relational(final @NotNull SpongePlaceholderContext context) {
        if (context.getPlatform1Player().isPresent() && context.getPlatform2Player().isPresent() && context.getPlatform1Player().get().isOnline() && context.getPlatform2Player().get().isOnline()) {
            return resolveRelational(
                context.getPlatform1Player().get().getAudience(),
                context.getPlatform2Player().get().getAudience()
            );
        } else {
            return player(context);
        }
    }
}
