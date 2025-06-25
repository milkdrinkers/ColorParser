package io.github.milkdrinkers.colorparser.common.mock.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.PlatformPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MockPlayer extends PlatformPlayer {
    public MockPlayer(@NotNull UUID uuid, @NotNull String name) {
        super(uuid, name);
    }
}
