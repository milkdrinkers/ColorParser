package io.github.milkdrinkers.colorparser.common.mock.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import io.github.milkdrinkers.colorparser.common.mock.MockComponentBuilder;
import io.github.milkdrinkers.colorparser.common.mock.placeholder.MockPlaceholderContext;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class MockParserEngine extends ParserEngine<MockComponentBuilder, MockParserEngine.Builder, MockParserEngine, MockPlaceholderContext> {
    protected MockParserEngine(@NotNull MiniMessage miniMessage, boolean parseDefaultTags, boolean parseDefaultAddonTags, boolean parseLegacy) {
        super(miniMessage, parseDefaultTags, parseDefaultAddonTags, parseLegacy);
    }

    @Override
    protected MockComponentBuilder createComponentBuilder(@NotNull String content) {
        return new MockComponentBuilder(this, content);
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
    public static class Builder extends EngineBuilder<
        MockComponentBuilder,
        Builder,
        MockParserEngine,
        MockPlaceholderContext
        > {

        @Override
        protected @NotNull MockParserEngine buildPlatformSpecific() {
            return new MockParserEngine(
                this.miniMessage,
                this.parseDefaultTags,
                this.parseDefaultAddonTags,
                this.parseLegacy
            );
        }
    }
}
