package com.hotel.booking.repository

import com.hotel.booking.domain.Reservation
import com.hotel.booking.infrastructure.DatabaseConfig
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import java.time.LocalDateTime

class ReservationTable(tag: Tag) extends Table[Reservation](tag, "reservations") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def roomId = column[Int]("room_id")
  def guestName = column[String]("guest_name")
  def startTime = column[LocalDateTime]("start_time")
  def endTime = column[LocalDateTime]("end_time")

  def * = (id.?, roomId, guestName, startTime, endTime) <> (Reservation.tupled, Reservation.unapply)
}

class ReservationRepository(implicit ec: ExecutionContext) {
  private val reservations = TableQuery[ReservationTable]

  def save(reservation: Reservation): Future[Either[String, Reservation]] = {
    val overlappingQuery = reservations.filter(r =>
      r.roomId === reservation.roomId &&
        r.startTime < reservation.endTime &&
        r.endTime > reservation.startTime
    ).exists.result

    DatabaseConfig.run(overlappingQuery).flatMap { exists =>
      if (exists) {
        Future.successful(Left("Room is already booked for this time slot."))
      } else {
        val insertQuery = (reservations returning reservations.map(_.id)) += reservation.copy(id = None)
        DatabaseConfig.run(insertQuery).map(id => Right(reservation.copy(id = Some(id))))
      }
    }
  }

  def findByDate(date: LocalDateTime): Future[Seq[Reservation]] = {
    DatabaseConfig.run(reservations.filter(r => r.startTime >= date && r.startTime < date.plusDays(1)).result)
  }
}