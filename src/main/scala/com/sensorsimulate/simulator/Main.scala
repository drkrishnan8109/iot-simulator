package com.sensorsimulate.simulator

import akka.actor.ActorSystem

object Main extends App {

  implicit val system: ActorSystem = ActorSystem("SimulationSystem")
  val service = new SimulatorService(system);
}
