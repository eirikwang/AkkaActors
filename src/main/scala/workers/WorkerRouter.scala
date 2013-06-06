package workers

import Worker.Oper
import akka.actor._
import workers.WorkerRouter.Route
import workers.WorkerRouter.Route
import scala.concurrent.duration._
import akka.actor.SupervisorStrategy.Resume

object WorkerRouter {

  case class Route(oper: Oper, account: Int)

}

class WorkerRouter(accountRange:Range) extends Actor with ActorLogging{
  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange=1 minute){
      case ex: RuntimeException => {
        println("error " + ex)
        Resume
      }
    }

  val actors = accountRange.map(account => context.actorOf(Props[Worker], "ac" + account))
  def receive = {
    case r:Route => {
      actors(r.account) forward r.oper
    }
  }
}
