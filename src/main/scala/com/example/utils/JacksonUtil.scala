package com.example.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

trait JacksonUtil extends App {
  val mapper = new ObjectMapper
  mapper.registerModule(DefaultScalaModule)
}
