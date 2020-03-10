import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object MaterializerStreams extends App {

  implicit val system = ActorSystem("Core")
  val materializer = ActorMaterializer()

  val simpleGraph = Source(1 to 10).to(Sink.foreach(println))
  val simpleMatValue = simpleGraph.run()

}
