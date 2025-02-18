package com.hotel.booking.services

import com.hotel.booking.domain.Reservation
import com.hotel.booking.repository.ReservationRepository
import java.time.LocalDateTime
import scala.concurrent.{ExecutionContext, Future}

class ReservationServiceImpl(reservationRepository: ReservationRepository)(implicit ec: ExecutionContext) extends ReservationService {
  def bookReservation(reservation: Reservation): Future[Either[String, Reservation]] = {
    reservationRepository.save(reservation)
  }

  def getOccupancy(day: LocalDateTime): Future[Seq[Reservation]] = {
    reservationRepository.findByDate(day)
  }
}
