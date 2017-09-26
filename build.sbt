lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.duskeagle",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "RestTest"
  )

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.3"
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.1"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
