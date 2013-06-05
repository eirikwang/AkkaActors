name := "game of life"

version := "1.0"

scalaVersion := "2.10.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.1.4"

libraryDependencies += "com.typesafe.akka" %% "akka-kernel" % "2.1.4"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.1.4" % "test"

libraryDependencies += "org.apache.commons" % "commons-io" % "1.3.2"

libraryDependencies += "junit" % "junit" % "4.11" % "test"


