package repository

import models._
import scala.collection.mutable

class RoomRepository {
  private val rooms = mutable.Map[String, Room]()

  def addRoom(room: Room): Unit = rooms.put(room.id, room)
  def removeRoom(roomId: String): Unit = rooms.remove(roomId)
  def getRoom(roomId: String): Option[Room] = rooms.get(roomId)
}