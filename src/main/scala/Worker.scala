import Worker._
import akka.actor.Actor

object Worker {

  case class Handle(line: String)

  case class Add(amount: Long)

  case class Subtract(amount: Long)

  case class Sqt()

  case class Get()

}

class Worker() extends Actor {
  var count = 0L

  def receive = {
    case Handle(line: String) => {
      val oper = line.split(" ")
      oper(0) match {
        case "ADD" => self ! Add(oper(1).toLong)
        case "SUBTRACT" => self ! Subtract(oper(1).toLong)
        case "SQT" => self ! Sqt()
      }
    }
    case o: Add => count += o.amount
    case o: Subtract => count -= o.amount
    case o: Sqt => count = Math.sqrt(count).toLong
    case o: Get => sender ! count
  }
}
