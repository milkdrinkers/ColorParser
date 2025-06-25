package io.github.milkdrinkers.colorparser.paper.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PaperPlaceholderContext extends PlaceholderContext<PaperPlayer> {
    public PaperPlaceholderContext(@NotNull Type type, @Nullable PaperPlayer platform1Player, @Nullable PaperPlayer platform2Player) {
        super(type, platform1Player, platform2Player);
    }
}
