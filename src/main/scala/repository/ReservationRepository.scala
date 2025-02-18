package repository

import models._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{Future, ExecutionContext}
import java.time.LocalDateTime

class ReservationTable(tag: Tag) extends Table[Reservation](tag, "reservations") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def guestName = column[String]("guest_name")
  def guestEmail = column[String]("guest_email")
  def roomId = column[String]("room_id")
  def startDate = column[LocalDateTime]("start_date")
  def endDate = column[LocalDateTime]("end_date")

  def * = (guestName, guestEmail, roomId, startDate, endDate).mapTo[Reservation]
}

class ReservationRepository(db: Database)(implicit ec: ExecutionContext) {
  private val reservations = TableQuery[ReservationTable]

  def addReservation(reservation: Reservation): Future[Int] = db.run(reservations += reservation)
  def getReservationsByRoom(roomId: String): Future[Seq[Reservation]] =
    db.run(reservations.filter(_.roomId === roomId).result)
}
