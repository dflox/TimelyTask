val scala3Version = "3.5.1"
val circeVersion = "0.14.1"
val scalaTestVersion = "3.2.19"
val jlineVersion = "3.26.2"
val nscalaTimeVersion = "2.34.0"
val scalaMetaVersion = "1.0.0"
val scalaYamlVersion = "0.3.0"
val scalaXmlVersion = "2.3.0"
val mockitoVersion = "3.2.10.0"
val scalaFxVersion = "23.0.1-R34"


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
      "io.circe" %% "circe-parser" % circeVersion
    )
  )