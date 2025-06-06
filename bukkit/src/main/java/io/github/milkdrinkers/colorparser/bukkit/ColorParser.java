package io.github.milkdrinkers.colorparser.bukkit;

import io.github.milkdrinkers.colorparser.bukkit.engine.BukkitParserEngine;
import io.github.milkdrinkers.colorparser.bukkit.engine.BukkitParserEngineBuilder;
import io.github.milkdrinkers.colorparser.bukkit.placeholder.BukkitPlaceholderContext;
import io.github.milkdrinkers.colorparser.bukkit.placeholder.provider.PlaceholderAPIProvider;
import io.github.milkdrinkers.colorparser.common.ColorParserBase;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The main class for the ColorParser library, providing methods to create
 * builders for parsing contents into Adventure {@link Component}'s.
 *
 * @see Component
 * @since 4.0.0
 */
public class ColorParser {
    private static @Nullable ColorParserImpl INSTANCE = null;

    private static @NotNull ColorParserImpl getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ColorParserImpl();
        return INSTANCE;
    }

    /**
     * Initializes the parser with the given Bukkit parser engine and adventure audiences.
     *
     * @param adventure The Bukkit audiences for sending messages
     * @apiNote This method must be called before using any of the parser methods.
     * @see #init(BukkitAudiences, BukkitParserEngine)
     * @see BukkitAudiences
     * @since 4.0.0
     */
    public static void init(BukkitAudiences adventure) {
        INSTANCE = new ColorParserImpl(adventure);
    }

    /**
     * Initializes the parser with the given Bukkit parser engine and adventure audiences.
     *
     * @param adventure The Bukkit audiences for sending messages
     * @param engine    The Bukkit parser engine to use
     * @apiNote This method must be called before using any of the parser methods.
     * @see #init(BukkitAudiences)
     * @see BukkitAudiences
     * @see BukkitParserEngineBuilder
     * @since 4.0.0
     */
    public static void init(BukkitAudiences adventure, BukkitParserEngine engine) {
        INSTANCE = new ColorParserImpl(adventure, engine);
    }

    /**
     * Creates a new colorparser/builder for the given content.
     *
     * @param content The content to parse
     * @return A new colorparser instance
     * @implSpec On Bukkit you must initialize the parser with {@link #init(BukkitAudiences)} or {@link #init(BukkitAudiences, BukkitParserEngine)} before using this method.
     * @see #init(BukkitAudiences)
     * @since 4.0.0
     */
    public static BukkitComponentBuilder of(String content) {
        return getInstance().of(content);
    }

    /**
     * Creates a new colorparser/builder for the given content.
     *
     * @param component The component to parse
     * @return A new colorparser instance
     * @implSpec On Bukkit you must initialize the parser with {@link #init(BukkitAudiences)} or {@link #init(BukkitAudiences, BukkitParserEngine)} before using this method.
     * @see #init(BukkitAudiences)
     * @since 4.0.0
     */
    public static BukkitComponentBuilder of(ComponentLike component) {
        return getInstance().of(component);
    }

    private static class ColorParserImpl extends ColorParserBase<BukkitComponentBuilder, BukkitParserEngineBuilder, BukkitParserEngine, BukkitPlaceholderContext> {
        private BukkitAudiences adventure;

        public ColorParserImpl() {
            super();
        }

        public ColorParserImpl(BukkitAudiences adventure) {
            super();
            this.adventure = adventure;

            // Register placeholder providers
            super.getEngine().getPlaceholderManager().add(new PlaceholderAPIProvider());
        }

        public ColorParserImpl(BukkitAudiences adventure, BukkitParserEngine engine) {
            super(engine);
            this.adventure = adventure;

            // Register placeholder providers
            super.getEngine().getPlaceholderManager().add(new PlaceholderAPIProvider());
        }

        @Override
        public @NotNull BukkitParserEngineBuilder engine() {
            return new BukkitParserEngineBuilder(adventure);
        }
    }
}
