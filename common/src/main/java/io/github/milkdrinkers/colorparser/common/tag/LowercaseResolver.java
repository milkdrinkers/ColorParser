package io.github.milkdrinkers.colorparser.common.tag;

import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LowercaseResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue arguments, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        return TagUtil.modify((component, s) -> s.toLowerCase());
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("lowercase") || name.equals("lower");
    }
}
