package io.github.milkdrinkers.colorparser.sponge;

import io.github.milkdrinkers.colorparser.common.ColorParserBase;
import io.github.milkdrinkers.colorparser.sponge.engine.SpongeParserEngine;
import io.github.milkdrinkers.colorparser.sponge.placeholder.SpongePlaceholderContext;
import io.github.milkdrinkers.colorparser.sponge.placeholder.provider.MiniPlaceholdersProvider;
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
     * @see SpongeParserEngine#builder()
     * @see SpongeParserEngine.Builder
     * @since 4.0.0
     */
    public static void init(SpongeParserEngine engine) {
        INSTANCE = new ColorParserImpl(engine);
    }

    /**
     * Creates a new colorparser/builder for the given content.
     *
     * @param content The content to parse
     * @return A new colorparser instance
     * @since 4.0.0
     */
    public static SpongeComponentBuilder of(String content) {
        return getInstance().of(content);
    }

    /**
     * Creates a new colorparser/builder for the given content.
     *
     * @param component The component to parse
     * @return A new colorparser instance
     * @since 4.0.0
     */
    public static SpongeComponentBuilder of(ComponentLike component) {
        return getInstance().of(component);
    }

    private static class ColorParserImpl extends ColorParserBase<SpongeComponentBuilder, SpongeParserEngine.Builder, SpongeParserEngine, SpongePlaceholderContext> {
        public ColorParserImpl() {
            super();

            // Register placeholder providers
            super.getEngine().getPlaceholderManager().add(new MiniPlaceholdersProvider());
        }

        public ColorParserImpl(SpongeParserEngine engine) {
            super(engine);

            // Register placeholder providers
            super.getEngine().getPlaceholderManager().add(new MiniPlaceholdersProvider());
        }

        @Override
        public @NotNull SpongeParserEngine.Builder engine() {
            return SpongeParserEngine.builder()
                .parseMiniPlaceholders(true);
        }
    }
}
