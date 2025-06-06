package io.github.milkdrinkers.colorparser.common.engine;

import io.github.milkdrinkers.colorparser.common.ComponentBuilder;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProviderManager;
import io.github.milkdrinkers.colorparser.common.placeholder.SimplePlaceholderContext;
import io.github.milkdrinkers.colorparser.common.processor.LegacyColorsProcessor;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

/**
 * A customizable engine for parsing components.
 * This is the core processing unit that handles component transformations.
 */
public abstract class ParserEngine<ColorParser extends ComponentBuilder<ColorParser, EngineBuilder, Engine, Context>, EngineBuilder extends ParserEngineBuilder<ColorParser, EngineBuilder, Engine, Context>, Engine extends ParserEngine<ColorParser, EngineBuilder, Engine, Context>, Context extends SimplePlaceholderContext<?, ?>> {
    private final @NotNull MiniMessage miniMessage;
    private final boolean parseLegacy;
    private final LegacyColorsProcessor legacyColorsProcessor = new LegacyColorsProcessor();
    private final PlaceholderProviderManager<Context> placeholderManager = new PlaceholderProviderManager<>();

    /**
     * Creates a new ParserEngine with the given settings.
     *
     * @param miniMessage The MiniMessage instance
     * @param parseLegacy Whether to parse legacy format or not
     */
    protected ParserEngine(@NotNull MiniMessage miniMessage, boolean parseLegacy) {
        this.miniMessage = miniMessage;
        this.parseLegacy = parseLegacy;
    }

    /**
     * Creates a new ComponentBuilder for the given content.
     *
     * @param content The content to parse
     * @return A new ComponentBuilder
     */
    @NotNull
    public abstract ColorParser parse(@NotNull String content);

    /**
     * Creates a new ComponentBuilder for the given component.
     *
     * @param component The component to parse
     * @return A new ComponentBuilder
     */
    @NotNull
    public abstract ColorParser parse(@NotNull ComponentLike component);

    /**
     * Get the MiniMessage instance.
     *
     * @return The MiniMessage instance
     */
    @NotNull
    public MiniMessage getMiniMessage() {
        return miniMessage;
    }

    /**
     * Get whether this engine parses legacy formatted text.
     *
     * @return Whether this engine parses legacy format
     */
    public boolean isParsingLegacy() {
        return parseLegacy;
    }

    /**
     * Get the legacy colors processor.
     *
     * @return The LegacyColorsProcessor instance
     */
    public @NotNull LegacyColorsProcessor getLegacyColorsProcessor() {
        return legacyColorsProcessor;
    }

    @NotNull
    public PlaceholderProviderManager<Context> getPlaceholderManager() {
        return placeholderManager;
    }
}