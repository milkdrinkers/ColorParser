package io.github.milkdrinkers.colorparser.common.tag;

import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This tag converts the content of a component to uppercase.
 * </br></br>
 * Example usage:</br>
 * {@code <uppercase>Some Text</uppercase>}</br>
 * </br>
 *
 * @implNote The tag will convert the content of the component to uppercase.
 * @since 4.0.0
 */
public class UppercaseResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue arguments, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        return TagUtil.modify((component, s) -> s.toUpperCase());
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("uppercase") || name.equals("upper");
    }
}
