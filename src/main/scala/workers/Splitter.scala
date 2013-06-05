package workers

import Worker.{Sqt, Subtract, Add}
import akka.actor.{ActorRef, Actor}
import workers.Splitter.Handle
import workers.WorkerRouter.Route

object Splitter {

  case class Handle(line: String) {
    val split = line.split(" ")

    def oper = split(0)

    def actor = split(1).toInt

    def amount = split(2).toLong

  }

}

class Splitter(router: ActorRef) extends Actor {

  def receive = {
    case parsed: Handle => {
      parsed.oper match {
        case "ADD" => router ! Route(Add(parsed.amount), parsed.actor)
        case "SUBTRACT" => router ! Route(Subtract(parsed.amount), parsed.actor)
        case "SQT" => router ! Route(Sqt(), parsed.actor)
      }
    }
  }
}
