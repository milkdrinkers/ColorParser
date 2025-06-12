package io.github.milkdrinkers.colorparser.common.mock;

import io.github.milkdrinkers.colorparser.common.ComponentBuilder;
import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngineBuilder;
import io.github.milkdrinkers.colorparser.common.mock.placeholder.MockPlaceholderContext;

public class MockComponentBuilder extends ComponentBuilder<MockComponentBuilder, MockParserEngineBuilder, MockParserEngine, MockPlaceholderContext> {
    public MockComponentBuilder(MockParserEngine engine, String content) {
        super(engine, content);
    }
}
