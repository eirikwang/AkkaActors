import akka.actor.ActorSystem
import akka.kernel.Bootable

class GameOfLife extends Bootable {
  val system = ActorSystem("GameOfLife")

  def startup() {

  }

  def shutdown() {
    system.shutdown()
  }
}
