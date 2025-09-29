package io.github.milkdrinkers.colorparser.sponge;

import io.github.milkdrinkers.colorparser.common.ComponentBuilder;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProviderManager;
import io.github.milkdrinkers.colorparser.sponge.engine.SpongeParserEngine;
import io.github.milkdrinkers.colorparser.sponge.placeholder.SpongePlaceholderContext;
import io.github.milkdrinkers.colorparser.sponge.placeholder.SpongePlayer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Platform-specific {@link ComponentBuilder} for creating Adventure {@link Component}'s.
 * <p>
 * This builder extends the abstract {@link ComponentBuilder} and provides additional methods for handling options specific to this platform/environment.
 *
 * @since 4.0.0
 */
@SuppressWarnings("unused")
public final class SpongeComponentBuilder extends ComponentBuilder<SpongeComponentBuilder, SpongeParserEngine.Builder, SpongeParserEngine, SpongePlaceholderContext> {
    /**
     * Creates a new ComponentBuilder with the given engine and content.
     *
     * @param engine  The ParserEngine to use
     * @param content The content to parse
     * @since 4.0.0
     */
    public SpongeComponentBuilder(@NotNull SpongeParserEngine engine, @NotNull String content) {
        super(engine, content);
        if (engine.isParsingMini())
            mini();
    }

    private @Nullable SpongePlaceholderContext miniPlaceholdersContext = null;
    private boolean miniPlaceholdersEnabled = false;

    /**
     * Enables MiniPlaceholders parsing for global placeholders.
     *
     * @return The current builder instance
     * @apiNote This will only do something if <a href="https://github.com/MiniPlaceholders/MiniPlaceholders">MiniPlaceholders</a> is installed and running.
     * @since 4.0.0
     */
    @NotNull
    public SpongeComponentBuilder mini() {
        miniPlaceholdersContext = new SpongePlaceholderContext(PlaceholderContext.Type.GLOBAL, null, null);
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
    public SpongeComponentBuilder mini(@NotNull Player player) {
        audienceTarget = player;
        miniPlaceholdersContext = new SpongePlaceholderContext(PlaceholderContext.Type.PLAYER, new SpongePlayer(player.identity()), null);
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
    public SpongeComponentBuilder mini(@NotNull Player player1, @NotNull Player player2) {
        audienceTarget = player1;
        audienceRelation = player2;
        miniPlaceholdersContext = new SpongePlaceholderContext(PlaceholderContext.Type.RELATIONAL, new SpongePlayer(player1.identity()), new SpongePlayer(player2.identity()));
        miniPlaceholdersEnabled = true;
        return this;
    }

    @Override
    @NotNull
    public Component build() {
        final PlaceholderProviderManager<SpongePlaceholderContext> placeholderManager = getEngine().getPlaceholderManager();

        if (miniPlaceholdersEnabled && miniPlaceholdersContext != null) {
            placeholderManager.getMiniMessageTagResolver("MiniPlaceholders", miniPlaceholdersContext)
                .ifPresent(this::tag);
        }

        return super.build();
    }
}
