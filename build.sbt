lazy val akkaHttpV = "10.0.3"
val autowireV = "0.2.6"
lazy val reactV = "15.4.2"
lazy val scaJSreactV = "0.11.3"
lazy val scalaDomV = "0.9.1"
val scalaV = "2.12.1"
val upickleV = "0.4.4"
lazy val webAppDir = ""

val commonSettings = Seq(
  name := "RESTless webapp Akka HTTP, react as sbtweb-ScalaJs",
  version := "0.0.1-SNAPSHOT",
  description := "Learning Spring Scala Js",
  organization := "org.devd",
  organizationName := "DevD Private Limited",
  startYear := Some(2014),
  scalaVersion := scalaV,
  libraryDependencies ++= Settings.sharedDependencies
)

scalaVersion := scalaV
scalacOptions in ThisBuild ++= Seq("-deprecation", "-feature", "-unchecked")

lazy val client = (project in file(webAppDir + "/client")).settings(
  commonSettings,
  jsDependencies ++= Settings.jsDependencies,
  libraryDependencies ++= Settings.scalajsDependencies,
  // KEEP THIS normalizedName CONSTANTLY THE SAME, otherwise the outputted JS filename will be changed.
  normalizedName := "main",
  persistLauncher := true,
  persistLauncher in Test := false
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).dependsOn(sharedJs)

lazy val server = (project in file(webAppDir + "/server")).settings(
  commonSettings,
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  // Compile the project before generating Eclipse files, so that generated .scala or .class files for Twirl templates are present
  //EclipseKeys.preTasks := Seq(compile in Compile)
  libraryDependencies ++= Settings.jvmDependencies,
  managedClasspath in Runtime += (packageBin in Assets).value,
  name := "SERVER",
  pipelineStages in Assets := Seq(scalaJSPipeline),
  scalaJSProjects := Seq(client),
  WebKeys.packagePrefix in Assets := "public/"
).enablePlugins(SbtWeb /*, JavaAppPackaging*/).dependsOn(sharedJvm)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file(webAppDir + "/shared")).
  settings(commonSettings).jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val sharedJs = shared.js
lazy val sharedJvm = shared.jvm

// loads the server project at sbt startup
onLoad in Global := (Command.process("project server", _: State))