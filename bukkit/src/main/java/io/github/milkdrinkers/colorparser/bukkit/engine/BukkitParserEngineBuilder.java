package io.github.milkdrinkers.colorparser.bukkit.engine;

import io.github.milkdrinkers.colorparser.bukkit.BukkitComponentBuilder;
import io.github.milkdrinkers.colorparser.bukkit.placeholder.BukkitPlaceholderContext;
import io.github.milkdrinkers.colorparser.common.engine.ParserEngineBuilder;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.jetbrains.annotations.NotNull;

public class BukkitParserEngineBuilder extends ParserEngineBuilder<BukkitComponentBuilder, BukkitParserEngineBuilder, BukkitParserEngine, BukkitPlaceholderContext> {
    private final @NotNull BukkitAudiences adventure;

    public BukkitParserEngineBuilder(@NotNull BukkitAudiences adventure) {
        super();
        this.adventure = adventure;
    }

    @Override
    public @NotNull BukkitParserEngine build() {
        super.buildInternal();

        return new BukkitParserEngine(
            this.miniMessage,
            this.parseLegacy,
            adventure
        );
    }
}
