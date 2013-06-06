import akka.actor.SupervisorStrategy.Resume
import workers.Counter.Start
import workers.{Splitter, WorkerRouter, FileReader, Worker}
import akka.actor.{OneForOneStrategy, ActorRef, Props, ActorSystem}
import akka.kernel.Bootable
import scala.io.Source
import scala.concurrent.duration._


class AkkaBatchRunner extends Bootable{
  val system = ActorSystem("AkkaBatchRunner")
  val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange=1 minute){
      case ex: RuntimeException => {
        println("error " + ex)
        Resume
      }
    }

  def startup() {
    val start = System.currentTimeMillis()
    val counter = system.actorOf(Props[workers.Counter], "cnt")
    val router: ActorRef = system.actorOf(Props(new WorkerRouter(1 to 100)), "workers")
    val splitter = system.actorOf(Props(new Splitter(router)), "splitter")
    val fileReader = system.actorOf(Props(new FileReader(splitter)), "fileReader")

    counter ! Start
    fileReader ! "error.txt"
  }

  def shutdown() {
    system.shutdown()
  }
}
