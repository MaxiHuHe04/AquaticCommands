# Aquatic Commands

[![Build Status](https://travis-ci.com/MaxiHuHe04/AquaticCommands.svg?token=Bq9BRQi93kaDqWE2Gk21&branch=master)](https://travis-ci.com/MaxiHuHe04/AquaticCommands)

## Features
- Make new commands with almost no code
- 1.13 vanilla-like tab completion and parser errors
- Easily expandable
- No unnecessary code required

## Installation

### SBT

Add this to your `build.sbt`:
```sbt
resolvers += Resolver.jcenterRepo
libraryDependencies += "me.maxih" %% "aquaticcommands" % "0.3"

resolvers += Resolver.sonatypeRepo("public")
resolvers += "spigot-repo" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
libraryDependencies += "org.spigotmc" % "spigot-api" % "1.13.1-R0.1-SNAPSHOT" % "provided"
```


### Maven

Add this to your `pom.xml`:
```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <url>https://jcenter.bintray.com/</url>
    </repository>
    <repository>
        <id>spigot-repo</id>
        <url>https://hub.spigotmc.org/nexus/content/repositories/snapshots/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>org.spigotmc</groupId>
        <artifactId>spigot-api</artifactId>
        <version>1.13-R0.1-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>me.maxih</groupId>
        <artifactId>aquaticcommands_2.12</artifactId>
        <version>0.3</version>
    </dependency>
</dependencies>
```

### Jar

Choose the jar file without `-spigot` at the [latest release](https://github.com/MaxiHuHe04/AquaticCommands/releases/latest).

### Spigot

You have to add the AquaticCommands plugin to your plugins folder, which includes the library and Scala runtime.
The plugin can be found [here](https://github.com/MaxiHuHe04/AquaticCommands/releases/latest). Make sure you use the `xxx-spigot-X.X.jar` file.

## [Getting started](https://github.com/MaxiHuHe04/AquaticCommands/wiki/Getting-started)

