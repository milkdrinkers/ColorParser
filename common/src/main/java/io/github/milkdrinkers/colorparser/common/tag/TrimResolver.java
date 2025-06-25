package io.github.milkdrinkers.colorparser.common.tag;

import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This tag trims whitespace from the beginning and end of the input string.
 * </br></br>
 * Example usage:</br>
 * {@code <trim>   Hello World!   </trim>}</br>
 *
 * @implNote The tag will remove any leading or trailing whitespace from the components contained within the trim.
 * @see String#trim()
 * @since 4.0.0
 */
public class TrimResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue arguments, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        return TagUtil.modify(String::trim);
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("trim");
    }
}
