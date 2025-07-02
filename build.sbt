import scala.collection.Seq

val scala3Version = "3.7.1"
val circeVersion = "0.14.14"
val scalaTestVersion = "3.2.19"
val jlineVersion = "3.30.4"
val nscalaTimeVersion = "3.0.0"
val scalaMetaVersion = "1.1.1"
val scalaXmlVersion = "2.4.0"
val mockitoVersion = "3.2.10.0"
val scalaFxVersion = "24.0.0-R35"
val macwireVersion = "2.6.6"
val circeYamlVersion = "1.15.0"
val sqliteJdbcVersion = "3.50.1.0"
val simpleSqlVersion = "0.4.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "TimelyTask",
    version := {
      try {
        sys.process.Process("git describe --tags --abbrev=0").!!.stripLineEnd
      } catch {
        case _: Throwable => "0.0.0" // Default version if no tags are found
      }
    },
    assembly / assemblyJarName := s"timelytask-${version.value}.jar",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % scalaMetaVersion % Test,
      "com.github.nscala-time" %% "nscala-time" % nscalaTimeVersion,
      "org.jline" % "jline" % jlineVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
      "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion,
      "org.scalatestplus" %% "mockito-3-4" % mockitoVersion % Test,
      "org.scalafx" %% "scalafx" % scalaFxVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "io.circe" %% "circe-yaml-v12" % circeYamlVersion,
      "com.softwaremill.macwire" %% "macros" % macwireVersion % "provided",
      "org.xerial" % "sqlite-jdbc" % sqliteJdbcVersion,
      "io.crashbox" %% "simplesql" % simpleSqlVersion
    )
  )

assembly / assemblyMergeStrategy := {
  case "module-info.class" => MergeStrategy.discard
  case x if x.endsWith("module-info.class") => MergeStrategy.discard
  case x if x.contains("META-INF/substrate") => MergeStrategy.first
  case x if x.contains("META-INF/versions") => MergeStrategy.first
  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}