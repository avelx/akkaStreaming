import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object FirstPrinciples extends App {

  implicit val system = ActorSystem("Core")
  val materializer = ActorMaterializer()

  val source = Source(1 to 10)
  val sink = Sink.foreach[Int](println)

  // create graph
  val graph = source.to(sink)
  //graph.run()

  val flow = Flow[Int].map(x => x + 1)

  val sourceWithFlow = source.via(flow)
  val flowWithSink = flow.to(sink)

  sourceWithFlow.to(flowWithSink).run()
}
