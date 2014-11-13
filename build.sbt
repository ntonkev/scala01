name := "scala01"

version := "0.1"

scalaVersion := "2.10.4"


//resolvers += "rediscala" at "http://dl.bintray.com/etaty/maven"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2-RC1" % "test",
  //"org.specs2" %% "specs2" % "2.0" % "it,test",
  //"com.etaty.rediscala" %% "rediscala" % "1.4.0",
  "net.debasishg" %% "redisclient" % "2.13"
)