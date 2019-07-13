name := "ultra-iot-task"

version := "1.0"

scalaVersion := "2.12.0"

libraryDependencies ++= {
  val akkaVersion       = "2.4.17"
  val kafkaVesion       = "0.10.1.0"
  val jacksonVersion = "2.9.8"

  Seq(
    "com.typesafe.akka"           %% "akka-actor"               % akkaVersion,
    "com.typesafe.akka"           %% "akka-slf4j"               % akkaVersion,
    "com.typesafe.akka"           %% "akka-stream"              % akkaVersion,
    "org.scalaz"                  %% "scalaz-core"              % "7.2.15",
    "org.apache.kafka"            % "kafka-clients"             % kafkaVesion,
    "com.google.code.gson" % "gson" % "1.7.1",
    "org.scalatest"                % "scalatest_2.11"            % "2.2.1"               % "test"
  )
}