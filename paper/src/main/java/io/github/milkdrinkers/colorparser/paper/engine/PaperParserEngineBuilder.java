package io.github.milkdrinkers.colorparser.paper.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngineBuilder;
import io.github.milkdrinkers.colorparser.paper.PaperComponentBuilder;
import io.github.milkdrinkers.colorparser.paper.placeholder.PaperPlaceholderContext;
import org.jetbrains.annotations.NotNull;

public class PaperParserEngineBuilder extends ParserEngineBuilder<PaperComponentBuilder, PaperParserEngineBuilder, PaperParserEngine, PaperPlaceholderContext> {
    public PaperParserEngineBuilder() {
        super();
    }

    @Override
    public @NotNull PaperParserEngine build() {
        super.buildInternal();

        return new PaperParserEngine(
            this.miniMessage,
            this.parseLegacy
        );
    }
}
