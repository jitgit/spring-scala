package modules.hello

import diode.data.{Pot, Ready}
import diode.{Action, ActionHandler, ModelRW}
import modules.hello.HelloActions.{FetchMessage, GetMessage}
import org.scalajs.dom.ext.Ajax

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.util.{Failure, Success}

object HelloActions {

  object GetMessage extends Action

  object FetchMessage extends Action

}

class HelloActionHandler[M](modelRW: ModelRW[M, Pot[HelloModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    //case GetMessage => updated(Ready(HelloModel("Hello React !!")))
    case FetchMessage =>
      val f = Ajax.get("/hi")
      f.onComplete {
        case Success(xhr) =>
          val json = js.JSON.parse(xhr.responseText)
          val title = json.title.toString
          val body = json.body.toString
          println(body)
          updated(Ready(HelloModel(body)))
        case Failure(e) => println(e.toString)
          updated(Ready(HelloModel("Error")))
      }
      updated(value)
  }
}

case class HelloModel(message: String)
