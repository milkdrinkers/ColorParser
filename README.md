
<h1 align="center">ColorParser</h1>
<p align="center">
    <img src="https://img.shields.io/github/license/milkdrinkers/ColorParser?color=blue&style=flat-square" alt="license"/>
    <a href="https://github.com/milkdrinkers/ColorParser#installation"><img src="https://jitpack.io/v/milkdrinkers/colorparser.svg?style=flat-square" alt="jitpack"/></a>
    <a href="https://jitpack.io/com/github/USER/REPO/latest/javadoc/"><img src="https://img.shields.io/badge/Javadoc-8A2BE2?style=flat-square" alt="javadoc"/></a>
</p>

---

## Description

A simple utility for easily adding [MiniMessage](https://docs.advntr.dev/minimessage/format.html) support with [Adventure](https://docs.advntr.dev/index.html) on Paper server.

---

## Useful Links

* **JavaDoc** - [Link](https://jitpack.io/com/github/USER/REPO/latest/javadoc/)
* **MiniMessage Formatting** - [Link](https://docs.advntr.dev/minimessage/format.html)
* **MiniMessage Previewer** - [Link](https://webui.advntr.dev/)
* **Adventure Documentation** - [Link](https://docs.advntr.dev/index.html)

---

## Usage

Basic usage example parsing a string into a component:
```java
Component message = new ColorParser("<#00ff00><hover:show_text:'<red>test'>R G B!").build();
player.sendMessage(message);
```

This example has a custom placeholder (`<player>`) in the string that needs to be replaced:
```java
Component message = new ColorParser("<green><player> Teleported to you.").parseMinimessagePlaceholder("player", player.getName()).build();
player.sendMessage(message);
```

This example also parses legacy color codes in the string:
```java
Component message = new ColorParser("&6So<green>me &5String &4Here").parseLegacy().build();
player.sendMessage(message);
```

This example also parses all PlaceholderAPI placeholders in the string:
```java
Component message = new ColorParser("Your Displayname is: %player_displayname%").parsePAPIPlaceholders(player).build();
player.sendMessage(message);
```

--- 

## Installation

**Gradle (Kotlin DSL)**
```kotlin
repositories {
    maven("https://jitpack.io/") {
        content {
            includeGroup("com.github.milkdrinkers")
        }
    }
}

dependencies {
    implementation("com.github.milkdrinkers:colorparser:1.0.5")
}
```

**Maven**
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.milkdrinkers</groupId>
    <artifactId>colorparser</artifactId>
    <version>1.0.5</version>
</dependency>
```