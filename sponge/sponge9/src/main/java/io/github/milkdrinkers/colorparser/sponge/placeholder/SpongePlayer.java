package io.github.milkdrinkers.colorparser.sponge.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.PlatformPlayer;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.profile.GameProfile;

public class SpongePlayer extends PlatformPlayer {
    public SpongePlayer(@NotNull GameProfile user) {
        super(user.uniqueId(), user.name().orElse(""));
    }

    /**
     * Get the audience associated with this player instance.
     *
     * @return The audience object representing the player.
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public @NotNull Audience getAudience() {
        return Sponge.server().player(getUuid()).get();
    }

    /**
     * Check if the player is online.
     *
     * @return true if the player is online, false otherwise.
     */
    public boolean isOnline() {
        return Sponge.server().player(getUuid()).isPresent();
    }
}
