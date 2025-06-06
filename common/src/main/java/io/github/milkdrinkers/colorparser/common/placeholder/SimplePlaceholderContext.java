package io.github.milkdrinkers.colorparser.common.placeholder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class SimplePlaceholderContext<T1, T2> implements PlaceholderContext<T1, T2> {
    private final @NotNull PlaceholderContext.Type type;
    private final @Nullable UUID player1UUID;
    private final @Nullable String player1Name;
    private final @Nullable UUID player2UUID;
    private final @Nullable String player2Name;
    private final @Nullable T1 platform1Player;
    private final @Nullable T2 platform2Player;

    public SimplePlaceholderContext(@NotNull Type type, @Nullable UUID player1UUID, @Nullable String player1Name, @Nullable UUID player2UUID, @Nullable String player2Name, @Nullable T1 platform1Player, @Nullable T2 platform2Player) {
        this.type = type;
        this.player1UUID = player1UUID;
        this.player1Name = player1Name;
        this.player2UUID = player2UUID;
        this.player2Name = player2Name;
        this.platform1Player = platform1Player;
        this.platform2Player = platform2Player;
    }

    @Override
    public @NotNull Type getType() {
        return type;
    }

    @Override
    public @NotNull Optional<UUID> getPlayer1UUID() {
        return Optional.ofNullable(player1UUID);
    }

    @Override
    public @NotNull Optional<String> getPlayer1Name() {
        return Optional.ofNullable(player1Name);
    }

    @Override
    public @NotNull Optional<UUID> getPlayer2UUID() {
        return Optional.ofNullable(player2UUID);
    }

    @Override
    public @NotNull Optional<String> getPlayer2Name() {
        return Optional.ofNullable(player2Name);
    }

    @Override
    public @NotNull Optional<T1> getPlatform1Player() {
        return Optional.ofNullable(platform1Player);
    }

    @Override
    public @NotNull Optional<T2> getPlatform2Player() {
        return Optional.ofNullable(platform2Player);
    }
}
