package factory

import models._
import java.time.LocalDateTime

trait ReservationFactory {
  def createReservation(guest: Guest, room: Room, startDate: LocalDateTime, endDate: LocalDateTime) :Option[Reservation]
}

class DefaultReservationFactory extends ReservationFactory {
  def createReservation(guest: Guest, room: Room, startDate: LocalDateTime, endDate: LocalDateTime): Option[Reservation] = {
    Some(Reservation(guest.name, guest.email, room.id, startDate, endDate))
  }
}
