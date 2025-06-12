package io.github.milkdrinkers.colorparser.common.mock.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.SimplePlaceholderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MockPlaceholderContext extends SimplePlaceholderContext<Object, Object> {
    public MockPlaceholderContext(@NotNull Type type, @Nullable UUID player1UUID, @Nullable String player1Name, @Nullable UUID player2UUID, @Nullable String player2Name, @Nullable Object platform1Player, @Nullable Object platform2Player) {
        super(type, player1UUID, player1Name, player2UUID, player2Name, platform1Player, platform2Player);
    }
}
