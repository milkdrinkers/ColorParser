package io.github.milkdrinkers.colorparser.bukkit.placeholder;

import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitPlaceholderContext extends PlaceholderContext<BukkitPlayer> {
    private final @NotNull BukkitAudiences adventure;

    public BukkitPlaceholderContext(@NotNull Type type, @Nullable BukkitPlayer platform1Player, @Nullable BukkitPlayer platform2Player, @NotNull BukkitAudiences adventure) {
        super(type, platform1Player, platform2Player);
        this.adventure = adventure;
    }

    @NotNull
    public BukkitAudiences getAdventure() {
        return adventure;
    }
}
