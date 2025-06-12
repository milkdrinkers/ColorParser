package io.github.milkdrinkers.colorparser.common.mock.engine;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngineBuilder;
import io.github.milkdrinkers.colorparser.common.mock.MockComponentBuilder;
import io.github.milkdrinkers.colorparser.common.mock.placeholder.MockPlaceholderContext;

public class MockParserEngineBuilder extends ParserEngineBuilder<MockComponentBuilder, MockParserEngineBuilder, MockParserEngine, MockPlaceholderContext> {
    public MockParserEngineBuilder() {
        super();
    }

    @Override
    public MockParserEngine build() {
        super.buildInternal();

        return new MockParserEngine(this.miniMessage, this.parseLegacy);
    }
}
