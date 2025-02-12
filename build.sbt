name := """scala-play-study-application"""
organization := "com.mpanov"

version := "1.0.0"

lazy val CI = config("ci") extend Test
lazy val Dev = config("dev") extend Compile
lazy val Prod = config("prod") extend Compile

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .configs(Prod, Dev, CI)
  .settings(
    inConfig(Prod)(Defaults.compileSettings),
    inConfig(Dev)(Defaults.compileSettings),
    inConfig(CI)(Defaults.testSettings),

    CI / test / fork := true,
    Dev / run / fork := true,
    Prod / run / fork := true,

    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),

    CI / javaOptions += "-Dconfig.file=conf/ci.conf",
    CI / unmanagedSourceDirectories += (Test / scalaSource).value,

    Dev / run / javaOptions += "-Dconfig.file=conf/dev.conf",
    Dev / mainClass := Some("play.core.server.ProdServerStart"),

    Prod / run / javaOptions += "-Dconfig.file=conf/prod.conf",
    Prod / mainClass := Some("play.core.server.ProdServerStart")

  )

scalaVersion := "3.3.5"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
libraryDependencies += "org.playframework" %% "play-slick" % "6.1.1"
libraryDependencies += "org.postgresql" % "postgresql" % "42.7.5"
libraryDependencies += "org.flywaydb" % "flyway-core" % "7.2.0"
