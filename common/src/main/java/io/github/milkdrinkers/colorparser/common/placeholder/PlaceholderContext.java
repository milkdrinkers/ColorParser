package io.github.milkdrinkers.colorparser.common.placeholder;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface PlaceholderContext<T1, T2> {
    enum Type {
        GLOBAL,
        PLAYER,
        RELATIONAL,
    }

    /**
     * Get the type of the placeholder context
     */
    @NotNull Type getType();

    /**
     * Get the player 1 UUID if available
     */
    Optional<UUID> getPlayer1UUID();

    /**
     * Get the player 1 name if available
     */
    Optional<String> getPlayer1Name();

    /**
     * Get the player 2 UUID if available
     */
    Optional<UUID> getPlayer2UUID();

    /**
     * Get the player 2 name if available
     */
    Optional<String> getPlayer2Name();

    /**
     * Get the platform-specific player 1 object
     */
    Optional<T1> getPlatform1Player();

    /**
     * Get the platform-specific player 2 object
     */
    Optional<T2> getPlatform2Player();
}
