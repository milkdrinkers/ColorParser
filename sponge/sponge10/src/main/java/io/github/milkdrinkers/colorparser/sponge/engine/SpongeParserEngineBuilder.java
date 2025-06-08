package io.github.milkdrinkers.colorparser.sponge.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngineBuilder;
import io.github.milkdrinkers.colorparser.sponge.SpongeComponentBuilder;
import io.github.milkdrinkers.colorparser.sponge.placeholder.SpongePlaceholderContext;
import org.jetbrains.annotations.NotNull;

public class SpongeParserEngineBuilder extends ParserEngineBuilder<SpongeComponentBuilder, SpongeParserEngineBuilder, SpongeParserEngine, SpongePlaceholderContext> {
    public SpongeParserEngineBuilder() {
        super();
    }

    @Override
    public @NotNull SpongeParserEngine build() {
        super.buildInternal();

        return new SpongeParserEngine(
            this.miniMessage,
            this.parseLegacy
        );
    }
}
