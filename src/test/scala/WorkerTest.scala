import akka.actor.ActorSystem
import akka.actor.Status.Success
import org.scalatest.matchers.MustMatchers
import akka.util.Timeout
import scala.concurrent.duration._
import Worker._
import akka.testkit.{TestKit, ImplicitSender, TestActorRef}
import org.scalatest.{WordSpec, BeforeAndAfterAll}
import akka.pattern.ask

class WorkerTest extends TestKit(ActorSystem()) with ImplicitSender
with WordSpec with MustMatchers with BeforeAndAfterAll {

  implicit val timeout = Timeout(5 seconds)

  override def afterAll {
    system.shutdown()
  }

  "En arbeider skal legge til med add" in {
    val worker = TestActorRef[Worker](new Worker())
    worker ! Add(10)
    worker ! Get()
    expectMsg(10L)
  }

  "En arbeider skal subtrahere med Subtract" in {
    val worker = TestActorRef[Worker](new Worker())
    worker ! Add(10)
    worker ! Subtract(1)
    worker ! Get()
    expectMsg(9L)
  }

  "En arbeider skal kjøre rot med Sqt" in {
    val worker = TestActorRef[Worker](new Worker())
    worker ! Add(9)
    worker ! Sqt()
    worker ! Get()
    expectMsg(3L)
  }

}
