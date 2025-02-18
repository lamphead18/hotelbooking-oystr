package com.hotel.booking.services

import com.hotel.booking.domain.Room
import scala.concurrent.Future

trait RoomService {
  def addRoom(room: Room): Future[Int]
  def removeRoom(roomId: Int): Future[Int]
  def listRooms(): Future[Seq[Room]]
}
