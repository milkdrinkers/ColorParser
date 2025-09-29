package io.github.milkdrinkers.colorparser.common.tag;

import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.util.TestUtil;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.format.NamedTextColor.RED;

@DisplayName("Test - UppercaseResolver")
public class UppercaseResolverTest {
    private MockParserEngine engine;

    @BeforeEach
    void setUp() {
        engine = MockParserEngine.builder().parseLegacy(true).build();
    }

    @Test
    @DisplayName("Simple Uppercase")
    public void testSimpleUppercase() {
        final String test = "<uppercase>hello world</uppercase>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("HELLO WORLD");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Uppercase with Mixed Case")
    public void testUppercaseWithMixedCase() {
        final String test = "<uppercase>Hello World</uppercase>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("HELLO WORLD");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Uppercase with Color")
    public void testUppercaseWithColor() {
        final String test = "<red><uppercase>hello world</uppercase></red>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("HELLO WORLD").color(RED);

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Uppercase Alias - upper")
    public void testUppercaseAlias() {
        final String test = "<upper>hello world</upper>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("HELLO WORLD");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Uppercase with Special Characters")
    public void testUppercaseWithSpecialCharacters() {
        final String test = "<uppercase>hello world! 123</uppercase>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("HELLO WORLD! 123");

        TestUtil.assertEquals(expected, actual);
    }
}