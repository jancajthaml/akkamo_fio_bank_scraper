version := "1.0"

description := "FIO Bank scraper"

scalaVersion in Global := "2.11.8"

lazy val root = (project in file(".")).settings(
  name := "fiobank-scraper",
  mainClass in Compile := Some("eu.akkamo.Main"),
  libraryDependencies ++= Seq(
    "eu.akkamo" %% "akkamo" % "1.0.2"
  )
).enablePlugins(AkkamoSbtPlugin)