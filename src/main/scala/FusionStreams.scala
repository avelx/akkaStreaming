import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object FusionStreams extends App {

  implicit val system = ActorSystem("Core")
  val materializer = ActorMaterializer()

  val simpleSource = Source(1 to 1000)
  val simpleSink = Sink.foreach[Int](println)

  val complexFlow = Flow[Int].map{ x =>
    Thread.sleep(1000)
    x + 1
  }

  val complexFlow2 = Flow[Int].map{ x =>
    Thread.sleep(1000)
    x * 10
  }

  simpleSource
    .via(complexFlow).async
    .via(complexFlow2).async
    .to(simpleSink)
    .run()

}