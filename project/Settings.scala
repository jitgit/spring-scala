import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt._

/**
  * Application settings. Configure the build for your application here.
  * You normally don't have to touch the actual build definition after this.
  */
object Settings {
  /** The name of your application */
  val name = "spring-scalajs-spa"

  /** The version of your application */
  val version = "1.1.4"

  /** Options for the scala compiler */
  val scalacOptions = Seq(
    "-Xlint",
    "-unchecked",
    "-deprecation",
    "-feature"
  )
  /**
    * These dependencies are shared between JS and JVM projects
    * the special %%% function selects the correct version for each project
    */
  val sharedDependencies = Def.setting(Seq(
    //"com.lihaoyi" %%% "autowire" % Versions.autowire
  ))

  /** Dependencies only used by the JVM project */
  val jvmDependencies = Def.setting(Seq(
    "org.springframework.boot" % "spring-boot-starter-web" % Versions.springBoot
    , "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % Versions.jacksonModuleScala
    , "com.fasterxml.jackson.core" % "jackson-databind" % Versions.jacksonModuleScala
    //,"org.scala-lang.modules" %% "scala-xml" % "1.0.6"
  ))

  /** Dependencies only used by the JS project (note the use of %%% instead of %%) */
  val scalajsDependencies = Def.setting(Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % Versions.scalajsReact,
    "com.github.japgolly.scalajs-react" %%% "extra" % Versions.scalajsReact,
    "me.chrons" %%% "diode" % Versions.diode,
    "me.chrons" %%% "diode-react" % Versions.diode,
    "org.scala-js" %%% "scalajs-dom" % Versions.scalaDom,
    "com.lihaoyi" %%% "upickle" % Versions.upickle
  ))

  /** Dependencies for external JS libs that are bundled into a single .js file according to dependency order */
  val jsDependencies = Def.setting(Seq(
    "org.webjars.bower" % "react" % Versions.react / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
    "org.webjars.bower" % "react" % Versions.react / "react-dom.js" minified "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM"
  ))
}
