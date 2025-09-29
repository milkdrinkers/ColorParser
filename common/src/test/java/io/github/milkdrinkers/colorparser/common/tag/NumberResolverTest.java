package io.github.milkdrinkers.colorparser.common.tag;

import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.util.TestUtil;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.format.NamedTextColor.AQUA;

@DisplayName("Test - NumberResolver")
public class NumberResolverTest {
    private MockParserEngine engine;

    @BeforeEach
    void setUp() {
        engine = MockParserEngine.builder().parseLegacy(true).build();
    }

    @Test
    @DisplayName("Number with Comma Separator")
    public void testNumberWithCommaSeparator() {
        final String test = "<number:<balance>:'#,##0.00'>";

        final Component actual = engine.parse(test).with("balance", "1234.5").build();
        final Component expected = Component.text("1,234.50");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Number with Two Decimal Places")
    public void testNumberWithTwoDecimals() {
        final String test = "<number:<amount>:'#,##0.00'>";

        final Component actual = engine.parse(test).with("amount", "999.999").build();
        final Component expected = Component.text("1,000.00");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Number with One Decimal Place")
    public void testNumberWithOneDecimal() {
        final String test = "<number:<value>:'#,##0.0'>";

        final Component actual = engine.parse(test).with("value", "1234.567").build();
        final Component expected = Component.text("1,234.6");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Number No Decimal Places")
    public void testNumberNoDecimals() {
        final String test = "<number:<score>:'#,##0'>";

        final Component actual = engine.parse(test).with("score", "12345.67").build();
        final Component expected = Component.text("12,346");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Number with Color")
    public void testNumberWithColor() {
        final String test = "<aqua><number:<balance>:'#,##0.00'></aqua>";

        final Component actual = engine.parse(test).with("balance", "1234.5").build();
        final Component expected = Component.text("1,234.50").color(AQUA);

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Number with Zero Value")
    public void testNumberWithZero() {
        final String test = "<number:<value>:'#,##0.00'>";

        final Component actual = engine.parse(test).with("value", "0").build();
        final Component expected = Component.text("0.00");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Number with Negative Value")
    public void testNumberWithNegative() {
        final String test = "<number:<value>:'#,##0.00'>";

        final Component actual = engine.parse(test).with("value", "-1234.56").build();
        final Component expected = Component.text("-1,234.56");

        TestUtil.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Number with Large Value")
    public void testNumberWithLargeValue() {
        final String test = "<number:<value>:'#,##0'>";

        final Component actual = engine.parse(test).with("value", "1000000").build();
        final Component expected = Component.text("1,000,000");

        TestUtil.assertEquals(expected, actual);
    }
}