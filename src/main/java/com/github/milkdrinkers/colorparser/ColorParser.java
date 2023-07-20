package com.github.milkdrinkers.colorparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Builder Util for Adventure Messages.
 */
public class ColorParser {

    private static final MiniMessage mm = MiniMessage.miniMessage();
    private static final Pattern legacyRegex = Pattern.compile("[§&][0-9a-fk-or]");
    private static final Map<String, String> legacyToMiniMessage = Map.ofEntries(
        Map.entry("§0", "<black>"),
        Map.entry("§1", "<dark_blue>"),
        Map.entry("§2", "<dark_green>"),
        Map.entry("§3", "<dark_aqua>"),
        Map.entry("§4", "<dark_red>"),
        Map.entry("§5", "<dark_purple>"),
        Map.entry("§6", "<gold>"),
        Map.entry("§7", "<gray>"),
        Map.entry("§8", "<dark_gray>"),
        Map.entry("§9", "<blue>"),
        Map.entry("§a", "<green>"),
        Map.entry("§b", "<aqua>"),
        Map.entry("§c", "<red>"),
        Map.entry("§d", "<light_purple>"),
        Map.entry("§e", "<yellow>"),
        Map.entry("§f", "<white>"),
        Map.entry("§k", "<obfuscated>"),
        Map.entry("§l", "<bold>"),
        Map.entry("§m", "<strikethrough>"),
        Map.entry("§n", "<underlined>"),
        Map.entry("§o", "<italic>"),
        Map.entry("§r", "<reset>"),
        Map.entry("&0", "<black>"),
        Map.entry("&1", "<dark_blue>"),
        Map.entry("&2", "<dark_green>"),
        Map.entry("&3", "<dark_aqua>"),
        Map.entry("&4", "<dark_red>"),
        Map.entry("&5", "<dark_purple>"),
        Map.entry("&6", "<gold>"),
        Map.entry("&7", "<gray>"),
        Map.entry("&8", "<dark_gray>"),
        Map.entry("&9", "<blue>"),
        Map.entry("&a", "<green>"),
        Map.entry("&b", "<aqua>"),
        Map.entry("&c", "<red>"),
        Map.entry("&d", "<light_purple>"),
        Map.entry("&e", "<yellow>"),
        Map.entry("&f", "<white>"),
        Map.entry("&k", "<obfuscated>"),
        Map.entry("&l", "<bold>"),
        Map.entry("&m", "<strikethrough>"),
        Map.entry("&n", "<underlined>"),
        Map.entry("&o", "<italic>"),
        Map.entry("&r", "<reset>")
    );
    private final List<TagResolver> minimessagePlaceholders = new ArrayList<>(); // Store MiniMessage placeholders to be applied
    private String text;

    /**
     * Instantiates a new Component parser.
     *
     * @param text the text
     */
    public ColorParser(String text) {
        this.text = text;
    }

    /**
     * Text component parser.
     *
     * @param text the text
     * @return the component parser
     */
    public static ColorParser text(String text) {
        return new ColorParser(text);
    }

    /**
     * To component component.
     *
     * @return the component
     */
    public @NotNull Component build() {
        return mm.deserialize(this.text, this.minimessagePlaceholders.toArray(new TagResolver[0]));
    }

    /**
     * Parse legacy color codes and formatting, including <code>{@literal &}</code> and
     * <code>{@literal §}</code> into their minimessage equivalents.
     *
     * @return the component parser
     */
    public @NotNull ColorParser parseLegacy() {
        String textParsed = this.text;
        final Matcher matcher = legacyRegex.matcher(textParsed);

        while (matcher.find()) {
            final String match = matcher.group();
            textParsed = textParsed.replace(match, legacyToMiniMessage.getOrDefault(match, match));
        }

        this.text = textParsed;

        return this;
    }

    /**
     * Parse all PAPI placeholders.
     *
     * @param p the player to parse for
     * @return the component parser
     */
    public @NotNull ColorParser parsePAPIPlaceholders(Player p) {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.text = PlaceholderAPI.setPlaceholders(p.getPlayer(), this.text);
        }

        return this;
    }


    /**
     * Parse a minimessage placeholder.
     *
     * @param placeholder the placeholder name like <code>player_name</code>, in config
     *                    <code>{@literal <player_name>}</code>
     * @param value       the value like <code>{@literal "<gold><bold>darksaid98"}</code>
     * @return the component parser
     */
    public @NotNull ColorParser parseMinimessagePlaceholder(String placeholder, String value) {
        this.minimessagePlaceholders.add(
            Placeholder.component(
                placeholder,
                new ColorParser(value).parseLegacy().build()
            )
        );

        return this;
    }

    /**
     * Parse a minimessage placeholder.
     *
     * @param placeholder the placeholder name like <code>player_name</code>, in config
     *                    <code>{@literal <player_name>}</code>
     * @param value       a component
     * @return the component parser
     */
    public @NotNull ColorParser parseMinimessagePlaceholder(String placeholder, Component value) {
        this.minimessagePlaceholders.add(
            Placeholder.component(
                placeholder,
                value
            )
        );

        return this;
    }

    /**
     * Parse a string placeholder.
     *
     * @param placeholder the placeholder like <code>{@literal %player_name%}</code>
     * @param value       the value like <code>{@literal &6&ldarksaid98}</code>
     * @return the component parser
     */
    public @NotNull ColorParser parseStringPlaceholder(String placeholder, String value) {
        this.text = this.text.replaceAll(placeholder, value);

        return this;
    }

    public String toString() {
        return this.text;
    }
}