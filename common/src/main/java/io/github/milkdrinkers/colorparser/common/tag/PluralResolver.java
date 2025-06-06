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

// Plural Resolver: <plural:variable 'clauses'>
// Example: <plural:apples 'one:apple|other:apples'>
// Example: <plural:apples:'one:apple|other:apples'>
public class PluralResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue args, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        // 1. Resolve the variable (e.g., "apples") to a number
        final double countVariable = resolveVariableToNumber(args.popOr("Missing variable argument!").value(), ctx);

        // 2. Parse clauses (e.g., "one:apple|other:apples")
        final String clausesArg = args.popOr("Missing clauses argument!").value();

        // 3. Find the matching clause
        for (String clause : clausesArg.split("\\|")) {
            final TagUtil.Tuple parts = splitClause(clause);
            if (parts == null) continue;

            if (matchesPluralCondition(parts.key, countVariable)) {
                return Tag.inserting(ctx.deserialize(parts.value));
            }
        }

        return Tag.inserting(Component.empty());
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("plural");
    }
}
