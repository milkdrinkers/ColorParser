package io.github.milkdrinkers.colorparser.common.tag;

import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.util.TestUtil;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;

@DisplayName("Test - TrimResolver")
public class TrimResolverTest {
    private MockParserEngine engine;

    @BeforeEach
    void setUp() {
        engine = MockParserEngine.builder().build();
    }

    @Test
    @DisplayName("Trim Leading Whitespace")
    public void testTrimLeadingWhitespace() {
        final String test = "<trim>   Hello World</trim>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("Hello World");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Trim Trailing Whitespace")
    public void testTrimTrailingWhitespace() {
        final String test = "<trim>Hello World   </trim>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("Hello World");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Trim Both Leading and Trailing Whitespace")
    public void testTrimBothWhitespace() {
        final String test = "<trim>   Hello World   </trim>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("Hello World");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Trim with Color")
    public void testTrimWithColor() {
        final String test = "<yellow><trim>   Hello World   </trim></yellow>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("Hello World").color(YELLOW);

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Trim with Color Nested")
    public void testTrimWithColorNested() {
        final String test = "<trim>   <yellow>Hello World</yellow>   </trim>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("Hello World").color(YELLOW);

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Trim No Whitespace")
    public void testTrimNoWhitespace() {
        final String test = "<trim>Hello World</trim>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("Hello World");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Trim with Tabs and Newlines")
    public void testTrimWithTabsAndNewlines() {
        final String test = "<trim>\t\nHello World\n\t</trim>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("Hello World");

        TestUtil.assertEquals(expected, actual);
    }
}