name := """scala-play-study-application"""
organization := "com.mpanov"

version := "1.0.0"

lazy val CI = config("ci") extend Test
lazy val Dev = config("dev") extend Compile
lazy val Prod = config("prod") extend Compile

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, ScoverageSbtPlugin, JacocoPlugin)
  .configs(Prod, Dev, CI)
  .settings(
    // Scalafix configuration
    ThisBuild / scalafixDependencies += "io.github.dedis" %% "scapegoat-scalafix" % "1.1.4",
    semanticdbEnabled := true,

    // Compiler options
    scalaVersion := "3.3.5",
    scalacOptions += "-Wunused:imports",
    javacOptions ++= Seq("-source", "17", "-target", "17"),

    // Base configurations
    inConfig(Prod)(Defaults.compileSettings),
    inConfig(Dev)(Defaults.compileSettings),
    inConfig(CI)(Defaults.testSettings),

    // Enable forks for all application configurations
    CI / test / fork := true,
    Dev / run / fork := true,
    Prod / run / fork := true,

    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),

    // Dev Application Configuration
    Dev / run / javaOptions += "-Dconfig.file=conf/dev.conf",
    Dev / mainClass := Some("play.core.server.ProdServerStart"),

    // Prod Application Configuration
    Prod / run / javaOptions += "-Dconfig.file=conf/prod.conf",
    Prod / mainClass := Some("play.core.server.ProdServerStart"),

    // CI Process Configuration
    CI / javaOptions += "-Dconfig.file=conf/ci.conf",
    CI / unmanagedSourceDirectories += (Test / scalaSource).value,
    CI / coverageReport := (CI / coverageReport).value,
    coverageEnabled := true,
    coverageExcludedFiles := ".*ReverseRoutes.*;.*JavaScriptReverseRoutes.*",
    coverageExcludedPackages := "controllers\\.javascript;router"
  )

libraryDependencies ++= Seq(
    guice,
    "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
    "org.playframework" %% "play-slick" % "6.1.1",
    "org.postgresql" % "postgresql" % "42.7.5",
    "org.flywaydb" % "flyway-core" % "7.2.0"
)