package io.github.milkdrinkers.colorparser.bukkit;

import io.github.milkdrinkers.colorparser.bukkit.engine.BukkitParserEngine;
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
@SuppressWarnings("unused")
public class ColorParser {
    private static @Nullable ColorParserImpl INSTANCE = null;

    private static @NotNull ColorParserImpl getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ColorParserImpl();
        return INSTANCE;
    }

    /**
     * Initializes the parser with the given parser engine.
     *
     * @param engine The Parser Engine to use
     * @apiNote This method must be called before using any of the parser methods.
     * @see BukkitParserEngine#builder()
     * @see BukkitParserEngine.Builder
     * @since 4.0.0
     */
    public static void init(BukkitParserEngine engine) {
        INSTANCE = new ColorParserImpl(engine);
    }

    /**
     * Creates a new colorparser/builder for the given content.
     *
     * @param content The content to parse
     * @return A new colorparser instance
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
     * @since 4.0.0
     */
    public static BukkitComponentBuilder of(ComponentLike component) {
        return getInstance().of(component);
    }

    private static class ColorParserImpl extends ColorParserBase<BukkitComponentBuilder, BukkitParserEngine.Builder, BukkitParserEngine, BukkitPlaceholderContext> {
        private BukkitAudiences adventure;

        public ColorParserImpl() {
            super();

            // Register placeholder providers
            super.getEngine().getPlaceholderManager().add(new PlaceholderAPIProvider());
        }

        public ColorParserImpl(BukkitParserEngine engine) {
            super(engine);

            // Register placeholder providers
            super.getEngine().getPlaceholderManager().add(new PlaceholderAPIProvider());
        }

        @Override
        public @NotNull BukkitParserEngine.Builder engine() {
            return BukkitParserEngine.builder().parsePlaceholderAPI(true);
        }
    }
}
