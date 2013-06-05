import akka.actor.Actor

class Cell(var startState: Boolean) extends Actor {
  var state = startState

  def receive = {
    case "" =>
  }
}
