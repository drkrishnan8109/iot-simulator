package com.example.simulator

import akka.actor.ActorSystem
import com.example.config.KafkaConf
import com.example.iot.ProducerActor.Produce

import scala.concurrent.duration._
import akka.actor.{ActorRef, OneForOneStrategy}
import akka.routing.{DefaultResizer, SmallestMailboxPool}
import com.example.iot.ProducerActor
import com.example.iot.DataManager
import com.example.utils.JacksonUtil

class SimulatorService(system: ActorSystem) extends KafkaConf with JacksonUtil {

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

  val data = DataManager.getData

  // Scheduler: Every one second, a random data(corresponding to any of the 3 deviceIds) is send to the producer
  system.scheduler.scheduleOnce(60 milliseconds) {
    producerPool ! Produce(kafkaTopic, mapper.writeValueAsString(data))
  }

}
