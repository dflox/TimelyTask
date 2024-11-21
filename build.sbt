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
    version := "0.1.0-SNAPSHOT",

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

// sbt-github-actions defaults to using JDK 8 for testing and publishing.
// The following adds JDK 17 for testing.
import sbtghactions.GenerativePlugin.autoImport._

ThisBuild / githubWorkflowJavaVersions := List(JavaSpec.temurin("17"))
ThisBuild / crossScalaVersions := Seq("3.5.1")
ThisBuild / scalaVersion := "3.5.1"

// Specify the operating systems
ThisBuild / githubWorkflowOSes := Seq("ubuntu-latest", "windows-latest", "macos-latest")

// Define the CI jobs
ThisBuild / githubWorkflowGeneratedCI := Seq(
  WorkflowJob(
    name = "test",
    id = "test",
    steps = List(
    WorkflowStep.CheckoutFull: WorkflowStep,
    WorkflowStep.SetupJava(List(JavaSpec.temurin("17"))).head,
    WorkflowStep.Sbt(List("++${{ env.SCALA_VERSION }}", "clean", "test"),
      name = Some("Run tests")): WorkflowStep
  ))
)

// Specify environment variables
ThisBuild / githubWorkflowEnv := Map(
  "SCALA_VERSION" -> "3.5.1"
)