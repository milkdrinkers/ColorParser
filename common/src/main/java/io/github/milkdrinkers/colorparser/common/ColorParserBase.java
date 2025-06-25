package io.github.milkdrinkers.colorparser.common;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import io.github.milkdrinkers.colorparser.common.placeholder.PlatformPlayer;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

/**
 * The main entry point for the ColorParser library.
 * Provides static methods for parsing text with Adventure components.
 *
 * @since 4.0.0
 */
public abstract class ColorParserBase<ColorParser extends ComponentBuilder<ColorParser, EngineBuilder, Engine, Context>, EngineBuilder extends ParserEngine.EngineBuilder<ColorParser, EngineBuilder, Engine, Context>, Engine extends ParserEngine<ColorParser, EngineBuilder, Engine, Context>, Context extends PlaceholderContext<? extends PlatformPlayer>> {
    private final Engine ENGINE;

    /**
     * Default constructor for ColorParserBase.
     * Initializes the ColorParser with a default ParserEngine.
     *
     * @since 4.0.0
     */
    @SuppressWarnings("this-escape")
    public ColorParserBase() {
        this.ENGINE = engine().parseLegacy(true).build();
    }

    /**
     * Default constructor for ColorParserBase.
     * Initializes the ColorParser with a default ParserEngine.
     *
     * @since 4.0.0
     */
    public ColorParserBase(Engine ENGINE) {
        this.ENGINE = ENGINE;
    }

    /**
     * Returns the current ParserEngine instance.
     *
     * @return The current ParserEngine instance
     * @since 4.0.0
     */
    protected Engine getEngine() {
        return ENGINE;
    }

    /**
     * Creates a new builder for the given content.
     *
     * @param content The content to parse
     * @return A new ComponentBuilder instance
     * @since 4.0.0
     */
    public @NotNull ColorParser of(@NotNull String content) {
        if (ENGINE == null)
            throw new IllegalStateException("ColorParser has not been initialized. Call init() with a valid ParserEngine instance.");
        return ENGINE.parse(content);
    }

    /**
     * Creates a new builder for the given component.
     *
     * @param component The component to parse
     * @return A new ComponentBuilder instance
     * @since 4.0.0
     */
    public @NotNull ColorParser of(@NotNull ComponentLike component) {
        if (ENGINE == null)
            throw new IllegalStateException("ColorParser has not been initialized. Call init() with a valid ParserEngine instance.");
        return ENGINE.parse(component);
    }

    /**
     * Creates a new EngineBuilder to create a custom ParserEngine.
     *
     * @return A new EngineBuilder
     * @since 4.0.0
     */
    public abstract EngineBuilder engine();
}