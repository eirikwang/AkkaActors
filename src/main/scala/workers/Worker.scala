package workers

import Worker._
import akka.actor.Actor
import workers.Counter.Tick

object Worker {

  trait Oper

  case class Add(amount: Long) extends Oper

  case class Subtract(amount: Long) extends Oper

  case class Sqt() extends Oper

  case class Get() extends Oper

}

class Worker() extends Actor {
  var count = 0L

  def receive = {
    case o: Add => {
      context.actorFor("/user/cnt") ! Tick
      count += o.amount
    }
    case o: Subtract => {
      context.actorFor("/cnt") ! Tick
      count -= o.amount
    }
    case o: Sqt => {
      context.actorFor("/cnt") ! Tick
      count = Math.sqrt(count).toLong
    }
    case o: Get => {
      context.actorFor("/cnt") ! Tick
      sender ! count
    }
  }
}
