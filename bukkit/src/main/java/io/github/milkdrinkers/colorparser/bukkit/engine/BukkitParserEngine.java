package io.github.milkdrinkers.colorparser.bukkit.engine;

import io.github.milkdrinkers.colorparser.bukkit.BukkitComponentBuilder;
import io.github.milkdrinkers.colorparser.bukkit.placeholder.BukkitPlaceholderContext;
import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class BukkitParserEngine extends ParserEngine<BukkitComponentBuilder, BukkitParserEngineBuilder, BukkitParserEngine, BukkitPlaceholderContext> {
    private final @NotNull BukkitAudiences adventure;

    /**
     * Creates a new ParserEngine with the given settings.
     *
     * @param miniMessage The MiniMessage instance
     * @param parseLegacy Whether to parse legacy format or not
     */
    protected BukkitParserEngine(@NotNull MiniMessage miniMessage, boolean parseLegacy, @NotNull BukkitAudiences adventure) {
        super(miniMessage, parseLegacy);
        this.adventure = adventure;
    }

    @Override
    public @NotNull BukkitComponentBuilder parse(@NotNull String content) {
        return new BukkitComponentBuilder(this, content, adventure);
    }

    @Override
    public @NotNull BukkitComponentBuilder parse(@NotNull ComponentLike component) {
        return new BukkitComponentBuilder(this, getMiniMessage().serialize(component.asComponent()), adventure);
    }
}
