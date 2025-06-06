package io.github.milkdrinkers.colorparser.bukkit;

import io.github.milkdrinkers.colorparser.bukkit.engine.BukkitParserEngine;
import io.github.milkdrinkers.colorparser.bukkit.engine.BukkitParserEngineBuilder;
import io.github.milkdrinkers.colorparser.bukkit.placeholder.BukkitPlaceholderContext;
import io.github.milkdrinkers.colorparser.common.ComponentBuilder;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProviderManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
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
public class BukkitComponentBuilder extends ComponentBuilder<BukkitComponentBuilder, BukkitParserEngineBuilder, BukkitParserEngine, BukkitPlaceholderContext> {
    private final @NotNull BukkitAudiences adventure;

    /**
     * Creates a new ComponentBuilder with the given engine and content.
     *
     * @param engine  The ParserEngine to use
     * @param content The content to parse
     * @since 4.0.0
     */
    public BukkitComponentBuilder(@NotNull BukkitParserEngine engine, @NotNull String content, @NotNull BukkitAudiences adventure) {
        super(engine, content);
        this.adventure = adventure;
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
        papiContext = new BukkitPlaceholderContext(PlaceholderContext.Type.GLOBAL, null, null, null, null, null, null, adventure);
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
        papiContext = new BukkitPlaceholderContext(PlaceholderContext.Type.PLAYER, player.getUniqueId(), player.getName(), null, null, player, null, adventure);
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
        papiContext = new BukkitPlaceholderContext(PlaceholderContext.Type.PLAYER, player.getUniqueId(), player.getName(), null, null, player, null, adventure);
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
        papiContext = new BukkitPlaceholderContext(PlaceholderContext.Type.RELATIONAL, player1.getUniqueId(), player1.getName(), player2.getUniqueId(), player2.getName(), player1, player2, adventure);
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
