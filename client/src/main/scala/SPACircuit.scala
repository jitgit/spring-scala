import diode.Circuit
import diode.data.{Empty, Pot}
import diode.react.ReactConnector

// Application circuit
object SPACircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  //  // initial application model
  override protected def initialModel = RootModel(Empty)

  // combine all handlers into one
  override protected val actionHandler = composeHandlers(
    //    new TodoHandler(zoomRW(_.todos)((m, v) => m.copy(todos = v))),
    //    new MotdHandler(zoomRW(_.motd)((m, v) => m.copy(motd = v)))
  )
}

// The base model of our application
case class RootModel(message: Pot[String])