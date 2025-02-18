package services

import models._
import factory._
import scala.collection.mutable
import java.time.LocalDateTime

class ReservationService(factory: ReservationFactory) {
  private val reservations = mutable.ListBuffer[Reservation]()

  def bookRoom(guest: Guest, room: Room, startDate: LocalDateTime, endDate: LocalDateTime): Option[Reservation] = {
    val reservation = factory.createReservation(guest, room, startDate, endDate)
    reservation.foreach(reservations += _)
    reservation
  }
}