import workers.{Splitter, WorkerRouter, FileReader, Worker}
import akka.actor.{ActorRef, Props, ActorSystem}
import akka.kernel.Bootable
import scala.io.Source

class AkkaBatchRunner extends Bootable {
  val system = ActorSystem("AkkaBatchRunner")

  def startup() {
    val start = System.currentTimeMillis()
    val counter = system.actorOf(Props[workers.Counter], "cnt")
    val router: ActorRef = system.actorOf(Props(new WorkerRouter(1 to 100)), "workers")
    val splitter = system.actorOf(Props(new Splitter(router)), "splitter")
    val fileReader = system.actorOf(Props(new FileReader(splitter)), "fileReader")

    fileReader ! "testinput.txt"

  }

  def shutdown() {
    system.shutdown()
  }
}
