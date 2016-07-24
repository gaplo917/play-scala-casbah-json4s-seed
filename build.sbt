name := """play-scala-casbah-json4s-seed"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.mongodb" %% "casbah" % "3.1.1",
  "org.mongodb" % "bson" % "3.3.0",
  "org.json4s" %% "json4s-native" % "3.3.0",
  "org.json4s" %% "json4s-mongo" % "3.3.0",
  "org.json4s" %% "json4s-ext" % "3.3.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
