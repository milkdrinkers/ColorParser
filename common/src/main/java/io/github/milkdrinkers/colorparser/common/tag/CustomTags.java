package io.github.milkdrinkers.colorparser.common.tag;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

/**
 * Tags distributed with the ColorParser library.
 *
 * @since 4.0.0
 */
public final class CustomTags {
    private CustomTags() {
    }

    private static final TagResolver ALL = TagResolver.builder()
        .resolvers(
            new UppercaseResolver(),
            new LowercaseResolver(),
            new NumberResolver(),
            new PluralResolver(),
            new ChoiceResolver(),
            new TrimResolver(),
            new LighterResolver(),
            new DarkerResolver(),
            new SmallcapsResolver()
        )
        .build();

    /**
     * Returns a TagResolver containing all custom tags provided by the ColorParser library.
     *
     * @return A TagResolver with all custom tags
     * @since 4.0.0
     */
    public static @NotNull TagResolver defaults() {
        return ALL;
    }
}
