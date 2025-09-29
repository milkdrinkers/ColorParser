package io.github.milkdrinkers.colorparser.common.tag;

import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.util.TestUtil;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.format.NamedTextColor.RED;

@DisplayName("Test - ChoiceResolver")
public class ChoiceResolverTest {
    private MockParserEngine engine;

    @BeforeEach
    void setUp() {
        engine = MockParserEngine.builder().parseLegacy(true).build();
    }

    @Test
    @DisplayName("Choice Low Range")
    public void testChoiceLowRange() {
        final String test = "<choice:<score>:'0-2:Low|3-5:Medium|6-10:High'>";

        final Component actual = engine.parse(test).with("score", "1").build();
        final Component expected = Component.text("Low");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Choice Medium Range")
    public void testChoiceMediumRange() {
        final String test = "<choice:<score>:'0-2:Low|3-5:Medium|6-10:High'>";

        final Component actual = engine.parse(test).with("score", "4").build();
        final Component expected = Component.text("Medium");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Choice High Range")
    public void testChoiceHighRange() {
        final String test = "<choice:<score>:'0-2:Low|3-5:Medium|6-10:High'>";

        final Component actual = engine.parse(test).with("score", "8").build();
        final Component expected = Component.text("High");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Choice Exact Match")
    public void testChoiceExactMatch() {
        final String test = "<choice:<value>:'0:Zero|1:One|2:Two|3:Three'>";

        final Component actual = engine.parse(test).with("value", "2").build();
        final Component expected = Component.text("Two");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Choice Exceed Min")
    public void testChoiceExceedMin() {
        final String test = "<choice:<value>:'0:Zero|1:One|2:Two'>";

        final Component actual = engine.parse(test).with("value", "-1").build();
        final Component expected = Component.text("Zero");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Choice Exceed Max")
    public void testChoiceExceedMax() {
        final String test = "<choice:<value>:'0:Zero|1:One|2:Two'>";

        final Component actual = engine.parse(test).with("value", "3").build();
        final Component expected = Component.text("Two");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Choice No Match Returns Empty")
    public void testChoiceNoMatch() {
        final String test = "<choice:<value>:'0-5:Valid'>";

        final Component actual = engine.parse(test).with("value", "10").build();
        final Component expected = Component.empty();

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Choice with Color")
    public void testChoiceWithColor() {
        final String test = "<red><choice:<score>:'0-5:Low|6-10:High'></red>";

        final Component actual = engine.parse(test).with("score", "3").build();
        final Component expected = Component.text("Low").color(RED);

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Choice First Match Priority")
    public void testChoiceFirstMatchPriority() {
        final String test = "<choice:<value>:'0-10:First|5-15:Second'>";

        final Component actual = engine.parse(test).with("value", "7").build();
        final Component expected = Component.text("First");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Choice With Nested Tags")
    public void testChoiceWithNestedTags() {
        final String test = "<choice:<score>:'0-5:<green>Low|6-10:<red>High'>";

        final Component actual = engine.parse(test).with("score", "8").build();
        final Component expected = Component.text("High").color(RED);

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Choice Edge of Range")
    public void testChoiceEdgeOfRange() {
        final String test = "<choice:<value>:'0-5:First|6-10:Second'>";

        final Component actual = engine.parse(test).with("value", "5").build();
        final Component expected = Component.text("First");

        TestUtil.assertEquals(expected, actual);
    }
}