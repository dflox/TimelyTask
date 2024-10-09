val scala3Version = "3.5.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "TimelyTask",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test,
    libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.34.0"
  )
