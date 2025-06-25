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
 * This tag returns a value based on a numeric variable and a set of numeric ranges.
 * </br></br>
 * Example usage:</br>
 * {@code <choice:variable:'clauses'>}</br>
 * {@code <choice:some_name:'0-1:Low|2-5:Medium|6-31:High'>}</br>
 * {@code <choice:score:'0-2:sometext|3-5:othertext'>}</br>
 * </br>
 * The tag requires two arguments:</br>
 * - the name of a minimessage variable which can be parsed into a number</br>
 * - a string of clauses, where each clause is a numeric range followed by a colon and the corresponding value, separated by pipes ({@literal |})</br>
 *
 * @implNote The tag will return the result of the first matching clause or default to an empty component if no match is found.
 * @since 4.0.0
 */
public class ChoiceResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue args, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        final double keyVariable = resolveVariableToNumber(args.popOr("Missing variable argument!").value(), ctx);
        final String clausesArg = args.popOr("Missing clauses argument!").value();

        for (final String clause : clausesArg.split("\\|")) {
            final Tuple<String, String> parts = splitClause(clause);
            if (parts == null)
                continue;

            try {
                if (matchesChoicePattern(parts.getKey(), keyVariable)) {
                    return Tag.inserting(ctx.deserialize(parts.getValue()));
                }
            } catch (NullPointerException | NumberFormatException e) {
                throw ctx.newException("Invalid clause format!", e, args);
            }
        }

        return Tag.inserting(Component.empty());
    }

    private static boolean matchesChoicePattern(final String pattern, final double count) throws NullPointerException, NumberFormatException {
        final String cleanPattern = pattern.trim().toLowerCase();
        if (cleanPattern.contains("-")) {
            final String[] range = cleanPattern.split("-");
            final double min = Double.parseDouble(range[0]);
            final double max = Double.parseDouble(range[1]);
            return count >= min && count <= max;
        } else {
            return count == Double.parseDouble(cleanPattern);
        }
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("choice");
    }
}
