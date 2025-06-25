package io.github.milkdrinkers.colorparser.common.placeholder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * A class representing a context for placeholders that can be used on various platforms.
 * This context provides information about players involved in placeholder resolution.
 *
 * @param <T> The type of the platform-specific player object implementation.
 * @since 4.0.0
 */
public abstract class PlaceholderContext<T extends PlatformPlayer> {
    private final @NotNull PlaceholderContext.Type type;
    private final @Nullable T platform1Player;
    private final @Nullable T platform2Player;

    public PlaceholderContext(@NotNull Type type, @Nullable T platform1Player, @Nullable T platform2Player) {
        this.type = type;
        this.platform1Player = platform1Player;
        this.platform2Player = platform2Player;
    }

    /**
     * Enum representing the way this placeholder should resolved in this context.
     *
     * @since 4.0.0
     */
    public enum Type {
        GLOBAL,
        PLAYER,
        RELATIONAL,
    }

    /**
     * Get the type of the placeholder context
     *
     * @since 4.0.0
     */
    public @NotNull Type getType() {
        return type;
    }

    /**
     * Get the platform-specific player 1 object
     *
     * @since 4.0.0
     */
    public @NotNull Optional<T> getPlatform1Player() {
        return Optional.ofNullable(platform1Player);
    }

    /**
     * Get the platform-specific player 2 object
     *
     * @since 4.0.0
     */
    public @NotNull Optional<T> getPlatform2Player() {
        return Optional.ofNullable(platform2Player);
    }
}
