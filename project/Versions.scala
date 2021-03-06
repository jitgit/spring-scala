object Versions extends WebJarsVersions with ScalaJSVersions with SharedVersions with JsVersions with ScalaJVMVersions {

  val scalaVersion = "2.12.1"
  val appVersion = "0.0.1"
  val akkaHttpExtensions = "0.0.10"
}

trait ScalaJVMVersions {
  val springBoot = "1.5.1.RELEASE"
  val jacksonModuleScala = "2.8.8"
}

trait ScalaJSVersions {
  val scalajsReact = "0.11.3"
  val scalaCSS = "0.5.0"
  val scalaDom = "0.9.1"
  val diode = "1.1.0"
  val jqueryFacade = "1.0-RC6"
  val datePickerFacade = "0.8"
  val selectizeFacade = "0.12.1-0.2.1"
  val momentJSFacade = "0.1.5"
  val jQuery = "2.1.4"
  val querkiJsext = "0.7"
}

//versions for libs that are shared between client and server
trait SharedVersions {
  val upickle = "0.4.4"
  val autowire = "0.2.5"
  val booPickle = "1.2.4"
  val scalaTest = "3.0.0"
  val test = "0.11.2"
}


trait WebJarsVersions {
  val bootstrap = "3.3.6"
  val scalajsScripts = "1.0.0"
  val playWS = "2.4.3"
  val fontAwesome = "4.6.3"
  val json4s = "3.4.1"
}

trait JsVersions {
  val log4js = "1.4.13-1"
  val react = "15.3.2"
  val chartjs = "26962ce-1"
  val datePicker = "1.5.0-1"
  val selectizejs = "0.12.2"
}
