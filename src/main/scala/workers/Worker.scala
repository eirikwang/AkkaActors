package workers

import Worker._
import akka.actor.{ActorRef, Actor}

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
    case o: Add => count += o.amount
    case o: Subtract => count -= o.amount
    case o: Sqt => count = Math.sqrt(count).toLong
    case o: Get => sender ! count
  }
}
