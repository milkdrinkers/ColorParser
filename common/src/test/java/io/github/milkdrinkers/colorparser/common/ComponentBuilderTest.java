package io.github.milkdrinkers.colorparser.common;

import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.util.TestUtil;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.event.ClickEvent.openUrl;
import static net.kyori.adventure.text.event.HoverEvent.showText;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;

@DisplayName("Test - ComponentBuilder")
public class ComponentBuilderTest {
    private MockParserEngine engine;

    @BeforeEach
    void setUp() {
        engine = MockParserEngine.builder().parseLegacy(true).build();
    }

    @Test
    @DisplayName("Simple Component")
    public void testSimpleComponent() {
        final String test = "<dark_gray>»<gray> To download it from the internet, <click:open_url:'https://www.google.com'><hover:show_text:\"<green>/!\\ install it from Options/ResourcePacks in your game\"><green><bold>CLICK HERE</bold></hover></click>";

        final Component actual = engine.parse(test)
            .build();

        final Component expected = Component.empty().color(DARK_GRAY)
            .append(Component.text("»"))
            .append(Component.empty().color(GRAY)
                .append(Component.text(" To download it from the internet, "))
                .append(Component.text("CLICK HERE").decorate(BOLD).color(GREEN).clickEvent(openUrl("https://www.google.com")).hoverEvent(showText(Component.text("/!\\ install it from Options/ResourcePacks in your game").color(GREEN))))
            );

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Complex Component with String Placeholder")
    public void testComplexComponentWithStringPlaceholder() {
        final String test = "<dark_gray>»<gray> To download it from the internet, <click:open_url:'<pack_url>'><hover:show_text:\"<green>/!\\ install it from Options/ResourcePacks in your game\"><green><bold>CLICK HERE</bold></hover></click>";

        final Component actual = engine.parse(test)
            .with(
                "pack_url",
                "https://www.google.com"
            )
            .build();

        final Component expected = Component.empty().color(DARK_GRAY)
            .append(Component.text("»"))
            .append(Component.empty().color(GRAY)
                .append(Component.text(" To download it from the internet, "))
                .append(Component.text("CLICK HERE").decorate(BOLD).color(GREEN).clickEvent(openUrl("https://www.google.com")).hoverEvent(showText(Component.text("/!\\ install it from Options/ResourcePacks in your game").color(GREEN))))
            );

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Complex Component with Multiple String Placeholders")
    public void testComplexComponentWithStringPlaceholders() {
        final String test = "<dark_gray>»<gray> To download it from the internet, <custom_click>";

        final Component actual = engine.parse(test)
            .with(
                "pack_url",
                "https://www.google.com"
            )
            .with(
                "custom_click",
                "<click:open_url:'<pack_url>'><hover:show_text:\"<green>/!\\ install it from Options/ResourcePacks in your game\"><green><bold>CLICK HERE</bold></hover></click>"
            )
            .build();

        final Component expected = Component.empty().color(DARK_GRAY)
            .append(Component.text("»"))
            .append(Component.empty().color(GRAY)
                .append(Component.text(" To download it from the internet, "))
                .append(Component.text("CLICK HERE").decorate(BOLD).color(GREEN).clickEvent(openUrl("https://www.google.com")).hoverEvent(showText(Component.text("/!\\ install it from Options/ResourcePacks in your game").color(GREEN))))
            );

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Complex Component with ParserEngine Placeholder")
    public void testComplexComponentWithParserEnginePlaceholder() {
        final String test = "<dark_gray>»<gray> To download it from the internet, <custom_click>";

        final Component actual = engine.parse(test)
            .with(
                "custom_click",
                engine.parse("<click:open_url:'<pack_url>'><hover:show_text:\"<green>/!\\ install it from Options/ResourcePacks in your game\"><green><bold>CLICK HERE</bold></hover></click>")
                    .with(
                        "pack_url",
                        "https://www.google.com"
                    )
            )
            .build();

        final Component expected = Component.empty().color(DARK_GRAY)
            .append(Component.text("»"))
            .append(Component.empty().color(GRAY)
                .append(Component.text(" To download it from the internet, "))
                .append(Component.text("CLICK HERE").decorate(BOLD).color(GREEN).clickEvent(openUrl("https://www.google.com")).hoverEvent(showText(Component.text("/!\\ install it from Options/ResourcePacks in your game").color(GREEN))))
            );

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Complex Component with Component Placeholder")
    public void testComplexComponentWithComponentPlaceholder() {
        final String test = "<dark_gray>»<gray> To download it from the internet, <custom_click>";

        final Component actual = engine.parse(test)
            .with(
                "custom_click",
                engine.parse("<click:open_url:'<pack_url>'><hover:show_text:\"<green>/!\\ install it from Options/ResourcePacks in your game\"><green><bold>CLICK HERE</bold></hover></click>")
                    .with(
                        "pack_url",
                        "https://www.google.com"
                    )
                    .build()
            )
            .build();

        final Component expected = Component.empty().color(DARK_GRAY)
            .append(Component.text("»"))
            .append(Component.empty().color(GRAY)
                .append(Component.text(" To download it from the internet, "))
                .append(Component.text("CLICK HERE").decorate(BOLD).color(GREEN).clickEvent(openUrl("https://www.google.com")).hoverEvent(showText(Component.text("/!\\ install it from Options/ResourcePacks in your game").color(GREEN))))
            );

        TestUtil.assertEquals(expected, actual);
    }
}
