package io.github.milkdrinkers.colorparser.velocity.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngineBuilder;
import io.github.milkdrinkers.colorparser.velocity.VelocityComponentBuilder;
import io.github.milkdrinkers.colorparser.velocity.placeholder.VelocityPlaceholderContext;
import org.jetbrains.annotations.NotNull;

public class VelocityParserEngineBuilder extends ParserEngineBuilder<VelocityComponentBuilder, VelocityParserEngineBuilder, VelocityParserEngine, VelocityPlaceholderContext> {
    public VelocityParserEngineBuilder() {
        super();
    }

    @Override
    public @NotNull VelocityParserEngine build() {
        super.buildInternal();

        return new VelocityParserEngine(
            this.miniMessage,
            this.parseLegacy
        );
    }
}
