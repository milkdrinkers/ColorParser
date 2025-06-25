package io.github.milkdrinkers.colorparser.bukkit.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.PlatformPlayer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class BukkitPlayer extends PlatformPlayer {
    @NotNull
    private final OfflinePlayer offlinePlayer;

    public BukkitPlayer(@NotNull OfflinePlayer offlinePlayer) {
        super(offlinePlayer.getUniqueId(), offlinePlayer.getName() != null ? offlinePlayer.getName() : "");
        this.offlinePlayer = offlinePlayer;
    }

    /**
     * Get the offline player associated with this player instance.
     *
     * @return The OfflinePlayer object representing the player.
     */
    public @NotNull OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }
}
