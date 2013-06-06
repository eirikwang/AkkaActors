package workers

import workers.Worker.{Unknown, Sqt, Subtract, Add}
import akka.actor.{OneForOneStrategy, Props, ActorRef, Actor}
import workers.Splitter.Handle
import workers.WorkerRouter.Route
import akka.actor.SupervisorStrategy.{Restart, Escalate, Resume}
import scala.concurrent.duration._
import akka.routing.SmallestMailboxRouter

object Splitter {

  case class Handle(line: String) {
    val split = line.split(" ")

    def oper = split(0)

    def actor = split(1).toInt

    def amount = split(2).toLong

  }

}

class Splitter(router: ActorRef) extends Actor {
  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: NumberFormatException => {
        Restart
      }
      case _: Any => {
        Resume
      }
    }
  var splitter = context.actorOf(Props(new ActualSplitter(router))
    .withRouter(SmallestMailboxRouter(nrOfInstances = 5)))

  def receive = {
    case obj: Any => splitter forward obj
  }
}

class ActualSplitter(router: ActorRef) extends Actor {
  var count = 0

  def receive = {
    case parsed: Handle => {
      count += 1
      println(parsed.line)
      parsed.oper match {
        case "ADD" => router ! Route(Add(parsed.amount), parsed.actor)
        case "SUBTRACT" => router ! Route(Subtract(parsed.amount), parsed.actor)
        case "SQT" => router ! Route(Sqt(), parsed.actor)
        case o: String => router ! Route(Unknown(o), parsed.actor)
      }
    }
  }

  override def preRestart(reason: Throwable, message: Option[Any]) {
    println(s"Kunne ikke parse: $message");
    super.preRestart(reason, message)
  }


  override def postStop() {
    println(s"har stoppet meg selv: ${self.path}")
    super.postStop()
  }

  override def postRestart(reason: Throwable) {
    println(s"Har restartet meg selv: ${self.path}")
    super.postRestart(reason)
  }
}
