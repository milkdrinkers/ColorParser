package io.github.milkdrinkers.colorparser.paper;

import io.github.milkdrinkers.colorparser.common.ComponentBuilder;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProviderManager;
import io.github.milkdrinkers.colorparser.paper.engine.PaperParserEngine;
import io.github.milkdrinkers.colorparser.paper.engine.PaperParserEngineBuilder;
import io.github.milkdrinkers.colorparser.paper.placeholder.PaperPlaceholderContext;
import io.github.milkdrinkers.colorparser.paper.placeholder.PaperPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
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
public class PaperComponentBuilder extends ComponentBuilder<PaperComponentBuilder, PaperParserEngineBuilder, PaperParserEngine, PaperPlaceholderContext> {
    /**
     * Creates a new ComponentBuilder with the given engine and content.
     *
     * @param engine  The ParserEngine to use
     * @param content The content to parse
     * @since 4.0.0
     */
    public PaperComponentBuilder(@NotNull PaperParserEngine engine, @NotNull String content) {
        super(engine, content);
    }

    private @Nullable PaperPlaceholderContext papiContext = null;
    private boolean papiEnabled = false;

    /**
     * Enables PlaceholderAPI parsing for global placeholders.
     *
     * @return The current builder instance
     * @apiNote This will only do something if <a href="https://github.com/PlaceholderAPI/PlaceholderAPI">PlaceholderAPI</a> is installed and running.
     * @since 4.0.0
     */
    @NotNull
    public PaperComponentBuilder papi() {
        papiContext = new PaperPlaceholderContext(PlaceholderContext.Type.GLOBAL, null, null);
        papiEnabled = true;
        return this;
    }

    /**
     * Enables PlaceholderAPI parsing for player and global placeholders.
     *
     * @return The current builder instance
     * @apiNote This will only do something if <a href="https://github.com/PlaceholderAPI/PlaceholderAPI">PlaceholderAPI</a> is installed and running.
     * @since 4.0.0
     */
    @NotNull
    public PaperComponentBuilder papi(@NotNull Player player) {
        papiContext = new PaperPlaceholderContext(PlaceholderContext.Type.PLAYER, new PaperPlayer(player), null);
        papiEnabled = true;
        return this;
    }

    /**
     * Enables PlaceholderAPI parsing for player and global placeholders.
     *
     * @return The current builder instance
     * @apiNote This will only do something if <a href="https://github.com/PlaceholderAPI/PlaceholderAPI">PlaceholderAPI</a> is installed and running.
     * @since 4.0.0
     */
    @NotNull
    public PaperComponentBuilder papi(@NotNull OfflinePlayer player) {
        papiContext = new PaperPlaceholderContext(PlaceholderContext.Type.PLAYER, new PaperPlayer(player), null);
        papiEnabled = true;
        return this;
    }

    /**
     * Enables PlaceholderAPI parsing for relational, player and global placeholders.
     *
     * @return The current builder instance
     * @apiNote This will only do something if <a href="https://github.com/PlaceholderAPI/PlaceholderAPI">PlaceholderAPI</a> is installed and running.
     * @since 4.0.0
     */
    @NotNull
    public PaperComponentBuilder papi(@NotNull Player player1, @NotNull Player player2) {
        papiContext = new PaperPlaceholderContext(PlaceholderContext.Type.RELATIONAL, new PaperPlayer(player1), new PaperPlayer(player2));
        papiEnabled = true;
        return this;
    }

    private @Nullable PaperPlaceholderContext miniPlaceholdersContext = null;
    private boolean miniPlaceholdersEnabled = false;

    /**
     * Enables MiniPlaceholders parsing for global placeholders.
     *
     * @return The current builder instance
     * @apiNote This will only do something if <a href="https://github.com/MiniPlaceholders/MiniPlaceholders">MiniPlaceholders</a> is installed and running.
     * @since 4.0.0
     */
    @NotNull
    public PaperComponentBuilder mini() {
        miniPlaceholdersContext = new PaperPlaceholderContext(PlaceholderContext.Type.GLOBAL, null, null);
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
    public PaperComponentBuilder mini(@NotNull Player player) {
        miniPlaceholdersContext = new PaperPlaceholderContext(PlaceholderContext.Type.PLAYER, new PaperPlayer(player), null);
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
    public PaperComponentBuilder mini(@NotNull OfflinePlayer player) {
        miniPlaceholdersContext = new PaperPlaceholderContext(PlaceholderContext.Type.PLAYER, new PaperPlayer(player), null);
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
    public PaperComponentBuilder mini(@NotNull Player player1, @NotNull Player player2) {
        miniPlaceholdersContext = new PaperPlaceholderContext(PlaceholderContext.Type.RELATIONAL, new PaperPlayer(player1), new PaperPlayer(player2));
        miniPlaceholdersEnabled = true;
        return this;
    }

    @Override
    @NotNull
    public Component build() {
        final PlaceholderProviderManager<PaperPlaceholderContext> placeholderManager = getEngine().getPlaceholderManager();

        if (papiEnabled && papiContext != null) {
            placeholderManager.getStringResolver("PlaceholderAPI")
                .ifPresent(r -> setContent(r.apply(papiContext, getContent())));
        }

        if (miniPlaceholdersEnabled && miniPlaceholdersContext != null) {
            placeholderManager.getMiniMessageTagResolver("MiniPlaceholders", miniPlaceholdersContext)
                .ifPresent(this::tag);
        }

        return super.build();
    }
}
