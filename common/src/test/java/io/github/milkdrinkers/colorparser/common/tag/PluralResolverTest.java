package io.github.milkdrinkers.colorparser.common.tag;

import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.util.TestUtil;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.format.NamedTextColor.GOLD;

@DisplayName("Test - PluralResolver")
public class PluralResolverTest {
    private MockParserEngine engine;

    @BeforeEach
    void setUp() {
        engine = MockParserEngine.builder().parseLegacy(true).build();
    }

    @Test
    @DisplayName("Plural One Apple")
    public void testPluralOneApple() {
        final String test = "<plural:<count>:'one:apple|other:apples'>";

        final Component actual = engine.parse(test).with("count", "1").build();
        final Component expected = Component.text("apple");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Plural Multiple Apples")
    public void testPluralMultipleApples() {
        final String test = "<plural:<count>:'one:apple|other:apples'>";

        final Component actual = engine.parse(test).with("count", "5").build();
        final Component expected = Component.text("apples");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Plural Zero Items")
    public void testPluralZeroItems() {
        final String test = "<plural:<count>:'0:zero items|one:item|other:items'>";

        final Component actual = engine.parse(test).with("count", "0").build();
        final Component expected = Component.text("zero items");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Plural Range")
    public void testPluralRange() {
        final String test = "<plural:<points>:'0:zero|1:one|2:two|3-5:few|other:many'>";

        final Component actual = engine.parse(test).with("points", "4").build();
        final Component expected = Component.text("few");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Plural Range Other")
    public void testPluralRangeOther() {
        final String test = "<plural:<points>:'0:zero|1:one|2:two|3-5:few|other:many'>";

        final Component actual = engine.parse(test).with("points", "10").build();
        final Component expected = Component.text("many");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Plural with Color")
    public void testPluralWithColor() {
        final String test = "<gold><plural:<count>:'one:point|other:points'></gold>";

        final Component actual = engine.parse(test).with("count", "1").build();
        final Component expected = Component.text("point").color(GOLD);

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Plural Specific Numbers")
    public void testPluralSpecificNumbers() {
        final String test = "<plural:<count>:'0:no items|1:item|other:items'>";

        final Component actual = engine.parse(test).with("count", "1").build();
        final Component expected = Component.text("item");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Plural With Nested Tags")
    public void testPluralWithNestedTags() {
        final String test = "<plural:<count>:'one:<green>apple|other:<red>apples'>";

        final Component actual = engine.parse(test).with("count", "5").build();
        final Component expected = Component.text("apples").color(net.kyori.adventure.text.format.NamedTextColor.RED);

        TestUtil.assertEquals(expected, actual);
    }
}