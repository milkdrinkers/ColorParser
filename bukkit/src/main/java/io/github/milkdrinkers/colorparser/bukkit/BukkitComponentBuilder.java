package io.github.milkdrinkers.colorparser.bukkit;

import io.github.milkdrinkers.colorparser.bukkit.engine.BukkitParserEngine;
import io.github.milkdrinkers.colorparser.bukkit.placeholder.BukkitPlaceholderContext;
import io.github.milkdrinkers.colorparser.bukkit.placeholder.BukkitPlayer;
import io.github.milkdrinkers.colorparser.common.ComponentBuilder;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProviderManager;
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
public final class BukkitComponentBuilder extends ComponentBuilder<BukkitComponentBuilder, BukkitParserEngine.Builder, BukkitParserEngine, BukkitPlaceholderContext> {
    /**
     * Creates a new ComponentBuilder with the given engine and content.
     *
     * @param engine  The ParserEngine to use
     * @param content The content to parse
     * @since 4.0.0
     */
    public BukkitComponentBuilder(@NotNull BukkitParserEngine engine, @NotNull String content) {
        super(engine, content);
        if (engine.isParsingPapi())
            papi();
    }

    private @Nullable BukkitPlaceholderContext papiContext = null;
    private boolean papiEnabled = false;

    /**
     * Enables PlaceholderAPI parsing for global placeholders.
     *
     * @return The current builder instance
     * @apiNote This will only do something if <a href="https://github.com/PlaceholderAPI/PlaceholderAPI">PlaceholderAPI</a> is installed and running.
     * @since 4.0.0
     */
    @NotNull
    public BukkitComponentBuilder papi() {
        papiContext = new BukkitPlaceholderContext(PlaceholderContext.Type.GLOBAL, null, null, getEngine().getAdventure());
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
    public BukkitComponentBuilder papi(@NotNull Player player) {
        papiContext = new BukkitPlaceholderContext(PlaceholderContext.Type.PLAYER, new BukkitPlayer(player), null, getEngine().getAdventure());
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
    public BukkitComponentBuilder papi(@NotNull OfflinePlayer player) {
        papiContext = new BukkitPlaceholderContext(PlaceholderContext.Type.PLAYER, new BukkitPlayer(player), null, getEngine().getAdventure());
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
    public BukkitComponentBuilder papi(@NotNull Player player1, @NotNull Player player2) {
        papiContext = new BukkitPlaceholderContext(PlaceholderContext.Type.RELATIONAL, new BukkitPlayer(player1), new BukkitPlayer(player2), getEngine().getAdventure());
        papiEnabled = true;
        return this;
    }

    @Override
    @NotNull
    public Component build() {
        final PlaceholderProviderManager<BukkitPlaceholderContext> placeholderManager = getEngine().getPlaceholderManager();

        if (papiEnabled) {
            placeholderManager.getStringResolver("PlaceholderAPI")
                .ifPresent(r -> setContent(r.apply(papiContext, getContent())));
        }

        return super.build();
    }
}
