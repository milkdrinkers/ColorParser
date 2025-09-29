package io.github.milkdrinkers.colorparser.common.tag;

import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.util.TestUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static net.kyori.adventure.text.format.NamedTextColor.BLUE;

@DisplayName("Test - LighterResolver")
public class LighterResolverTest {
    private MockParserEngine engine;

    @BeforeEach
    void setUp() {
        engine = MockParserEngine.builder().parseLegacy(true).build();
    }

    @Test
    @DisplayName("Lighter with Hex Color")
    public void testLighterWithHexColor() {
        final String test = "<lighter><color:#FF0000>Red Text</color></lighter>";

        final Component actual = engine.parse(test).build();
        final Color brighterRed = new Color(0xFF0000).brighter();
        final Component expected = Component.text("Red Text").color(TextColor.color(brighterRed.getRGB()));

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Lighter without Base Color")
    public void testLighterWithoutBaseColor() {
        final String test = "<lighter>No Color</lighter>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("No Color");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Lighter Nested Multiple Times")
    public void testLighterNestedMultipleTimes() {
        final String test = "<lighter><lighter><blue>Double Lighter</blue></lighter></lighter>";

        final Component actual = engine.parse(test).build();
        final Color doublyBrighterBlue = new Color(BLUE.value()).brighter().brighter();
        final Component expected = Component.text("Double Lighter").color(TextColor.color(doublyBrighterBlue.getRGB()));

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Lighter Nested Multiple Times Without Closing Tag")
    public void testLighterNestedMultipleTimesNoClosing() {
        final String test = "<lighter><lighter><blue>Double Lighter";

        final Component actual = engine.parse(test).build();
        final Color doublyBrighterBlue = new Color(BLUE.value()).brighter().brighter();
        final Component expected = Component.text("Double Lighter").color(TextColor.color(doublyBrighterBlue.getRGB()));

        TestUtil.assertEquals(expected, actual);
    }
}