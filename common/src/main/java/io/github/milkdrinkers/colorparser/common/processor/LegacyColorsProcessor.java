package io.github.milkdrinkers.colorparser.common.processor;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace legacy color codes with MiniMessage-compatible color codes
 *
 * @since 4.0.0
 */
public class LegacyColorsProcessor implements Processor {
    private static final Pattern LEGACY_REGEX = Pattern.compile("[§&][0-9a-fk-or]");
    private static final @NotNull Map<String, String> LEGACY_TO_MINIMESSAGE;

    static {
        final Map<String, String> map = new HashMap<>();
        map.put("§0", "<black>");
        map.put("§1", "<dark_blue>");
        map.put("§2", "<dark_green>");
        map.put("§3", "<dark_aqua>");
        map.put("§4", "<dark_red>");
        map.put("§5", "<dark_purple>");
        map.put("§6", "<gold>");
        map.put("§7", "<gray>");
        map.put("§8", "<dark_gray>");
        map.put("§9", "<blue>");
        map.put("§a", "<green>");
        map.put("§b", "<aqua>");
        map.put("§c", "<red>");
        map.put("§d", "<light_purple>");
        map.put("§e", "<yellow>");
        map.put("§f", "<white>");
        map.put("§k", "<obfuscated>");
        map.put("§l", "<bold>");
        map.put("§m", "<strikethrough>");
        map.put("§n", "<underlined>");
        map.put("§o", "<italic>");
        map.put("§r", "<reset>");
        map.put("&0", "<black>");
        map.put("&1", "<dark_blue>");
        map.put("&2", "<dark_green>");
        map.put("&3", "<dark_aqua>");
        map.put("&4", "<dark_red>");
        map.put("&5", "<dark_purple>");
        map.put("&6", "<gold>");
        map.put("&7", "<gray>");
        map.put("&8", "<dark_gray>");
        map.put("&9", "<blue>");
        map.put("&a", "<green>");
        map.put("&b", "<aqua>");
        map.put("&c", "<red>");
        map.put("&d", "<light_purple>");
        map.put("&e", "<yellow>");
        map.put("&f", "<white>");
        map.put("&k", "<obfuscated>");
        map.put("&l", "<bold>");
        map.put("&m", "<strikethrough>");
        map.put("&n", "<underlined>");
        map.put("&o", "<italic>");
        map.put("&r", "<reset>");
        LEGACY_TO_MINIMESSAGE = Collections.unmodifiableMap(map);
    }

    @Override
    public @NotNull String process(@NotNull String input) {
        final @NotNull Matcher matcher = LEGACY_REGEX.matcher(input);

        while (matcher.find()) {
            final String match = matcher.group();
            input = input.replace(match, LEGACY_TO_MINIMESSAGE.getOrDefault(match, match));
        }

        return input;
    }

    @Override
    public @NotNull String getName() {
        return "legacycolorcodes";
    }
}
