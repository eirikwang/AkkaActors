package workers

import Worker.Oper
import akka.actor.{ActorContext, Props, Actor}
import workers.WorkerRouter.Route

object WorkerRouter {

  case class Route(oper: Oper, account: Int)

}

class WorkerRouter(accountRange:Range) extends Actor {
  val actors = accountRange.map(account => context.actorOf(Props[Worker], "ac" + account))
  def receive = {
    case r:Route => {
      actors(r.account) forward r.oper
    }
  }
}
