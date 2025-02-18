package com.hotel.booking.api

import com.hotel.booking.domain.{Reservation, Room}
import spray.json._
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

trait JsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit object LocalDateTimeFormat extends JsonFormat[LocalDateTime] {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    def write(obj: LocalDateTime): JsValue = JsString(obj.format(formatter))

    def read(json: JsValue): LocalDateTime = json match {
      case JsString(s) => LocalDateTime.parse(s, formatter)
      case _           => throw new DeserializationException("Invalid datetime format")
    }
  }

  implicit val roomFormat: RootJsonFormat[Room] = jsonFormat2(Room)
  implicit val reservationFormat: RootJsonFormat[Reservation] = jsonFormat5(Reservation.apply)
}
