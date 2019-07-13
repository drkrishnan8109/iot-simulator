package com.example.simulator

import akka.actor.ActorSystem
import com.example.iot.ProducerActor

object Main extends App {

  implicit val system: ActorSystem = ActorSystem("SimulationSystem")
  val service = new SimulatorService(system);
}
