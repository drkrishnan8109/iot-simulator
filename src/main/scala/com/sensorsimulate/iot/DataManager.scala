package com.sensorsimulate.iot

case class Location(latitude: Double, longitude: Double)
case class Data(
    deviceId: String,
    temperature: Int,
    location: Location,
    time: Long
)

trait DataManager {

  def pickDeviceID: String = {
    val rand = scala.util.Random.nextInt(3)
    if (rand == 0)
      "11c1310e-c0c2-461b-a4eb-f6bf8da2d23c"
    else if (rand == 1)
      "22c1310e-c0c2-461b-a4eb-f6bf8da2d23c"
    else
      "33c1310e-c0c2-461b-a4eb-f6bf8da2d23c"
  }

  def getData: Data = {
    import java.time.Instant
    val deviceId = pickDeviceID
    Data(
      deviceId,
      scala.util.Random.nextInt(35),
      //TODO : Randomize info for location later
      Location(52.14691120000001, 52.14691120000001),
      Instant.now().getEpochSecond()
    )
  }
}
