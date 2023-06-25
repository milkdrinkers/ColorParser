## Description

A simple utility for easily adding [MiniMessage](https://docs.advntr.dev/minimessage/format.html) support with [Adventure](https://docs.advntr.dev/index.html) on Paper server.

---

## Useful Links

* **MiniMessage Formatting** - [Link](https://docs.advntr.dev/minimessage/format.html)
* **MiniMessage Previewer** - [Link](https://webui.advntr.dev/)
* **Adventure Documentation** - [Link](https://docs.advntr.dev/minimessage/format.html)

---

## Usage

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

**Gradle**

```kotlin
repositories {
    maven("https://jitpack.io/") {
        content {
            includeGroup("com.github.milkdrinkers")
        }
    }
}

dependencies {
    implementation("com.github.milkdrinkers:ColorParser:1.0.0")
}
```