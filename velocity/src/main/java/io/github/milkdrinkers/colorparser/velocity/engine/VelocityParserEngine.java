package io.github.milkdrinkers.colorparser.velocity.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import io.github.milkdrinkers.colorparser.velocity.VelocityComponentBuilder;
import io.github.milkdrinkers.colorparser.velocity.placeholder.VelocityPlaceholderContext;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class VelocityParserEngine extends ParserEngine<VelocityComponentBuilder, VelocityParserEngineBuilder, VelocityParserEngine, VelocityPlaceholderContext> {
    /**
     * Creates a new ParserEngine with the given settings.
     *
     * @param miniMessage The MiniMessage instance
     * @param parseLegacy Whether to parse legacy format or not
     */
    protected VelocityParserEngine(@NotNull MiniMessage miniMessage, boolean parseLegacy) {
        super(miniMessage, parseLegacy);
    }

    @Override
    public @NotNull VelocityComponentBuilder parse(@NotNull String content) {
        return new VelocityComponentBuilder(this, content);
    }

    @Override
    public @NotNull VelocityComponentBuilder parse(@NotNull ComponentLike component) {
        return new VelocityComponentBuilder(this, getMiniMessage().serialize(component.asComponent()));
    }
}
