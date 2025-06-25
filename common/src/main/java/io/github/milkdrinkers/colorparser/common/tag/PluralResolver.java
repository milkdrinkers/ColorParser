package io.github.milkdrinkers.colorparser.common.tag;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.milkdrinkers.colorparser.common.tag.TagUtil.*;

/**
 * This tag returns a value based on a numeric variable and a set of plural conditions.
 * </br></br>
 * Example usage:</br>
 * {@code <plural:variable:'clauses'>}</br>
 * {@code <plural:apple_count:'one:apple|other:apples'>}</br>
 * {@code <plural:user_points:'one:point|other:points'>}</br>
 * {@code <plural:user_points:'one:point|3-5:points|other:points'>}</br>
 * {@code <plural:user_points:'0:items|1:item|other:items'>}</br>
 * {@code <plural:user_points:'0:zero|1:one|2:two|3-5:few|other:many'>}</br>
 * </br>
 * The tag requires two arguments:</br>
 * - the name of a minimessage variable which can be parsed into a number</br>
 * - a string of clauses, where each clause is a condition followed by a colon and the corresponding value, separated by pipes ({@literal |})</br>
 *
 * @implNote The conditions can be "one", "other", or a range (e.g., "1-3"). If the count matches "one", it returns the singular form; otherwise, it returns the plural form. The tag will return the result of the first matching clause or default to an empty component if no match is found.
 * @since 4.0.0
 */
public class PluralResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue args, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        final double countVariable = resolveVariableToNumber(args.popOr("Missing variable argument!").value(), ctx);
        final String clausesArg = args.popOr("Missing clauses argument!").value();

        for (final String clause : clausesArg.split("\\|")) {
            final Tuple<String, String> parts = splitClause(clause);
            if (parts == null)
                continue;

            try {
                if (matchesPluralCondition(parts.getKey(), countVariable)) {
                    return Tag.inserting(ctx.deserialize(parts.getValue()));
                }
            } catch (NullPointerException | NumberFormatException e) {
                throw ctx.newException("Invalid clause format!", e, args);
            }
        }

        return Tag.inserting(Component.empty());
    }

    private static boolean matchesPluralCondition(final String condition, final double count) throws NullPointerException, NumberFormatException {
        final String cleanCondition = condition.trim().toLowerCase();
        switch (cleanCondition) {
            case "one":
                return count == 1;
            case "other":
                return count != 1;
            default:
                if (cleanCondition.contains("-")) {
                    final String[] range = cleanCondition.split("-");
                    final double min = Double.parseDouble(range[0]);
                    final double max = Double.parseDouble(range[1]);
                    return count >= min && count <= max;
                } else {
                    return count == Double.parseDouble(condition);
                }
        }
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("plural");
    }
}
