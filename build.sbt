lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.duskeagle",
      scalaVersion := "2.12.1",
      version      := "1.0.0"
    )),
    name := "RestTest"
  )

assemblyJarName in assembly := "resttest.jar"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.3"
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.1"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
