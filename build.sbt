version := "1.0"

scalaVersion := "2.11.8"

description := "FIO Bank scraper"

lazy val root = (project in file(".")).settings(
  name := "fio-scraper",
  mainClass in Compile := Some("eu.akkamo.Main"),
  libraryDependencies ++= Seq(
    "eu.akkamo" %% "akkamo" % "1.0.2"
  )
).enablePlugins(AkkamoSbtPlugin)