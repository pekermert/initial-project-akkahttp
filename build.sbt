name := "212-akkahttp"

version := "0.1"

scalaVersion := "2.11.8"

scalacOptions := Seq(
  "-deprecation",
  "-encoding", "UTF-8", // 2 args
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Ywarn-unused-import"
)

libraryDependencies ++= {
  val akkaVersion = "2.4.8"
  val slickV = "3.1.1"
  val metricsV = "3.5.5_a2.3"
  val scalaLoggingV = "3.1.0"
  Seq(
    "com.typesafe.akka"           %%  "akka-http-core"                      % akkaVersion,
    "com.typesafe.akka"           %%  "akka-http-spray-json-experimental"   % akkaVersion,
    "com.typesafe.akka"           %%  "akka-http-experimental"              % akkaVersion,
    "com.typesafe.akka"           %%  "akka-actor"                          % akkaVersion,
    "com.typesafe.akka"           %%  "akka-slf4j"                          % akkaVersion,
    "ch.qos.logback"              %   "logback-classic"                     % "1.2.3"  % Runtime,
    "com.typesafe.slick"          %%  "slick-hikaricp"                      % slickV,
    "nl.grons"                    %%  "metrics-scala"                       % metricsV,
    "com.typesafe.scala-logging"  %%  "scala-logging"                       % scalaLoggingV
  )
}

assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
  case m if m.startsWith("META-INF")              => MergeStrategy.discard
  case m if m.contains("reference.conf")          => MergeStrategy.concat
  case _                                          => MergeStrategy.first
}

assemblyJarName in assembly := s"${name.value}-${version.value}.jar"