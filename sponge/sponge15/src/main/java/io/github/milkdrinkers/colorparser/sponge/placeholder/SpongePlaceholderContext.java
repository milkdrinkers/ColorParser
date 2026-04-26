package io.github.milkdrinkers.colorparser.sponge.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpongePlaceholderContext extends PlaceholderContext<SpongePlayer> {
    public SpongePlaceholderContext(@NotNull Type type, @Nullable SpongePlayer platform1Player, @Nullable SpongePlayer platform2Player) {
        super(type, platform1Player, platform2Player);
    }
}
