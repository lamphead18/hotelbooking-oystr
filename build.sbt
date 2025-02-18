name := "HotelBooking"

version := "0.1"

scalaVersion := "2.13.12"
sbtVersion := "1.10.7"

// Dependencies
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.2.9",
  "com.typesafe.akka" %% "akka-stream" % "2.6.20",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.9"
)


// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.