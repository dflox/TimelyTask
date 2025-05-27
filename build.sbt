import scala.collection.Seq

val scala3Version = "3.6.4"
val circeVersion = "0.14.13"
val scalaTestVersion = "3.2.19"
val jlineVersion = "3.29.0"
val nscalaTimeVersion = "3.0.0"
val scalaMetaVersion = "1.1.1"
val scalaYamlVersion = "0.3.0"
val scalaXmlVersion = "2.3.0"
val mockitoVersion = "3.2.10.0"
val scalaFxVersion = "24.0.0-R35"
val macwireVersion = "2.6.6"
val circeYamlVersion = "0.16.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "TimelyTask",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % scalaMetaVersion % Test,
      "com.github.nscala-time" %% "nscala-time" % nscalaTimeVersion,
      "org.jline" % "jline" % jlineVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
      "org.virtuslab" %% "scala-yaml" % scalaYamlVersion % Test,
      "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion,
      "org.scalatestplus" %% "mockito-3-4" % mockitoVersion % Test,
      "org.scalafx" %% "scalafx" % scalaFxVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "io.circe" %% "circe-yaml-v12" % circeYamlVersion,
      "com.softwaremill.macwire" %% "macros" % macwireVersion % "provided"
    )
  )