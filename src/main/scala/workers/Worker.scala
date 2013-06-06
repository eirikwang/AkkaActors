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

  case class Unknown(oper:String) extends Oper

}

class Worker() extends Actor {
  var count = 0L

  def receive = {
    case o: Add => change(count +o.amount)
    case o: Subtract => change(count-o.amount)
    case o: Sqt => change(Math.sqrt(count).toLong)
    case o: Get => sender ! count
    case o: Unknown => throw new HandleException(s"Kan ikke håndtere denne typen melding <$o>")
    case o: String => throw new UnexpectedException(s"Håndterer ikke lenger meldinger av type String <$o>")
  }

  private def change(newVal:Long) {
    count = newVal
    context.actorFor("/user/cnt") ! Tick
  }

}
