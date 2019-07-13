package com.example.iot

import akka.actor.{Actor, Props}
import com.example.iot.ProducerActor.Produce
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object ProducerActor {

  case class Produce(topic: String, senz: String)
  def props = Props(classOf[ProducerActor])
}

class ProducerActor extends Actor with ProducerTrait {
  override def receive: Receive = {
    case Produce(topic, data) =>
      // produce message
      val record = new ProducerRecord[String, String](topic, data)

      producer.send(record)

    // logger.info(s"produced message $data to $topic")
  }
}
