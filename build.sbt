ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.3"

lazy val root = (project in file("."))
  .settings(
    name := "locksmith-postgres",
    organization := "com.raunakjodhawat"
  )
// Provided dependencies
libraryDependencies += "com.raunakjodhawat" %% "locksmith-core" % version.value % "provided"
libraryDependencies += "com.github.pureconfig" %% "pureconfig-core" % "0.17.8" % "provided"

libraryDependencies += "org.postgresql" % "postgresql" % "42.7.5"
// IO operations
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.7"
