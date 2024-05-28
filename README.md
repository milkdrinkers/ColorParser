
<h1 align="center">ColorParser</h1>
<p align="center">
    <img src="https://img.shields.io/github/license/milkdrinkers/ColorParser?color=blue&style=flat-square" alt="license"/>
    <a href="https://maven.athyrium.eu/#/releases/com/github/milkdrinkers/colorparser"><img alt="Maven metadata URL" src="https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.athyrium.eu%2Freleases%2Fcom%2Fgithub%2Fmilkdrinkers%2Fcolorparser%2Fmaven-metadata.xml&style=flat-square&label=Maven Version&color=44cc11"></a>
    <a href="https://milkdrinkers.github.io/ColorParser/introduction"><img src="https://img.shields.io/badge/Documentation-900C3F?style=flat-square" alt="documentation"/></a>
    <a href="https://maven.athyrium.eu/javadoc/releases/com/github/milkdrinkers/colorparser/latest"><img src="https://img.shields.io/badge/Javadoc-8A2BE2?style=flat-square" alt="javadoc"/></a>
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/milkdrinkers/colorparser?style=flat-square&label=Last%20Commit">
</p>

---

## Description

A simple utility library for easily adding [MiniMessage](https://docs.advntr.dev/minimessage/format.html) support with [Adventure](https://docs.advntr.dev/index.html) on any platform.

---

## Useful Links

* **Documentation** - [Link](https://milkdrinkers.github.io/ColorParser/introduction)
* **JavaDoc** - [Link](https://maven.athyrium.eu/javadoc/releases/com/github/milkdrinkers/colorparser/latest)
* **MiniMessage Formatting** - [Link](https://docs.advntr.dev/minimessage/format.html)
* **MiniMessage Previewer** - [Link](https://webui.advntr.dev/)
* **Adventure Documentation** - [Link](https://docs.advntr.dev/index.html)

---

## Examples

Basic usage example parsing a string into a component:
```java
Component message = ColorParser.of("<#00ff00><hover:show_text:'<red>test'>R G B!").build();
player.sendMessage(message);
```

This example has a custom placeholder (`<player>`) in the string that needs to be replaced:
```java
Component message = ColorParser.of("<green><player> Teleported to you.").parseMinimessagePlaceholder("player", player.getName()).build();
player.sendMessage(message);
```

This example also parses legacy color codes in the string:
```java
Component message = ColorParser.of("&6So<green>me &5String &4Here").parseLegacy().build();
player.sendMessage(message);
```

This example parses all PlaceholderAPI placeholders in the string:
```java
Component message = ColorParser.of("Your Displayname is: %player_displayname%").parsePAPIPlaceholders(player).build();
player.sendMessage(message);
```

---

## Installation

Please check out our [Documentation](https://milkdrinkers.github.io/ColorParser/introduction) for installation steps.