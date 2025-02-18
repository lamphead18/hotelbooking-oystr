package com.hotel.booking.repository

import com.hotel.booking.domain.Room
import com.hotel.booking.infrastructure.DatabaseConfig
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future

class RoomTable(tag: Tag) extends Table[Room](tag, "rooms") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")

  def * = (id, name) <> (Room.tupled, Room.unapply)
}

class RoomRepository {
  val rooms = TableQuery[RoomTable]

  def save(room: Room): Future[Int] = {
    DatabaseConfig.run(rooms += room)
  }

  def delete(roomId: Int): Future[Int] = {
    DatabaseConfig.run(rooms.filter(_.id === roomId).delete)
  }

  def findAll(): Future[Seq[Room]] = {
    DatabaseConfig.run(rooms.result)
  }
}
