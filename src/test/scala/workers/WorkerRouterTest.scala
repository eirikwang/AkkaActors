package workers

import akka.actor.ActorSystem
import org.scalatest.matchers.MustMatchers
import akka.testkit.{TestActorRef, TestKit, ImplicitSender}
import org.scalatest.{WordSpec, BeforeAndAfterAll}
import workers.Worker.{Get, Add}
import workers.WorkerRouter.Route

class WorkerRouterTest extends TestKit(ActorSystem()) with ImplicitSender
with WordSpec with MustMatchers with BeforeAndAfterAll {

  "router should forward message to actor" in {
    val range = 1 to 1
    val worker = TestActorRef[WorkerRouter](new WorkerRouter(range))
    worker ! Route(Add(1), 0)
    worker ! Route(Get(), 0)
    expectMsg(1L)
  }
}
