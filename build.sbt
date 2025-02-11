name := """scala-play-study-application"""
organization := "com.mpanov"

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.5"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
libraryDependencies += "org.playframework" %% "play-slick" % "6.1.1"
libraryDependencies += "org.postgresql" % "postgresql" % "42.7.5"
libraryDependencies += "org.flywaydb" % "flyway-core" % "7.2.0"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.mpanov.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.mpanov.binders._"
