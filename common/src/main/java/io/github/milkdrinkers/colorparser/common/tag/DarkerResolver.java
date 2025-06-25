package io.github.milkdrinkers.colorparser.common.tag;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Modifying;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Collections;
import java.util.function.Function;

/**
 * This tag darkens the color of the text by darkening the RGB values.
 * </br></br>
 * Example usage:</br>
 * {@code <darker>Some text</darker>}</br>
 *
 * @implNote The tag will apply the {@link Color#darker()} method to the current text color, making it darker.
 * @since 4.0.0
 */
public class DarkerResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue arguments, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        return modifyColor(Color::darker);
    }

    protected static @NotNull Modifying modifyColor(@NotNull Function<Color, Color> modifier) {
        return (component, depth) -> {
            // Component has no color so set children to empty list
            final TextColor textColor = component.color();
            if (textColor == null)
                return component.children(Collections.emptyList());

            final Color color = modifier.apply(new Color(textColor.value()));

            return component
                .children(Collections.emptyList())
                .color(TextColor.color(color.getRGB()));
        };
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("darker") || name.equals("darken");
    }
}
