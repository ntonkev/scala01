name := "scala01"

version := "0.1"

scalaVersion := "2.10.4"


//resolvers += "rediscala" at "http://dl.bintray.com/etaty/maven"

libraryDependencies ++= Seq(
  "com.typesafe.akka"   %% "akka-actor"                   % "2.3.2",
  "com.typesafe.akka"   %% "akka-slf4j"                   % "2.3.2",
  "org.scalatest"       %% "scalatest"                    % "2.2.1" % "test",
  "org.scalamock"       %% "scalamock-scalatest-support"  % "3.2-RC1" % "test",
  "net.debasishg"       %% "redisclient"                  % "2.13",
  "org.postgresql"       % "postgresql"                   % "9.2-1003-jdbc4",
  "com.typesafe.slick"  %% "slick"                        % "2.0.2",
  "joda-time"            % "joda-time"                    % "2.3",
  "org.joda"             % "joda-convert"                 % "1.5",
  "com.github.tototoshi" %% "slick-joda-mapper"           % "1.1.0"
)