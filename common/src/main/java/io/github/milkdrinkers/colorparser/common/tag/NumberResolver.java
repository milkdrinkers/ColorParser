package io.github.milkdrinkers.colorparser.common.tag;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;

import static io.github.milkdrinkers.colorparser.common.tag.TagUtil.resolveVariableToNumber;

// Number Resolver: <number:variable 'format'>
// Example: <number:balance:'#,##0.00'>
public class NumberResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue args, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        // 1. Resolve the variable (e.g., "balance") to a number
        final double valueVariable = resolveVariableToNumber(args.popOr("Missing variable argument!").value(), ctx);

        // 2. Parse the format (e.g., "#,##0.00")
        final String format = args.popOr("Missing format argument!").value();

        // 3. Format the number
        final DecimalFormat df = new DecimalFormat(format);
        return Tag.inserting(Component.text(df.format(valueVariable)));
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("number");
    }
}
