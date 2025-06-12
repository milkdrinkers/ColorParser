package io.github.milkdrinkers.colorparser.common.mock.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import io.github.milkdrinkers.colorparser.common.mock.MockComponentBuilder;
import io.github.milkdrinkers.colorparser.common.mock.placeholder.MockPlaceholderContext;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class MockParserEngine extends ParserEngine<MockComponentBuilder, MockParserEngineBuilder, MockParserEngine, MockPlaceholderContext> {
    protected MockParserEngine(@NotNull MiniMessage miniMessage, boolean parseLegacy) {
        super(miniMessage, parseLegacy);
    }

    @Override
    public @NotNull MockComponentBuilder parse(@NotNull String content) {
        return new MockComponentBuilder(this, content);
    }

    @Override
    public @NotNull MockComponentBuilder parse(@NotNull ComponentLike component) {
        return new MockComponentBuilder(this, getMiniMessage().serialize(component.asComponent()));
    }
}
