package com.github.milkdrinkers.colorparser;

import net.kyori.adventure.text.minimessage.MiniMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A singleton to hold precompiled
 */
public class ColorHolder {
    private static ColorHolder instance = null;

    protected static ColorHolder getInstance() {
//        MiniMessage.builder()
//            .preProcessor(s -> s.toLowerCase())
//            .build();
//        MiniMessage.miniMessage().
        if (instance == null)
            instance = new ColorHolder();

        return instance;
    }

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final Pattern LEGACY_REGEX = Pattern.compile("[§&][0-9a-fk-or]");
    private static final Map<String, String> LEGACY_TO_MINI_MESSAGE = initializeMiniMessageMap();

    private static Map<String, String> initializeMiniMessageMap() {
        Map<String,String> map = new HashMap<>();
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
        return map;
    }

    protected MiniMessage getMiniMessage() {
        return MINI_MESSAGE;
    }

    protected Pattern getLegacyRegex() {
        return LEGACY_REGEX;
    }

    protected Map<String, String> getLegacyToMiniMessage() {
        return LEGACY_TO_MINI_MESSAGE;
    }
}
