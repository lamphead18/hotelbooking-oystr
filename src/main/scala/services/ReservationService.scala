package com.hotel.booking.services

import com.hotel.booking.domain.Reservation
import java.time.LocalDateTime
import scala.concurrent.Future

trait ReservationService {
  def bookReservation(reservation: Reservation): Future[Either[String, Reservation]]
  def getOccupancy(day: LocalDateTime): Future[Seq[Reservation]]
}
