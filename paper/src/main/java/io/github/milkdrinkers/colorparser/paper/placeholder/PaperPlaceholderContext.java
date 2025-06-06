package io.github.milkdrinkers.colorparser.paper.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.SimplePlaceholderContext;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PaperPlaceholderContext extends SimplePlaceholderContext<OfflinePlayer, Player> {
    public PaperPlaceholderContext(@NotNull Type type, @Nullable UUID player1UUID, @Nullable String player1Name, @Nullable UUID player2UUID, @Nullable String player2Name, @Nullable Player platform1Player, @Nullable Player platform2Player) {
        super(type, player1UUID, player1Name, player2UUID, player2Name, platform1Player, platform2Player);
    }

    public PaperPlaceholderContext(@NotNull Type type, @Nullable UUID player1UUID, @Nullable String player1Name, @Nullable UUID player2UUID, @Nullable String player2Name, @Nullable OfflinePlayer platform1Player, @Nullable Player platform2Player) {
        super(type, player1UUID, player1Name, player2UUID, player2Name, platform1Player, platform2Player);
    }
}
