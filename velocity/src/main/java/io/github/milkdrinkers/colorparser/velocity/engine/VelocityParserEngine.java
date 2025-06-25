package io.github.milkdrinkers.colorparser.velocity.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import io.github.milkdrinkers.colorparser.velocity.VelocityComponentBuilder;
import io.github.milkdrinkers.colorparser.velocity.placeholder.VelocityPlaceholderContext;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public final class VelocityParserEngine extends ParserEngine<VelocityComponentBuilder, VelocityParserEngine.Builder, VelocityParserEngine, VelocityPlaceholderContext> {
    private final boolean mini;

    /**
     * Creates a new ParserEngine with the given settings.
     *
     * @param miniMessage The MiniMessage instance
     * @param parseLegacy Whether to parse legacy format or not
     */
    private VelocityParserEngine(@NotNull MiniMessage miniMessage, boolean parseDefaultTags, boolean parseDefaultAddonTags, boolean parseLegacy, boolean mini) {
        super(miniMessage, parseDefaultTags, parseDefaultAddonTags, parseLegacy);
        this.mini = mini;
    }

    @Override
    protected VelocityComponentBuilder createComponentBuilder(@NotNull String content) {
        return new VelocityComponentBuilder(this, content);
    }

    /**
     * If this engine is parsing Mini Placeholders by default.
     *
     * @return true is parsing, false otherwise
     */
    public boolean isParsingMini() {
        return mini;
    }

    /**
     * Creates a new EngineBuilder.
     *
     * @return A new EngineBuilder instance
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    /**
     * @see io.github.milkdrinkers.colorparser.common.engine.ParserEngine.EngineBuilder
     * @since 4.0.0
     */
    public static final class Builder extends EngineBuilder<
        VelocityComponentBuilder,
        Builder,
        VelocityParserEngine,
        VelocityPlaceholderContext
        > {
        private boolean mini = true;

        /**
         * Sets whether to parse MiniPlaceholders placeholders by default.
         *
         * @param state true to parse, false otherwise
         * @return this builder instance
         */
        @NotNull
        public Builder parseMiniPlaceholders(final boolean state) {
            this.mini = state;
            return this;
        }

        @Override
        protected @NotNull VelocityParserEngine buildPlatformSpecific() {
            return new VelocityParserEngine(
                this.miniMessage,
                this.parseDefaultTags,
                this.parseDefaultAddonTags,
                this.parseLegacy,
                this.mini
            );
        }
    }
}
