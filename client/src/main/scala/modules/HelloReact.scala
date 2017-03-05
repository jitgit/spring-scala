package modules

import diode.data.Pot
import diode.react._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object HelloReact {

  case class Props(proxy: ModelProxy[Pot[String]])

  case class State()

  class Backend($: BackendScope[Props, State]) {
    def componentDidMount(props: Props) = Callback.alert("componentDidMount")

    def render(props: Props, s: State) = <.div(
    )
  }

  // create the React component for To Do management
  val component = ReactComponentB[Props]("HelloReact")
    .initialState(State()) // initial state from TodoStore
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply(proxy: ModelProxy[Pot[String]]) = component(Props(proxy))
}