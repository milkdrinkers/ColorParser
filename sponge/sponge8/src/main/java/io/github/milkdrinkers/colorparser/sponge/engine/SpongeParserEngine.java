package io.github.milkdrinkers.colorparser.sponge.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import io.github.milkdrinkers.colorparser.sponge.SpongeComponentBuilder;
import io.github.milkdrinkers.colorparser.sponge.placeholder.SpongePlaceholderContext;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class SpongeParserEngine extends ParserEngine<SpongeComponentBuilder, SpongeParserEngineBuilder, SpongeParserEngine, SpongePlaceholderContext> {
    /**
     * Creates a new ParserEngine with the given settings.
     *
     * @param miniMessage The MiniMessage instance
     * @param parseLegacy Whether to parse legacy format or not
     */
    protected SpongeParserEngine(@NotNull MiniMessage miniMessage, boolean parseLegacy) {
        super(miniMessage, parseLegacy);
    }

    @Override
    public @NotNull SpongeComponentBuilder parse(@NotNull String content) {
        return new SpongeComponentBuilder(this, content);
    }

    @Override
    public @NotNull SpongeComponentBuilder parse(@NotNull ComponentLike component) {
        return new SpongeComponentBuilder(this, getMiniMessage().serialize(component.asComponent()));
    }
}
