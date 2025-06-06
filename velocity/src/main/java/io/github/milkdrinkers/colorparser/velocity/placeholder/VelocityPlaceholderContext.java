package io.github.milkdrinkers.colorparser.velocity.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.SimplePlaceholderContext;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class VelocityPlaceholderContext extends SimplePlaceholderContext<Audience, Audience> {
    public VelocityPlaceholderContext(@NotNull Type type, @Nullable UUID player1UUID, @Nullable String player1Name, @Nullable UUID player2UUID, @Nullable String player2Name, @Nullable Audience platform1Player, @Nullable Audience platform2Player) {
        super(type, player1UUID, player1Name, player2UUID, player2Name, platform1Player, platform2Player);
    }
}
