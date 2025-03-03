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
libraryDependencies += "org.typelevel" %% "cats-effect-kernel" % "3.5.7"
libraryDependencies += "org.tpolecat" %% "doobie-core" % "1.0.0-RC8"
libraryDependencies += "org.tpolecat" %% "doobie-postgres" % "1.0.0-RC8"
