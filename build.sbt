enablePlugins(JavaAppPackaging)

name := "chat-room"
organization := "com.akkachat"
version := "1.0"

scalaVersion := "2.12.1"

scalacOptions ++= Seq(
	"-unchecked",
	"-deprecation",
	"-feature",
	"-language:postfixOps")

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.4.16",
	"com.typesafe.akka" %% "akka-slf4j" % "2.4.16",
	"com.typesafe" % "config" % "1.3.1",
	"ch.qos.logback" % "logback-classic" % "1.2.1",
	"com.typesafe.akka" %% "akka-remote" % "2.4.16" % "runtime",
	"com.typesafe.akka" %% "akka-testkit" % "2.4.16" % "test",
	"org.scalatest" %% "scalatest" % "3.0.1" % "test")

