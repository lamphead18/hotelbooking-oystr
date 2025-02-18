package services

import models._
import repository._
import factory.ReservationFactory
import java.time.LocalDateTime
import scala.concurrent.{ExecutionContext, Future}

class ReservationService(factory: ReservationFactory, reservationRepo: ReservationRepository)(implicit ec: ExecutionContext) {

  def bookRoom(guest: Guest, room: Room, startDate: LocalDateTime, endDate: LocalDateTime): Future[Option[Reservation]] = {
    reservationRepo.getReservationsByRoom(room.id).map { reservations =>
      val isAvailable = reservations.forall { res =>
        val cleaningEnd = res.endDate.plusHours(4)
        startDate.isAfter(cleaningEnd)
      }

      if (isAvailable) {
        val reservation = factory.createReservation(guest, room, startDate, endDate)
        reservation.foreach(reservationRepo.addReservation)
        reservation
      } else {
        None
      }
    }
  }
}
