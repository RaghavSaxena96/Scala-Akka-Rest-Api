name := "untitled"

version := "0.1"

scalaVersion := "2.13.3"

//Dependencies

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.0"

libraryDependencies ++= Seq(

  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion ,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.mockito" % "mockito-all" % "1.9.5" % Test ,
  "com.github.etaty" %% "rediscala" % "1.9.0"

)


dockerBaseImage := "openjdk:8-jre-alpine"


enablePlugins(JavaAppPackaging ,AshScriptPlugin)
enablePlugins(DockerPlugin)