package io.github.milkdrinkers.colorparser.sponge.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import io.github.milkdrinkers.colorparser.sponge.SpongeComponentBuilder;
import io.github.milkdrinkers.colorparser.sponge.placeholder.SpongePlaceholderContext;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public final class SpongeParserEngine extends ParserEngine<SpongeComponentBuilder, SpongeParserEngine.Builder, SpongeParserEngine, SpongePlaceholderContext> {
    private final boolean mini;

    /**
     * Creates a new ParserEngine with the given settings.
     *
     * @param miniMessage The MiniMessage instance
     * @param parseLegacy Whether to parse legacy format or not
     * @param mini        Whether to use Mini placeholders
     */
    private SpongeParserEngine(@NotNull MiniMessage miniMessage, boolean parseLegacy, boolean mini) {
        super(miniMessage, parseLegacy);
        this.mini = mini;
    }

    @Override
    protected SpongeComponentBuilder createComponentBuilder(@NotNull String content) {
        return new SpongeComponentBuilder(this, content);
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
        SpongeComponentBuilder,
        Builder,
        SpongeParserEngine,
        SpongePlaceholderContext
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
        protected @NotNull SpongeParserEngine buildPlatformSpecific() {
            return new SpongeParserEngine(
                this.miniMessage,
                this.parseLegacy,
                this.mini
            );
        }
    }
}
