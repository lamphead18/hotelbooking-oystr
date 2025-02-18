package repository

import models._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent.Await
import scala.concurrent.duration._
import slick.jdbc.PostgresProfile.api._

class RoomTable(tag: Tag) extends Table[Room](tag, "rooms") {
  def id = column[String]("id", O.PrimaryKey)
  def capacity = column[Int]("capacity")
  def isAvailable = column[Boolean]("is_available", O.Default(true))

  def * = (id, capacity, isAvailable) <> (Room.tupled, Room.unapply)
}


class RoomRepository(db: Database)(implicit ec: ExecutionContext) {
  private val rooms = TableQuery[RoomTable]

  def addRoom(room: Room): Future[Int] = db.run(rooms += room)
  def removeRoom(roomId: String): Future[Int] = db.run(rooms.filter(_.id === roomId).delete)
  def getRoom(roomId: String): Future[Option[Room]] = db.run(rooms.filter(_.id === roomId).result.headOption)
}
