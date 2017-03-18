package remote

import diode.data.Pot
import diode.{Action, ActionHandler, ModelRW}
import modules.hello.HelloActions.GetMessage

object RemoteActions {

  object FetchHelloMessage extends Action

}

case class RootModel()

class RemoteActionHandler[M](modelRW: ModelRW[M, Pot[String]]) extends ActionHandler(modelRW) {
  override def handle = {
    case GetMessage => updated(value)
  }
}
