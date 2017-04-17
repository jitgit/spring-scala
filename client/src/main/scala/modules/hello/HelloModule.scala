package modules.hello

import diode.data.Pot
import diode.react._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import modules.hello.HelloActions.FetchMessage

import scala.scalajs.js.timers._

object HelloModule {

  case class Props(proxy: ModelProxy[Pot[HelloModel]], model: Pot[HelloModel])

  case class State()

  class Backend($: BackendScope[Props, State]) {
    def componentDidMount(props: Props) = Callback {
      setTimeout(1000) {
        println("Timer...")
      }
    }

    def onButtonClick(props: Props) = props.proxy.dispatchCB(FetchMessage)

    def render(props: Props, s: State) = {
      println(s"props ${props.model.isReady}")
      val message = props.model.map(hm => s"${hm.hello.to} at ${hm.hello.time}").getOrElse("Not Ready")
      <.div(<.div(message), <.button(^.onClick --> onButtonClick(props), "Click Me!!"))
    }
  }

  // create the React component for To Do management
  val component = ReactComponentB[Props]("HelloReact")
    .initialState(State()) // initial state from TodoStore
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply(proxy: ModelProxy[Pot[HelloModel]]) = component(Props(proxy, proxy()))
}