import sbt.Keys._
import sbt.Project.projectToRef


// use eliding to drop some debug code in the production build
lazy val elideOptions = settingKey[Seq[String]]("Set limit for elidable functions")
// instantiate the JS project for SBT with some additional settings
lazy val clientJSDeps = List() //List("prolog_parser.js", "validator.js")

lazy val sharedJVM = shared.jvm.settings(name := "sharedJVM")

lazy val sharedJS = shared.js.settings(name := "sharedJS")

// a special crossProject for configuring a JS/JVM/shared structure
lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared"))
  .settings(
    scalaVersion := Versions.scalaVersion,
    libraryDependencies ++= Settings.sharedDependencies.value
  )
  .jsConfigure(_ enablePlugins ScalaJSWeb) // set up settings specific to the JS project

lazy val client: Project = (project in file("client"))
  .settings(
    name := "client",
    version := Versions.appVersion,
    scalaVersion := Versions.scalaVersion,
    scalacOptions ++= Settings.scalacOptions,
    resolvers += sbt.Resolver.bintrayRepo("denigma", "denigma-releases"),
    libraryDependencies ++= Settings.scalajsDependencies.value,
    elideOptions := Seq(), // by default we do development build, no eliding
    scalacOptions ++= elideOptions.value,
    jsDependencies ++= Settings.jsDependencies.value,
    jsDependencies += RuntimeDOM % "test", // RuntimeDOM is needed for tests
    skip in packageJSDependencies := false, // yes, we want to package JS dependencies
    jsDependencies ++= clientJSDeps.map(ProvidedJS / _), // use Scala.js provided launcher code to start the client app
    persistLauncher := true,
    persistLauncher in Test := false
  ).enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .dependsOn(sharedJS)

// Client projects (just one in this case)
lazy val clients = Seq(client)
// instantiate the JVM project for SBT with some additional settings
lazy val server = (project in file("server"))
  .settings(
    name := "server",
    version := Versions.appVersion,
    scalaVersion := Versions.scalaVersion,
    scalacOptions ++= Settings.scalacOptions,
    resolvers += sbt.Resolver.bintrayRepo("denigma", "denigma-releases"), //add resolver
    libraryDependencies ++= Settings.jvmDependencies.value,
    commands += ReleaseCmd,
    compile in Compile <<= (compile in Compile) dependsOn scalaJSPipeline,
    scalaJSProjects := clients,  // connect to the client project
    pipelineStages in Assets := Seq(scalaJSPipeline)
//    pipelineStages := Seq(digest, gzip)
//    includeFilter in(Assets, LessKeys.less) := "main.less",
//    LessKeys.compress in Assets := true // compress CSS
  )
  //.enablePlugins(PlayScala)
  //.disablePlugins(PlayLayoutPlugin) // use the standard directory layout instead of Play's custom
  .aggregate(clients.map(projectToRef): _*)
  .dependsOn(sharedJVM)

//name := "spring-scala"
//
//version := Versions.appVersion
//
//scalaVersion := Versions.scalaVersion


// Command for building a release
lazy val ReleaseCmd = Command.command("release") {
  state =>
    "set elideOptions in client := Seq(\"-Xelide-below\", \"WARNING\")" ::
      "client/clean" ::
      "client/test" ::
      "server/clean" ::
      "server/test" ::
      "server/dist" ::
      "set elideOptions in client := Seq()" ::
      state
}




libraryDependencies += "org.springframework.boot" % "spring-boot-starter-web" % "1.5.1.RELEASE"
//libraryDependencies += "org.springframework.boot" % "spring-boot-starter-test" % "1.5.1.RELEASE"
//libraryDependencies += "org.springframework.boot" % "spring-boot-starter-tomcat" % "1.5.1.RELEASE"
//libraryDependencies += "org.springframework" % "spring-web" % "3.0.4.RELEASE"
//libraryDependencies += "org.springframework" % "spring-webmvc" % "5.0.0.M5"
// set the main class for 'sbt run'
mainClass in(Compile, run) := Some("ApplicationBoot")