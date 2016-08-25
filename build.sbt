version := "1.0"

description := "FIO Bank scraper"

scalaVersion in Global := "2.11.8"

lazy val numbers = RootProject(uri("git://github.com/jancajthaml/number.git#b417704bac0c4cbde505f9f560c50a54de407659"))

lazy val root = (project in file(".")).settings(
  name := "fiobank-scraper",
  mainClass in Compile := Some("eu.akkamo.Main"),
  libraryDependencies ++= Seq(
    "eu.akkamo" %% "akkamo" % "1.0.2"
  )
).dependsOn(numbers).enablePlugins(AkkamoSbtPlugin)
