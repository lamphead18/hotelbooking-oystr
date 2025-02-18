package factory

import models._
import java.time.LocalDateTime

trait ReservationFactory {
  def createReservation(guest: Guest, room: Room, startDate: LocalDateTime, endDate: LocalDateTime) :Option[Reservation]
}

class DefaultReservationFactory extends ReservationFactory {
  def createReservation(guest: Guest, room: Room, startDate: LocalDateTime, endDate: LocalDateTime): Option[Reservation] = {
    if (room.isAvailable) {
      room.isAvailable = false
      Some(Reservation(guest, room, startDate, endDate))
    } else None
  }
}