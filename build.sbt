organization := "com.katlex"

name := "util"

version := "0.1"

resolvers <+= sbtResolver

libraryDependencies ++= Seq("org.scala-sbt" % "launcher-interface" % "0.12.0" % "provided")

scalaVersion := "2.10.0"
