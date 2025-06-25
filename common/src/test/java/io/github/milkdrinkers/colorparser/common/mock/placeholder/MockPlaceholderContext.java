package io.github.milkdrinkers.colorparser.common.mock.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MockPlaceholderContext extends PlaceholderContext<MockPlayer> {
    public MockPlaceholderContext(@NotNull Type type, @Nullable MockPlayer platform1Player, @Nullable MockPlayer platform2Player) {
        super(type, platform1Player, platform2Player);
    }
}
