import akka.actor.ActorSystem
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source}

object BackpressureStreams extends App {

  implicit val system = ActorSystem("Core")

  val fastSource = Source(1 to 1000)
  val slowSink = Sink.foreach[Int] { x =>
    Thread.sleep(1000)
    println(s"Sink: $x")
  }

  val simpleFlow = Flow[Int].map { x =>
    println(s"Incoming: $x")
    x + 1
  }

//  fastSource.async
//    .via(simpleFlow).async
//    .to(slowSink)
//    .run()

  val bufferedFlow = simpleFlow.buffer(10, overflowStrategy = OverflowStrategy.dropHead)

  fastSource.async
    .via(bufferedFlow).async
    .to(slowSink)
    .run()
}
