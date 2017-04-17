package modules.hello

import diode.data.{Pot, Ready}
import diode.{Action, ActionHandler, Effect, ModelRW}
import modules.hello.HelloActions.{FetchMessage, GetMessage}
import org.devd.scala.Hello
import org.scalajs.dom.ext.Ajax
import upickle.default._

import scala.concurrent.ExecutionContext.Implicits.global

object HelloActions {

  case class GetMessage(h: Hello) extends Action

  object FetchMessage extends Action

}

class HelloActionHandler[M](modelRW: ModelRW[M, Pot[HelloModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case GetMessage(h) =>
      updated(Ready(HelloModel(h)))
    case FetchMessage =>
      effectOnly(Effect(Ajax.get("/hi").map(r => {
        GetMessage(read[Hello](r.responseText))
      })))
  }
}

case class HelloModel(hello: Hello)
