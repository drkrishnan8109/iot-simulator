package com.sensorsimulate.iot

import java.util.Properties
import com.sensorsimulate.config.KafkaConf

trait ProducerTrait extends KafkaConf {

  import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
  import org.apache.kafka.common.serialization.StringSerializer

  lazy val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, s"$kafkaHost:$kafkaPort")
  props.put(
    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
    classOf[StringSerializer].getCanonicalName
  )
  props.put(
    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
    classOf[StringSerializer].getCanonicalName
  )
  props.put(ProducerConfig.RETRIES_CONFIG, "5")

  lazy val producer = new KafkaProducer[String, String](props)
}
