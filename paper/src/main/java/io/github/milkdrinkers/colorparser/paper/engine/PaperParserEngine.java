package io.github.milkdrinkers.colorparser.paper.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import io.github.milkdrinkers.colorparser.paper.PaperComponentBuilder;
import io.github.milkdrinkers.colorparser.paper.placeholder.PaperPlaceholderContext;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public final class PaperParserEngine extends ParserEngine<PaperComponentBuilder, PaperParserEngine.Builder, PaperParserEngine, PaperPlaceholderContext> {
    private final boolean papi;
    private final boolean mini;

    /**
     * Creates a new ParserEngine with the given settings.
     *
     * @param miniMessage The MiniMessage instance
     * @param parseLegacy Whether to parse legacy format or not
     */
    private PaperParserEngine(@NotNull MiniMessage miniMessage, boolean parseLegacy, boolean papi, boolean mini) {
        super(miniMessage, parseLegacy);
        this.papi = papi;
        this.mini = mini;
    }

    @Override
    protected PaperComponentBuilder createComponentBuilder(@NotNull String content) {
        return new PaperComponentBuilder(this, content);
    }

    /**
     * If this engine is parsing PlaceholderAPI Placeholders by default.
     *
     * @return true is parsing, false otherwise
     */
    public boolean isParsingPapi() {
        return papi;
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
        PaperComponentBuilder,
        Builder,
        PaperParserEngine,
        PaperPlaceholderContext
        > {
        private boolean papi = true;
        private boolean mini = true;

        /**
         * Sets whether to parse PlaceholderAPI placeholders by default.
         *
         * @param state true to parse, false otherwise
         * @return this builder instance
         */
        @NotNull
        public Builder parsePlaceholderAPI(final boolean state) {
            this.papi = state;
            return this;
        }

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
        protected @NotNull PaperParserEngine buildPlatformSpecific() {
            return new PaperParserEngine(
                this.miniMessage,
                this.parseLegacy,
                this.papi,
                this.mini
            );
        }
    }
}
