package com.example.simulator

import akka.actor.ActorSystem
import com.example.config.KafkaConf
import com.example.iot.ProducerActor.Produce
import scala.concurrent.duration._
import akka.actor.{ActorRef, OneForOneStrategy}
import akka.routing.{DefaultResizer, SmallestMailboxPool}
import com.example.iot.ProducerActor

class SimulatorService(system: ActorSystem) extends KafkaConf {

  import com.example.iot.DataManager

  // Scheduler: Every one second, a random data(corresponding to any of the 3 deviceIds) is send to the producer

  implicit val executionContext = system.dispatcher
  /*val data =
    s"DATA #uid ${block.id.toString} #time ${block.timestamp} #trans ${trans.size} @blockz ^minerz digsig"
   */
  val data = " "

  val resizer = DefaultResizer(lowerBound = 1, upperBound = 3)
  val supervisorStrategy = OneForOneStrategy() {
    case e =>
      import akka.actor.SupervisorStrategy
      SupervisorStrategy.Restart
  }

  val producerPool: ActorRef =
    system.actorOf(
      SmallestMailboxPool(
        5,
        Some(resizer),
        supervisorStrategy = supervisorStrategy
      ).props(ProducerActor.props) s,
      "producerPool"
    )

  val data = DataManager.getData

  system.scheduler.scheduleOnce(60 milliseconds) {
    producerPool ! Produce(kafkaTopic, data.toString)
  }

}
