package io.github.milkdrinkers.colorparser.velocity.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VelocityPlaceholderContext extends PlaceholderContext<VelocityPlayer> {
    public VelocityPlaceholderContext(@NotNull Type type, @Nullable VelocityPlayer platform1Player, @Nullable VelocityPlayer platform2Player) {
        super(type, platform1Player, platform2Player);
    }
}
