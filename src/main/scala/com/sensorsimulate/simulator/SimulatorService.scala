package com.sensorsimulate.simulator

import akka.actor.ActorSystem
import com.sensorsimulate.config.KafkaConf
import com.sensorsimulate.iot.ProducerActor.Produce

import scala.concurrent.duration._
import akka.actor.{ActorRef, OneForOneStrategy}
import akka.routing.{DefaultResizer, SmallestMailboxPool}
import com.sensorsimulate.iot.ProducerActor
import com.sensorsimulate.iot.DataManager

class SimulatorService(system: ActorSystem) extends KafkaConf with DataManager {

  import com.sensorsimulate.util.GsonParser

  implicit val executionContext = system.dispatcher

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
      ).props(ProducerActor.props),
      "producerPool"
    )

  // Scheduler: Every one second, a random data(corresponding to any of the 3 deviceIds) is send to the producer
  system.scheduler.schedule(0 seconds, 60 milliseconds) {
    producerPool ! Produce(kafkaTopic, GsonParser.parser.toJson(getData))
  }

}
