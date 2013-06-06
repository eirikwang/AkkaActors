package workers

import akka.actor.Actor
import workers.Counter.{Start, Tick}

object Counter {
  case object Start
  case object Tick
}
class Counter extends Actor{
  var cnt = 0
  var start = System.currentTimeMillis()
  def receive = {
    case Start => start = System.currentTimeMillis()
    case Tick => {
      cnt+= 1
      if(cnt%100000 == 0){
        println("100000 in " + (System.currentTimeMillis() - start) + " " + cnt )
      }
    }
  }
}
