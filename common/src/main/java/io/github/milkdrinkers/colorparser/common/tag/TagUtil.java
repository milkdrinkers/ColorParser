package io.github.milkdrinkers.colorparser.common.tag;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Modifying;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Provides utility methods for working with tag resolvers.
 */
final class TagUtil {
    private static final PlainTextComponentSerializer PLAIN_TEXT_SERIALIZER = PlainTextComponentSerializer.plainText();

    private TagUtil() {
    }

    /**
     * Modify the content of a {@link TextComponent} using a function.
     *
     * @param modifier the function to apply to the content
     * @return the tag that applies this modification
     * @apiNote Shorthand for using {@link #modify(Component, int, Function)}
     * @see #modify(Component, int, Function)
     */
    static @NotNull Tag modify(@NotNull Function<String, String> modifier) {
        return (Modifying) (current, depth) -> modify(current, depth, modifier);
    }

    /**
     * Modify the content of a {@link TextComponent} using a function.
     *
     * @param modifier the function to apply to the content
     * @return the tag that applies this modification
     * @apiNote Shorthand for using {@link #modify(Component, int, Function)}
     * @see #modify(Component, int, Function)
     */
    static @NotNull Tag modify(@NotNull BiFunction<Component, String, String> modifier) {
        return (Modifying) (current, depth) -> modify(current, depth, modifier);
    }

    /**
     * Recursively iterate through all {@link TextComponent} child nodes and modify the content.
     *
     * @param component the component to modify
     * @param modifier  the function to apply to the content
     * @return the modified component
     */
    static @NotNull Component modify(Component component, int depth, @NotNull Function<String, String> modifier) {
        // Modify the content of the current component if it's a TextComponent
        if (component instanceof TextComponent) {
            final TextComponent casted = (TextComponent) component;
            return casted.content(modifier.apply(casted.content()))
                .children(Collections.emptyList());
        }

        return component;
    }

    /**
     * Recursively iterate through all {@link TextComponent} child nodes and modify the content.
     *
     * @param component the component to modify
     * @param modifier  the function to apply to the content
     * @return the modified component
     */
    static Component modify(Component component, int depth, @NotNull BiFunction<Component, String, String> modifier) {
        return modify(component, depth, (string) -> modifier.apply(component, string));
    }

    /**
     * Split a string at the first {@literal :} character, into a tuple containing the content ahead of the character and after.
     *
     * @param clause the string to split, e.g. "key:value"
     * @return a tuple containing the key and value, or null if the string does not contain a {@literal :} character
     */
    static @Nullable Tuple<String, String> splitClause(@NotNull String clause) {
        final String[] parts = clause.split(":", 2);
        if (parts.length != 2)
            return null;

        return new Tuple<>(parts[0].trim(), parts[1].trim());
    }

    /**
     * Resolves a minimessage variable using the provided context and converts it to a double.
     *
     * @param arg the name of a minimessage variable to resolve
     * @param ctx the current context to use for resolving the variable
     * @return the resolved number as a double or 0 if the variable could not be parsed into a valid number
     */
    static double resolveVariableToNumber(@NotNull String arg, @NotNull Context ctx) {
        final Component resolved = ctx.deserialize(arg); // Resolve the variable
        try {
            return Double.parseDouble(PLAIN_TEXT_SERIALIZER.serialize(resolved));
        } catch (NumberFormatException e) {
            return 0D; // If no valid number could be parsed, return 0
        }
    }

    /**
     * A simple tuple class to hold a key-value pair.
     *
     * @param <A> the type of the key
     * @param <B> the type of the value
     */
    static class Tuple<A, B> {
        private final A key;
        private final B value;

        public Tuple(A key, B value) {
            this.key = key;
            this.value = value;
        }

        public A getKey() {
            return key;
        }

        public B getValue() {
            return value;
        }
    }
}
