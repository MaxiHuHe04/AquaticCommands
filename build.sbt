name := "AquaticCommands"

scalaVersion := "2.12.6"

val bintrayCredentials = if ((Path.userHome / ".sbt" / "bintray.credentials").exists())
  Credentials(Path.userHome / ".sbt" / "bintray.credentials")
else
  Credentials("Bintray API Realm", "api.bintray.com", sys.env.getOrElse("BINTRAY_USER", ""), sys.env.getOrElse("BINTRAY_PASS", ""))

val commonSettings = Seq(
  version := "0.3",
  resolvers += "spigot-repo" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/",
  resolvers += "sonatype" at "https://oss.sonatype.org/content/groups/public/",
  libraryDependencies += "org.spigotmc" % "spigot-api" % "1.13.1-R0.1-SNAPSHOT" % "provided",
  publishTo := Some("bintray" at "https://api.bintray.com/maven/maxihuhe04/AquaticCommands/AquaticCommands/;publish=1"),
  credentials += bintrayCredentials,
  publishMavenStyle := true
)

lazy val aquaticCommands = (project in file(".")).settings(commonSettings)

lazy val spigotPlugin = (project in file("plugin")).dependsOn(aquaticCommands).settings(commonSettings).settings(
  assemblyJarName in assembly := s"AquaticCommands-spigot-${version.value}.jar"
)
