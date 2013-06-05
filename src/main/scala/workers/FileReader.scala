package workers

import akka.actor.{ActorRef, Actor}
import workers.Splitter.Handle
import scala.io.Source

class FileReader(splitter: ActorRef) extends Actor {
  def receive = {
    case path: String => {
      Source.fromFile(path).getLines().foreach {
        line => splitter ! Handle(line)
      }
    }
  }
}
