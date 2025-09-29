package io.github.milkdrinkers.colorparser.common.tag;

import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.util.TestUtil;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.format.NamedTextColor.GREEN;

@DisplayName("Test - SmallcapsResolver")
public class SmallcapsResolverTest {
    private MockParserEngine engine;

    @BeforeEach
    void setUp() {
        engine = MockParserEngine.builder().parseLegacy(true).build();
    }

    @Test
    @DisplayName("Simple Smallcaps")
    public void testSimpleSmallcaps() {
        final String test = "<smallcaps>hello</smallcaps>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("ʜᴇʟʟᴏ");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Smallcaps with Uppercase")
    public void testSmallcapsWithUppercase() {
        final String test = "<smallcaps>HELLO</smallcaps>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("ʜᴇʟʟᴏ");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Smallcaps Alias - small")
    public void testSmallcapsAlias() {
        final String test = "<small>hello</small>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("ʜᴇʟʟᴏ");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Smallcaps with Color")
    public void testSmallcapsWithColor() {
        final String test = "<green><smallcaps>hello world</smallcaps></green>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("ʜᴇʟʟᴏ ᴡᴏʀʟᴅ").color(GREEN);

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Smallcaps with Numbers")
    public void testSmallcapsWithNumbers() {
        final String test = "<smallcaps>hello123</smallcaps>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("ʜᴇʟʟᴏ123");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Smallcaps with Special Characters")
    public void testSmallcapsWithSpecialCharacters() {
        final String test = "<smallcaps>hello world!</smallcaps>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("ʜᴇʟʟᴏ ᴡᴏʀʟᴅ!");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Smallcaps All Letters")
    public void testSmallcapsAllLetters() {
        final String test = "<smallcaps>abcdefghijklmnopqrstuvwxyz</smallcaps>";

        final Component actual = engine.parse(test).build();
        final Component expected = Component.text("ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ");

        TestUtil.assertEquals(expected, actual);
    }
}