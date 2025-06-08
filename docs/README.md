<h1 style="text-align:center;">ColorParser</h1>

<p style="text-align:center;">
    <a href="https://github.com/milkdrinkers/ColorParser/blob/main/LICENSE">
        <img alt="GitHub License" src="https://img.shields.io/github/license/milkdrinkers/ColorParser?style=for-the-badge&color=blue&labelColor=141417">
    </a>
    <a href="https://central.sonatype.com/artifact/io.github.milkdrinkers/colorparser-common">
        <img alt="Maven Central Version" src="https://img.shields.io/maven-central/v/io.github.milkdrinkers/colorparser-common?style=for-the-badge&labelColor=141417">
    </a>
    <a href="https://milkdrinkers.athyrium.eu/colorparser">
        <img alt="Documentation" src="https://img.shields.io/badge/DOCUMENTATION-900C3F?style=for-the-badge&labelColor=141417">
    </a>
    <a href="https://javadoc.io/doc/io.github.milkdrinkers/colorparser-common">
        <img alt="Javadoc" src="https://img.shields.io/badge/JAVADOC-8A2BE2?style=for-the-badge&labelColor=141417">
    </a>
    <img alt="GitHub Actions Workflow Status" src="https://img.shields.io/github/actions/workflow/status/milkdrinkers/ColorParser/ci.yml?style=for-the-badge&labelColor=141417">
    <a href="https://github.com/milkdrinkers/ColorParser/issues">
        <img alt="GitHub Issues" src="https://img.shields.io/github/issues/milkdrinkers/ColorParser?style=for-the-badge&labelColor=141417">
    </a>
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/milkdrinkers/ColorParser?style=for-the-badge&labelColor=141417">
</p>

A simple utility library for adding [MiniMessage](https://docs.advntr.dev/minimessage/format.html) support with [Adventure](https://docs.advntr.dev/index.html) to your plugin/mod on any platform, with extensive support for placeholder systems like [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI) and [MiniPlaceholders](https://github.com/MiniPlaceholders/MiniPlaceholders). 

---

## 🌟 Features
- Simple API for parsing strings into Adventure components
- Backwards compatibility for legacy color codes like `§` & `&`
- Built-in support for placeholder systems like PlaceholderAPI and MiniPlaceholders
- Extension to the default MiniMessage Tags with new useful additions

## 🛠️ Supported Platforms

### Platform Support

| Platform | Minimum Version | Supported Versions | Notes                                                            |
|----------|-----------------|:-------------------|------------------------------------------------------------------|
| Velocity | 3.0.0           | 3.0.0+             | -                                                                |
| Paper    | 1.16.5          | 1.16.5+            | -                                                                |
| Bukkit   | 1.7.10          | 1.7.10+            | Use on platforms/versions that do not natively support Adventure |
| Sponge   | 8 (1.16.5)      | 8+                 | -                                                                |

### Placeholder Support

| Platform | PlaceholderAPI                         | MiniPlaceholders | Notes                                                                |
|----------|----------------------------------------|------------------|:---------------------------------------------------------------------|
| Bukkit   | ✅ All versions                         | ❌ Not supported | -                                                                    |
| Paper    | ✅ All versions                         | ✅ 1.19.4+ | MiniPlaceholders requires Paper 1.19.4 or higher                     |
| Velocity | ❌ Not supported (Use MiniPlaceholders) | ✅ All versions | MiniPlaceholders can provide PlaceholderAPI placeholders on Velocity |
| Sponge   | ❌ Not supported (Use MiniPlaceholders) | ✅ All versions | -                                                                    |

### Version Matrix

| Platform Version      | PlaceholderAPI | MiniPlaceholders |
|-----------------------|---------------|------------------|
| Bukkit 1.7.10+        | ✅ | ❌ |
| Paper 1.16.5 - 1.19.3 | ✅ | ❌ |
| Paper 1.19.4+         | ✅ | ✅ |
| Velocity 3.0.0+       | ❌ | ✅ |
| Sponge 8+             | ❌ | ✅ |

## ❓ Why Make This?

ColorParser was created from a place of frustration. Although Adventure/MiniMessage is great and incredibly powerful, implementing its usage in an actual project can be cumbersome, tedious, and in many cases result in some extremely verbose code. 

Trying to provide consistent PlaceholderAPI and MiniPlaceholders support, backwards compatibility for legacy color codes, and more, significantly compounds this unfortunate effect. Eventually, what was originally a simple single class Builder became a library that has grown with my own needs over time.

**The premise is and has always been simple.** We want to take our locale/translation strings and parse them into Components with as little code as possible, while still providing rich API for those who need it. After all, what server administrator doesn't want their plugins' messages to be customizable to the maximum. 

## 📦 Installation

Please read the full installation instructions in the [documentation here](https://milkdrinkers.athyrium.eu/colorparser/installation).

The `paper`, `velocity`, `bukkit` & `sponge` modules transitively include `common`. Additionally you should shade the library into your plugin jar.

<details>
<summary>Gradle Kotlin DSL</summary>

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.milkdrinkers:colorparser-paper:VERSION")
    implementation("io.github.milkdrinkers:colorparser-velocity:VERSION")
    implementation("io.github.milkdrinkers:colorparser-bukkit:VERSION")
}
```
</details>

<details>
<summary>Maven</summary>

```xml
<project>
    <dependencies>
        <dependency>
            <groupId>io.github.milkdrinkers</groupId>
            <artifactId>colorparser-paper</artifactId>
            <version>VERSION</version>
        </dependency>
        <dependency>
            <groupId>io.github.milkdrinkers</groupId>
            <artifactId>colorparser-velocity</artifactId>
            <version>VERSION</version>
        </dependency>
        <dependency>
            <groupId>io.github.milkdrinkers</groupId>
            <artifactId>colorparser-bukkit</artifactId>
            <version>VERSION</version>
        </dependency>
    </dependencies>
</project>
```
</details>

## Usage Example 🚀

```java
// Parses the content of a MiniMessage string into a component
Component message = ColorParser.of("<#00ff00><hover:show_text:'<red>test'>R G B!")
    .build();
```

```java
Component message1 = ColorParser.of("<green><player> Teleported to you.")
    .with("player", player.getName()) // Parses the placeholder <player> with the player's name
    .build();

Component message2 = ColorParser.of("<green><player> Teleported to you.")
    .with("player", Component.text("Lorem Ipsum")) // Parses the placeholder <player> with an Adventure Component
    .build();

Component message3 = ColorParser.of("<green><player> Teleported to you.")
    .with("player", "<red><bold>Lorem Ipsum") // Parses the placeholder <player> with MiniMessage string
    .build();
```

```java
Component message = ColorParser.of("&6So<green>me &5String &4Here")
    .legacy() // Parses legacy color codes like § and &, into their MiniMessage equivalents
    .build();
```

```java
Component message = ColorParser.of("Your Displayname is: %player_displayname%")
    .papi(player) // Parses all PlaceholderAPI placeholders in the string
    .mini(player) // Parses MiniPlaceholders in the string
    .build();
```

## 📚 Documentation 

- [Javadoc](https://javadoc.io/doc/io.github.milkdrinkers/colorparser-common)
- [Documentation](https://milkdrinkers.athyrium.eu/colorparser)
- [Maven Central](https://central.sonatype.com/search?q=colorparser&namespace=io.github.milkdrinkers)
- [MiniMessage Format](https://docs.advntr.dev/minimessage/format.html)
- [MiniMessage Previewer](https://webui.advntr.dev/)
- [Adventure Documentation](https://docs.advntr.dev/index.html)

---

## 🔨 Building from Source 

```bash
git clone https://github.com/milkdrinkers/ColorParser.git
cd colorparser
./gradlew publishToMavenLocal
```

---

## 🔧 Contributing

Contributions are always welcome! Please make sure to read our [Contributor's Guide](CONTRIBUTING.md) for standards and our [Contributor License Agreement (CLA)](CONTRIBUTOR_LICENSE_AGREEMENT.md) before submitting any pull requests.

We also ask that you adhere to our [Contributor Code of Conduct](CODE_OF_CONDUCT.md) to ensure this community remains a place where all feel welcome to participate.

---

## 📝 Licensing

You can find the license the source code and all assets are under [here](../LICENSE). Additionally, contributors agree to the Contributor License Agreement \(*CLA*\) found [here](CONTRIBUTOR_LICENSE_AGREEMENT.md).
