lazy val root = project.in(file("."))
  .settings(
    name := "scala3-incremental-compilation-cached-mirror",
    organization := "com.example",
    scalaVersion := "3.4.1",
    version      := "0.1.0-SNAPSHOT",
  )
