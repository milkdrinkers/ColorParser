package io.github.milkdrinkers.colorparser.paper.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import io.github.milkdrinkers.colorparser.paper.PaperComponentBuilder;
import io.github.milkdrinkers.colorparser.paper.placeholder.PaperPlaceholderContext;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class PaperParserEngine extends ParserEngine<PaperComponentBuilder, PaperParserEngineBuilder, PaperParserEngine, PaperPlaceholderContext> {
    /**
     * Creates a new ParserEngine with the given settings.
     *
     * @param miniMessage The MiniMessage instance
     * @param parseLegacy Whether to parse legacy format or not
     */
    protected PaperParserEngine(@NotNull MiniMessage miniMessage, boolean parseLegacy) {
        super(miniMessage, parseLegacy);
    }

    @Override
    public @NotNull PaperComponentBuilder parse(@NotNull String content) {
        return new PaperComponentBuilder(this, content);
    }

    @Override
    public @NotNull PaperComponentBuilder parse(@NotNull ComponentLike component) {
        return new PaperComponentBuilder(this, getMiniMessage().serialize(component.asComponent()));
    }
}
