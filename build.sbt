version := "0.1"
name := "scala-essentials"

scalaVersion := "2.13.18"

val akkaVersion = "2.8.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.2.20"
)
