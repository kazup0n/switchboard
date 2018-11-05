name := "switchboard"

version := "0.1"

scalaVersion := "2.12.7"

// play
// https://mvnrepository.com/artifact/com.typesafe.play/play-cache
libraryDependencies += "com.typesafe.play" %% "play-cache" % "2.6.20"


// aws sdk
// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-servicediscovery
libraryDependencies += "com.amazonaws" % "aws-java-sdk-servicediscovery" % "1.11.441"
libraryDependencies += "com.amazonaws" % "aws-java-sdk-ssm" % "1.11.441"

// test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
