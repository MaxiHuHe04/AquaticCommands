name := "AquaticCommands"

scalaVersion := "2.12.6"

val commonSettings = Seq(
  version := "0.3",
  resolvers += "spigot-repo" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/",
  resolvers += "sonatype" at "https://oss.sonatype.org/content/groups/public/",
  libraryDependencies += "org.spigotmc" % "spigot-api" % "1.13.1-R0.1-SNAPSHOT" % "provided"
)

lazy val aquaticCommands = (project in file(".")).settings(commonSettings)

lazy val spigotPlugin = (project in file("plugin")).dependsOn(aquaticCommands).settings(commonSettings).settings(
  assemblyJarName in assembly := s"AquaticCommands-spigot-${version.value}.jar"
)
