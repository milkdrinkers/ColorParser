package io.github.milkdrinkers.colorparser.bukkit.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.SimplePlaceholderContext;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BukkitPlaceholderContext extends SimplePlaceholderContext<OfflinePlayer, Player> {
    private final @NotNull BukkitAudiences adventure;

    public BukkitPlaceholderContext(@NotNull Type type, @Nullable UUID player1UUID, @Nullable String player1Name, @Nullable UUID player2UUID, @Nullable String player2Name, @Nullable Player platform1Player, @Nullable Player platform2Player, @NotNull BukkitAudiences adventure) {
        super(type, player1UUID, player1Name, player2UUID, player2Name, platform1Player, platform2Player);
        this.adventure = adventure;
    }

    public BukkitPlaceholderContext(@NotNull Type type, @Nullable UUID player1UUID, @Nullable String player1Name, @Nullable UUID player2UUID, @Nullable String player2Name, @Nullable OfflinePlayer platform1Player, @Nullable Player platform2Player, @NotNull BukkitAudiences adventure) {
        super(type, player1UUID, player1Name, player2UUID, player2Name, platform1Player, platform2Player);
        this.adventure = adventure;
    }

    @NotNull
    public BukkitAudiences getAdventure() {
        return adventure;
    }
}
