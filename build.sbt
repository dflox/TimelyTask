val scala3Version = "3.5.1"
val circeVersion = "0.14.1"
val scalaTestVersion = "3.2.19"
val jlineVersion = "3.26.2"
val nscalaTimeVersion = "2.34.0"
val scalaMetaVersion = "1.0.0"
val scalaYamlVersion = "0.3.0"
val scalaXmlVersion = "2.3.0"
val mockitoVersion = "3.2.10.0"


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
    libraryDependencies += "org.scalameta" %% "munit" % scalaMetaVersion % Test,
    libraryDependencies += "com.github.nscala-time" %% "nscala-time" % nscalaTimeVersion,
    libraryDependencies += "org.jline" % "jline" % jlineVersion,
    libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    libraryDependencies += "org.virtuslab" %% "scala-yaml" % scalaYamlVersion % Test,
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion,
    libraryDependencies += "org.scalatestplus" %% "mockito-3-4" % mockitoVersion % Test,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion)
  )
