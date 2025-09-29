package io.github.milkdrinkers.colorparser.common.tag;

import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.util.TestUtil;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.format.NamedTextColor.BLUE;

@DisplayName("Test - LowercaseResolver")
public class LowercaseResolverTest {
    private MockParserEngine engine;

    @BeforeEach
    void setUp() {
        engine = MockParserEngine.builder().parseLegacy(true).build();
    }

    @Test
    @DisplayName("Simple Lowercase")
    public void testSimpleLowercase() {
        final String test = "<lowercase>HELLO WORLD</lowercase>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("hello world");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Lowercase with Mixed Case")
    public void testLowercaseWithMixedCase() {
        final String test = "<lowercase>HeLLo WoRLd</lowercase>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("hello world");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Lowercase with Color")
    public void testLowercaseWithColor() {
        final String test = "<blue><lowercase>HELLO WORLD</lowercase></blue>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("hello world").color(BLUE);

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Lowercase Alias - lower")
    public void testLowercaseAlias() {
        final String test = "<lower>HELLO WORLD</lower>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("hello world");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Lowercase with Special Characters")
    public void testLowercaseWithSpecialCharacters() {
        final String test = "<lowercase>HELLO WORLD! 123</lowercase>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("hello world! 123");

        TestUtil.assertEquals(expected, actual);
    }
}