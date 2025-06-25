package io.github.milkdrinkers.colorparser.common.engine;

import io.github.milkdrinkers.colorparser.common.ComponentBuilder;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderProviderManager;
import io.github.milkdrinkers.colorparser.common.placeholder.PlatformPlayer;
import io.github.milkdrinkers.colorparser.common.processor.LegacyColorsProcessor;
import io.github.milkdrinkers.colorparser.common.tag.CustomTags;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * A customizable engine for parsing components.
 * This is the core processing unit that handles component transformations.
 *
 * @since 4.0.0
 */
public abstract class ParserEngine<
    ColorParser extends ComponentBuilder<ColorParser, EngineBuilder, Engine, Context>,
    EngineBuilder extends ParserEngine.EngineBuilder<ColorParser, EngineBuilder, Engine, Context>,
    Engine extends ParserEngine<ColorParser, EngineBuilder, Engine, Context>,
    Context extends PlaceholderContext<? extends PlatformPlayer>
    > {
    private final @NotNull MiniMessage miniMessage;
    private final boolean parseDefaultTags;
    private final boolean parseDefaultAddonTags;
    private final boolean parseLegacy;
    private final LegacyColorsProcessor legacyColorsProcessor = new LegacyColorsProcessor();
    private final PlaceholderProviderManager<Context> placeholderManager = new PlaceholderProviderManager<>();

    /**
     * Creates a new ParserEngine with the given settings.
     *
     * @param miniMessage The MiniMessage instance
     * @param parseLegacy Whether to parse legacy format or not
     * @since 4.0.0
     */
    protected ParserEngine(@NotNull MiniMessage miniMessage, boolean parseDefaultTags, boolean parseDefaultAddonTags, boolean parseLegacy) {
        this.miniMessage = miniMessage;
        this.parseDefaultTags = parseDefaultTags;
        this.parseDefaultAddonTags = parseDefaultAddonTags;
        this.parseLegacy = parseLegacy;
    }

    /**
     * Creates a new ComponentBuilder for the given content.
     *
     * @param content The content to parse
     * @return A new ComponentBuilder
     * @since 4.0.0
     */
    @NotNull
    public final ColorParser parse(@NotNull String content) {
        return createComponentBuilder(content);
    }

    /**
     * Creates a new ComponentBuilder for the given component.
     *
     * @param component The component to parse
     * @return A new ComponentBuilder
     * @since 4.0.0
     */
    @NotNull
    public final ColorParser parse(@NotNull ComponentLike component) {
        return createComponentBuilder(getMiniMessage().serialize(component.asComponent()));
    }

    /**
     * Creates a new ComponentBuilder for the given content or component.
     *
     * @param content The content to parse
     * @return A new ComponentBuilder for this platform
     * @since 4.0.0
     */
    @ApiStatus.Internal
    protected abstract ColorParser createComponentBuilder(@NotNull String content);

    /**
     * Get the MiniMessage instance.
     *
     * @return The MiniMessage instance
     * @since 4.0.0
     */
    @NotNull
    public final MiniMessage getMiniMessage() {
        return miniMessage;
    }

    /**
     * Get whether this engine parses default tags shipped with Adventure.
     *
     * @return Whether this engine parses default tags from Adventure
     * @since 4.0.0
     */
    public boolean isParsingDefaultTags() {
        return parseDefaultTags;
    }

    /**
     * Get whether this engine parses default addon tags shipped with ColorParser.
     *
     * @return Whether this engine parses default addon tags from ColorParser
     * @since 4.0.0
     */
    public boolean isParsingDefaultAddonTags() {
        return parseDefaultAddonTags;
    }

    /**
     * Get whether this engine parses legacy formatted text.
     *
     * @return Whether this engine parses legacy format
     * @since 4.0.0
     */
    public final boolean isParsingLegacy() {
        return parseLegacy;
    }

    /**
     * Get the legacy colors processor.
     *
     * @return The LegacyColorsProcessor instance
     * @since 4.0.0
     */
    public final @NotNull LegacyColorsProcessor getLegacyColorsProcessor() {
        return legacyColorsProcessor;
    }

    /**
     * Get the placeholder manager for this engine.
     *
     * @return The PlaceholderProviderManager instance
     * @since 4.0.0
     */
    @NotNull
    public final PlaceholderProviderManager<Context> getPlaceholderManager() {
        return placeholderManager;
    }

    /**
     * EngineBuilder for creating customized ParserEngine instances.
     *
     * @since 4.0.0
     */
    public abstract static class EngineBuilder<ColorParser extends ComponentBuilder<ColorParser, EngineBuilder, Engine, Context>, EngineBuilder extends ParserEngine.EngineBuilder<ColorParser, EngineBuilder, Engine, Context>, Engine extends ParserEngine<ColorParser, EngineBuilder, Engine, Context>, Context extends PlaceholderContext<? extends PlatformPlayer>> {
        protected MiniMessage miniMessage;
        protected boolean parseDefaultTags = true;
        protected boolean parseDefaultAddonTags = true;
        protected boolean parseLegacy = true;

        /**
         * Sets a custom MiniMessage instance to use.
         *
         * @param miniMessage The MiniMessage instance
         * @return This builder
         * @since 4.0.0
         */
        @SuppressWarnings("unchecked")
        @NotNull
        public final EngineBuilder miniMessage(@NotNull MiniMessage miniMessage) {
            this.miniMessage = miniMessage;
            return (EngineBuilder) this;
        }

        /**
         * Sets whether this engine parses the default tags shipped with Adventure.
         *
         * @param value Whether to parse default tags shipped with Adventure or not
         * @return This builder
         * @see TagResolver#standard()
         * @since 4.0.0
         */
        @SuppressWarnings("unchecked")
        @NotNull
        public final EngineBuilder parseDefaultTags(boolean value) {
            this.parseDefaultTags = value;
            return (EngineBuilder) this;
        }

        /**
         * Sets whether this engine parses the default addon tags shipped with ColorParser.
         *
         * @param value Whether to parse default addon tags shipped with ColorParser or not
         * @return This builder
         * @see CustomTags#defaults()
         * @since 4.0.0
         */
        @SuppressWarnings("unchecked")
        @NotNull
        public final EngineBuilder parseDefaultAddonTags(boolean value) {
            this.parseDefaultAddonTags = value;
            return (EngineBuilder) this;
        }

        /**
         * Sets whether this engine parses legacy message format.
         *
         * @param value Whether to parse legacy format or not
         * @return This builder
         * @since 4.0.0
         */
        @SuppressWarnings("unchecked")
        @NotNull
        public final EngineBuilder parseLegacy(boolean value) {
            this.parseLegacy = value;
            return (EngineBuilder) this;
        }

        /**
         * Builds the ParserEngine instance.
         *
         * @return The built ParserEngine instance
         * @implNote Internally this calls common build logic and then delegates to the platform-specific build logic.
         * @since 4.0.0
         */
        @NotNull
        public final Engine build() {
            // Common build logic
            if (miniMessage == null) {
                final MiniMessage.Builder miniMessageBuilder = MiniMessage.builder()
                    .strict(false)
                    .tags(TagResolver.empty());

                miniMessage = miniMessageBuilder.build();
            }

            // Platform-specific build logic
            return buildPlatformSpecific();
        }

        /**
         * Builds the platform-specific ParserEngine instance.
         *
         * @return The platform-specific ParserEngine instance
         * @since 4.0.0
         */
        @ApiStatus.Internal
        @NotNull
        protected abstract Engine buildPlatformSpecific();
    }
}