import akka.actor.ActorSystem
import akka.kernel.Bootable

class AkkaBatchRunner extends Bootable {
  val system = ActorSystem("AkkaBatchRunner")

  def startup() {

  }

  def shutdown() {
    system.shutdown()
  }
}
