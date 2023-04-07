import Dependencies._

ThisBuild / scalaVersion := "2.13.10"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "sangria-circe-issue",
    libraryDependencies ++= Seq(
      "org.sangria-graphql" %% "sangria" % "3.5.3",
      "org.sangria-graphql" %% "sangria-circe" % "1.3.2",
      "io.circe" %% "circe-literal" % "0.14.1",
      "io.circe" %% "circe-parser" % "0.14.1",
      "org.typelevel" %% "cats-effect" % "3.4.8",
      "co.fs2" %% "fs2-io" % "3.6.1"


    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
