import sbt._

/**
  * Application settings. Configure the build for your application here.
  * You normally don't have to touch the actual build definition after this.
  */
object Settings {
  /** The name of your application */
  val name = "scalajs-spa"

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
  val jvmDependencies = Seq(
    "org.springframework.boot" % "spring-boot-starter-web" % Versions.springBoot
  )

  /** Dependencies only used by the JS project (note the use of %%% instead of %%) */
  val scalajsDependencies = Seq(
    //    "com.github.japgolly.scalajs-react" %%% "core" % versions.scalajsReact,
  )

  /** Dependencies for external JS libs that are bundled into a single .js file according to dependency order */
  val jsDependencies = Seq(
    //    "org.webjars.bower" % "react" % versions.react / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
  )
}
