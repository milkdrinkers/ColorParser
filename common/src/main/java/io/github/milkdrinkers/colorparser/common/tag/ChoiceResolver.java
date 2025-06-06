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

// Choice Resolver: <choice:variable 'clauses'>
// Example: <choice:score '0-2:Low|3-5:Medium'>

/**
 * Creates a resolver for the "choice" tag.
 *
 * @implNote This resolver is similar to the "plural" resolver, but it uses numeric ranges instead of conditions.
 */
public class ChoiceResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue args, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        // 1. Resolve the variable (e.g., "score") to a number
        final double keyVariable = resolveVariableToNumber(args.popOr("Missing variable argument!").value(), ctx);

        // 2. Parse clauses (e.g., "0-2:Low|3-5:Medium")
        final String clausesArg = args.popOr("Missing clauses argument!").value();

        // 3. Find the matching clause
        for (String clause : clausesArg.split("\\|")) {
            final TagUtil.Tuple parts = splitClause(clause);
            if (parts == null) continue;

            if (matchesChoicePattern(parts.key, keyVariable)) {
                return Tag.inserting(ctx.deserialize(parts.value));
            }
        }

        return Tag.inserting(Component.empty());
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("choice");
    }
}
