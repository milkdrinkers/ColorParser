package io.github.milkdrinkers.colorparser.common.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.ansi.ColorLevel;
import net.kyori.examination.string.MultiLineStringExaminer;
import org.junit.jupiter.api.Assertions;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class TestUtil {
    private static final ANSIComponentSerializer ANSI = ANSIComponentSerializer.builder()
        .colorLevel(ColorLevel.TRUE_COLOR)
        .build();

    /**
     * Assert that two component are equal
     *
     * @param expected the expected component
     * @param actual   the actual component
     */
    @SuppressWarnings("unused")
    public static void assertEquals(Component expected, Component actual) {
        final Component expectedCompacted = expected.compact();
        final Component actualCompacted = actual.compact();

        Assertions.assertEquals(
            prettyPrint(expectedCompacted), prettyPrint(actualCompacted), log("Expected parsed value did not match actual", expected, actualCompacted)
        );
    }

    /**
     * Assert that two component are not equal
     *
     * @param expected the expected component
     * @param actual   the actual component
     */
    @SuppressWarnings("unused")
    public static void assertNotEquals(Component expected, Component actual) {
        final Component expectedCompacted = expected.compact();
        final Component actualCompacted = actual.compact();

        Assertions.assertNotEquals(
            prettyPrint(expectedCompacted), prettyPrint(actualCompacted), log("Parsed value matched actual", expected, actualCompacted)
        );
    }

    /**
     * Pretty print a component as a string
     *
     * @param component the component
     * @return a formatted string representation of the component
     */
    public static String prettyPrint(final Component component) {
        return component.examine(MultiLineStringExaminer.simpleEscaping()).collect(Collectors.joining("\n"));
    }

    private static Supplier<String> log(String msg, Component expected, Component actual) {
        return () -> "%s:\n\tExpected: %s\n\tActual:   %s".formatted(msg, ANSI.serialize(expected), ANSI.serialize(actual));
    }
}
