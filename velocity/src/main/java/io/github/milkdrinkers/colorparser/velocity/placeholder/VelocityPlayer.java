package io.github.milkdrinkers.colorparser.velocity.placeholder;

import com.velocitypowered.api.proxy.Player;
import io.github.milkdrinkers.colorparser.common.placeholder.PlatformPlayer;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public class VelocityPlayer extends PlatformPlayer {
    @NotNull
    private final Audience audience;

    public VelocityPlayer(@NotNull Player player) {
        super(player.getUniqueId(), player.getUsername());
        this.audience = player;
    }

    /**
     * Get the audience associated with this player instance.
     *
     * @return The audience object representing the player.
     */
    public @NotNull Audience getAudience() {
        return audience;
    }
}
