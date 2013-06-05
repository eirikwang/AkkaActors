package workers

import akka.actor.ActorSystem
import org.scalatest.matchers.MustMatchers
import workers.Splitter.Handle
import workers.Worker.{Sqt, Subtract, Add}
import workers.WorkerRouter.Route
import akka.testkit._
import org.scalatest.{WordSpec, BeforeAndAfterAll}

class SplitterTest extends TestKit(ActorSystem()) with ImplicitSender
with WordSpec with MustMatchers with BeforeAndAfterAll with DefaultTimeout {

  "Splitteren skal sende add til workeren" in {
    val probe = TestProbe()
    val splitter = TestActorRef[Splitter](new Splitter(probe.ref))
    splitter ! Handle("ADD 1 2")
    probe.expectMsg(Route(Add(2), 1))
  }

  "Splitteren skal sende subtract til workeren" in {
    val probe = TestProbe()
    val splitter = TestActorRef[Splitter](new Splitter(probe.ref))
    splitter ! Handle("SUBTRACT 1 2")
    probe.expectMsg(Route(Subtract(2), 1))
  }

  "Splitteren skal sende sqt til workeren" in {
    val probe = TestProbe()
    val splitter = TestActorRef[Splitter](new Splitter(probe.ref))
    splitter ! Handle("SQT 1")
    probe.expectMsg(Route(Sqt(), 1))
  }


}
