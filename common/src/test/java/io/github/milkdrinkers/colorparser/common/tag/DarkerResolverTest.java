package io.github.milkdrinkers.colorparser.common.tag;

import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.util.TestUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;

@DisplayName("Test - DarkerResolver")
public class DarkerResolverTest {
    private MockParserEngine engine;

    @BeforeEach
    void setUp() {
        engine = MockParserEngine.builder().parseLegacy(true).build();
    }

    @Test
    @DisplayName("Darker with Hex Color")
    public void testDarkerWithHexColor() {
        final String test = "<darker><color:#FF0000>Red Text</color></darker>";

        final Component actual = engine.parse(test).build();
        final Color darkerRed = new Color(0xFF0000).darker();
        final Component expected = Component.text("Red Text").color(TextColor.color(darkerRed.getRGB()));

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Darker without Base Color")
    public void testDarkerWithoutBaseColor() {
        final String test = "<darker>No Color</darker>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("No Color");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Darker Nested Multiple Times")
    public void testDarkerNestedMultipleTimes() {
        final String test = "<darker><darker><yellow>Double Darker</yellow></darker></darker>";

        final Component actual = engine.parse(test).build();
        final Color doublyDarkerYellow = new Color(YELLOW.value()).darker().darker();
        final Component expected = Component.text("Double Darker").color(TextColor.color(doublyDarkerYellow.getRGB()));

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Darker Nested Multiple Times Without Closing Tag")
    public void testDarkerNestedMultipleTimesNoClosing() {
        final String test = "<darker><darker><yellow>Double Darker";

        final Component actual = engine.parse(test).build();
        final Color doublyDarkerYellow = new Color(YELLOW.value()).darker().darker();
        final Component expected = Component.text("Double Darker").color(TextColor.color(doublyDarkerYellow.getRGB()));

        TestUtil.assertEquals(expected, actual);
    }
}