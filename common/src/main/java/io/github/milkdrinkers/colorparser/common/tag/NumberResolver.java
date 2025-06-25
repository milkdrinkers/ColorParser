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

/**
 * This tag is used to format a numeric variable according to a specified pattern.
 * </br></br>
 * Example usage:</br>
 * {@code <number:balance:'#,##0.00'>}</br>
 * {@code <number:some_name:'#,#0.0'>}</br>
 * </br>
 * The tag requires two arguments:</br>
 * - the name of a minimessage variable which can be parsed into a number</br>
 * - a pattern string, see {@link DecimalFormat} for details on the pattern string.</br>
 *
 * @implNote The pattern follows Java's DecimalFormat patterns.
 * @see DecimalFormat
 * @since 4.0.0
 */
public class NumberResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue args, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        final double valueVariable = resolveVariableToNumber(args.popOr("Missing variable argument!").value(), ctx);
        final String pattern = args.popOr("Missing pattern argument!").value();

        try {
            final DecimalFormat df = new DecimalFormat(pattern);
            return Tag.inserting(Component.text(df.format(valueVariable)));
        } catch (NullPointerException | IllegalArgumentException | ArithmeticException e) {
            throw ctx.newException("Invalid pattern argument!", e, args);
        }
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("number");
    }
}
