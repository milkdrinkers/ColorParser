package io.github.milkdrinkers.colorparser.bukkit.engine;

import io.github.milkdrinkers.colorparser.bukkit.BukkitComponentBuilder;
import io.github.milkdrinkers.colorparser.bukkit.placeholder.BukkitPlaceholderContext;
import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public final class BukkitParserEngine extends ParserEngine<BukkitComponentBuilder, BukkitParserEngine.Builder, BukkitParserEngine, BukkitPlaceholderContext> {
    private final @NotNull BukkitAudiences adventure;
    private final boolean papi;

    /**
     * Creates a new ParserEngine with the given settings.
     *
     * @param miniMessage The MiniMessage instance
     * @param parseLegacy Whether to parse legacy format or not
     */
    private BukkitParserEngine(@NotNull MiniMessage miniMessage, boolean parseLegacy, @NotNull BukkitAudiences adventure, boolean papi) {
        super(miniMessage, parseLegacy);
        this.adventure = adventure;
        this.papi = papi;
    }

    @Override
    protected BukkitComponentBuilder createComponentBuilder(@NotNull String content) {
        return new BukkitComponentBuilder(this, content);
    }

    public @NotNull BukkitAudiences getAdventure() {
        return adventure;
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
        BukkitComponentBuilder,
        Builder,
        BukkitParserEngine,
        BukkitPlaceholderContext
        > {
        private BukkitAudiences adventure = null;
        private boolean papi = true;

        /**
         * Sets the BukkitAudiences instance to use for this engine.
         *
         * @param state The BukkitAudiences instance
         * @return this builder instance
         */
        @NotNull
        public Builder setAdventure(final BukkitAudiences state) {
            this.adventure = state;
            return this;
        }

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

        @Override
        protected @NotNull BukkitParserEngine buildPlatformSpecific() {
            return new BukkitParserEngine(
                this.miniMessage,
                this.parseLegacy,
                this.adventure,
                this.papi
            );
        }
    }
}
