package com.github.milkdrinkers.colorparser;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.Component.*;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.examination.string.MultiLineStringExaminer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.xml.validation.Validator;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.ClickEvent.runCommand;
import static net.kyori.adventure.text.format.NamedTextColor.BLUE;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;
import static net.kyori.adventure.text.format.TextDecoration.UNDERLINED;

public class ColorParserTest {
    private ServerMock server;

    @BeforeEach
    public void setUp()
    {
        server = MockBukkit.mock();
    }

    @AfterEach
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    private void assertEquals(Component input, Component expected) {
        Assertions.assertEquals(input.compact(), expected.compact());
    }

    private void assertNotEquals(Component input, Component expected) {
        Assertions.assertNotEquals(input.compact(), expected.compact());
    }

    // Look at https://github.com/KyoriPowered/adventure/blob/main/4/text-minimessage/src/test/java/net/kyori/adventure/text/minimessage/MiniMessageParserTest.java
    @Test
    void testInvalidTagComplex() {
        Component input = ColorParser.of("<yellow><test> random <bold>stranger</bold><click:run_command:test command><oof></oof><underlined><red>click here</click><blue> to <bold>FEEL</underlined> it").build();
        final Component expected = empty().color(YELLOW)
            .append(text("<test> random "))
            .append(text("stranger").decorate(BOLD))
            .append(empty().clickEvent(runCommand("test command"))
                .append(text("<oof></oof>"))
                .append(text("click here").color(RED).decorate(UNDERLINED))
            ).append(empty().color(BLUE)
                .append(text(" to "))
                .append(text("FEEL</underlined> it").decorate(BOLD))
            );

        assertEquals(input, expected);
    }

    @Test
    void testSimple() {
        Component input = ColorParser.of("<blue>blue").build();
        Component expected = text("blue").color(BLUE);
        assertEquals(input, expected);
    }
}
