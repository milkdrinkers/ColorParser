package io.github.milkdrinkers.colorparser.velocity;

import com.velocitypowered.api.proxy.Player;
import io.github.milkdrinkers.colorparser.common.ComponentBuilder;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProviderManager;
import io.github.milkdrinkers.colorparser.velocity.engine.VelocityParserEngine;
import io.github.milkdrinkers.colorparser.velocity.placeholder.VelocityPlaceholderContext;
import io.github.milkdrinkers.colorparser.velocity.placeholder.VelocityPlayer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Platform-specific {@link ComponentBuilder} for creating Adventure {@link Component}'s.
 * <p>
 * This builder extends the abstract {@link ComponentBuilder} and provides additional methods for handling options specific to this platform/environment.
 *
 * @since 4.0.0
 */
@SuppressWarnings("unused")
public final class VelocityComponentBuilder extends ComponentBuilder<VelocityComponentBuilder, VelocityParserEngine.Builder, VelocityParserEngine, VelocityPlaceholderContext> {
    /**
     * Creates a new ComponentBuilder with the given engine and content.
     *
     * @param engine  The ParserEngine to use
     * @param content The content to parse
     * @since 4.0.0
     */
    public VelocityComponentBuilder(@NotNull VelocityParserEngine engine, @NotNull String content) {
        super(engine, content);
        if (engine.isParsingMini())
            mini();
    }

    private @Nullable VelocityPlaceholderContext miniPlaceholdersContext = null;
    private boolean miniPlaceholdersEnabled = false;

    /**
     * Enables MiniPlaceholders parsing for global placeholders.
     *
     * @return The current builder instance
     * @apiNote This will only do something if <a href="https://github.com/MiniPlaceholders/MiniPlaceholders">MiniPlaceholders</a> is installed and running.
     * @since 4.0.0
     */
    @NotNull
    public VelocityComponentBuilder mini() {
        miniPlaceholdersContext = new VelocityPlaceholderContext(PlaceholderContext.Type.GLOBAL, null, null);
        miniPlaceholdersEnabled = true;
        return this;
    }

    /**
     * Enables MiniPlaceholders parsing for player and global placeholders.
     *
     * @return The current builder instance
     * @apiNote This will only do something if <a href="https://github.com/MiniPlaceholders/MiniPlaceholders">MiniPlaceholders</a> is installed and running.
     * @since 4.0.0
     */
    @NotNull
    public VelocityComponentBuilder mini(@NotNull Player player) {
        audienceTarget = player;
        miniPlaceholdersContext = new VelocityPlaceholderContext(PlaceholderContext.Type.PLAYER, new VelocityPlayer(player), null);
        miniPlaceholdersEnabled = true;
        return this;
    }

    /**
     * Enables MiniPlaceholders parsing for relational, player and global placeholders.
     *
     * @return The current builder instance
     * @apiNote This will only do something if <a href="https://github.com/MiniPlaceholders/MiniPlaceholders">MiniPlaceholders</a> is installed and running.
     * @since 4.0.0
     */
    @NotNull
    public VelocityComponentBuilder mini(@NotNull Player player1, @NotNull Player player2) {
        audienceTarget = player1;
        audienceRelation = player2;
        miniPlaceholdersContext = new VelocityPlaceholderContext(PlaceholderContext.Type.RELATIONAL, new VelocityPlayer(player1), new VelocityPlayer(player2));
        miniPlaceholdersEnabled = true;
        return this;
    }

    @Override
    @NotNull
    public Component build() {
        final PlaceholderProviderManager<VelocityPlaceholderContext> placeholderManager = getEngine().getPlaceholderManager();

        if (miniPlaceholdersEnabled && miniPlaceholdersContext != null) {
            placeholderManager.getMiniMessageTagResolver("MiniPlaceholders", miniPlaceholdersContext)
                .ifPresent(this::tag);
        }

        return super.build();
    }
}
