import diode.Circuit
import diode.data.{Empty, Pot}
import diode.react.ReactConnector
import modules.hello.{HelloActionHandler, HelloModel}

// Application circuit
object SPACircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  //  // initial application model
  override protected def initialModel = RootModel(Empty)

  // combine all handlers into one
  override protected val actionHandler: SPACircuit.HandlerFunction = composeHandlers(
    new HelloActionHandler(zoomRW(_.helloModel)((m, v) => m.copy(helloModel = v)))
  )
}

// The base model of our application
case class RootModel(helloModel: Pot[HelloModel])