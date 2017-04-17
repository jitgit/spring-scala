
val commonSettings = Seq(
  name := "React with scalajs & spring",
  version := "0.0.1-SNAPSHOT",
  description := "Learning Spring Scala Js",
  organization := "org.devd",
  organizationName := "DevD Private Limited",
  startYear := Some(2014),
  scalaVersion := Versions.scalaVersion,
  libraryDependencies ++= Settings.sharedDependencies.value
)

scalaVersion := Versions.scalaVersion
scalacOptions in ThisBuild ++= Seq("-deprecation", "-feature", "-unchecked", "-Ylog-classpath")

lazy val client = (project in file("client")).settings(
  commonSettings,
  jsDependencies ++= Settings.jsDependencies.value,
  libraryDependencies ++= Settings.scalajsDependencies.value,
  normalizedName := "main", // KEEP THIS normalizedName CONSTANTLY THE SAME, otherwise the outputted JS filename will be changed.
  persistLauncher := true,
  persistLauncher in Test := false
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).dependsOn(sharedJs)

lazy val server = (project in file("server")).settings(
  commonSettings,
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  // Compile the project before generating Eclipse files, so that generated .scala or .class files for Twirl templates are present
  //EclipseKeys.preTasks := Seq(compile in Compile)
  libraryDependencies ++= Settings.jvmDependencies.value,
  managedClasspath in Runtime += (packageBin in Assets).value,
  name := "SERVER",
  pipelineStages in Assets := Seq(scalaJSPipeline),
  scalaJSProjects := Seq(client),
  scalacOptions := Seq("-Ylog-classpath", "verbose"),
  WebKeys.packagePrefix in Assets := "public/"
).enablePlugins(SbtWeb /*, JavaAppPackaging*/).dependsOn(sharedJvm)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(commonSettings).jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val sharedJs = shared.js
lazy val sharedJvm = shared.jvm

// loads the server project at sbt startup
onLoad in Global := (Command.process("project server", _: State))