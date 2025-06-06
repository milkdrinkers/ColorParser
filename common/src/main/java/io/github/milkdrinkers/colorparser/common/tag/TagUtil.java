package io.github.milkdrinkers.colorparser.common.tag;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Modifying;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class TagUtil {
    /**
     * Modify the content of a {@link TextComponent} using a function.
     *
     * @param modifier the function to apply to the content
     * @return the tag that applies this modification
     * @apiNote Shorthand for using {@link #modify(Component, Function)}
     * @see #modify(Component, Function)
     */
    public static @NotNull Tag modify(@NotNull Function<String, String> modifier) {
        return (Modifying) (current, depth) -> modify(current, modifier);
    }

    /**
     * Modify the content of a {@link TextComponent} using a function.
     *
     * @param modifier the function to apply to the content
     * @return the tag that applies this modification
     * @apiNote Shorthand for using {@link #modify(Component, Function)}
     * @see #modify(Component, Function)
     */
    public static @NotNull Tag modify(@NotNull BiFunction<Component, String, String> modifier) {
        return (Modifying) (current, depth) -> modify(current, modifier);
    }

    /**
     * Recursively iterate through all {@link TextComponent} child nodes and modify the content.
     *
     * @param component the component to modify
     * @param modifier  the function to apply to the content
     * @return the modified component
     */
    public static @NotNull Component modify(Component component, @NotNull Function<String, String> modifier) {
        // Recursively iterate through children and modify them
        component = component.children(
            component.children()
                .stream()
                .map(child -> modify(child, modifier))
                .collect(Collectors.toList())
        );

        // Modify the content of the current component if it's a TextComponent
        if (component instanceof TextComponent) {
            final TextComponent casted = (TextComponent) component;
            component = casted.content(modifier.apply(casted.content()));
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
    public static Component modify(Component component, @NotNull BiFunction<Component, String, String> modifier) {
        return modify(component, (string) -> modifier.apply(component, string));
    }

    static @Nullable Tuple splitClause(@NotNull String clause) {
        final String[] parts = clause.split(":", 2);
        if (parts.length != 2) return null;

        return new Tuple(parts[0].trim(), parts[1].trim());
    }

    static double resolveVariableToNumber(@NotNull String variableName, @NotNull Context ctx) {
        // Resolve the variable (e.g., "<balance>") to a plain text number
        Component resolved = ctx.deserialize(variableName);
        String plain = PlainTextComponentSerializer.plainText().serialize(resolved);
        return Double.parseDouble(plain);
    }

    static boolean matchesPluralCondition(String condition, double count) {
        condition = condition.trim().toLowerCase();
        switch (condition) {
            case "one":
                return count == 1;
            case "other":
                return count != 1;
            default:
                if (condition.contains("-")) {
                    String[] range = condition.split("-");
                    double min = Double.parseDouble(range[0]);
                    double max = Double.parseDouble(range[1]);
                    return count >= min && count <= max;
                }
                return count == Double.parseDouble(condition);
        }
    }

    static boolean matchesChoicePattern(String pattern, double key) {
        pattern = pattern.trim();
        if (pattern.contains("-")) {
            String[] range = pattern.split("-");
            double min = Double.parseDouble(range[0]);
            double max = Double.parseDouble(range[1]);
            return key >= min && key <= max;
        } else {
            return key == Double.parseDouble(pattern);
        }
    }

    static class Tuple {
        public final String key;
        public final String value;

        public Tuple(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
