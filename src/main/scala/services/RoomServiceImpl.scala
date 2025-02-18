package com.hotel.booking.services

import com.hotel.booking.domain.Room
import com.hotel.booking.repository.RoomRepository
import scala.concurrent.{ExecutionContext, Future}

class RoomServiceImpl(roomRepository: RoomRepository)(implicit ec: ExecutionContext) extends RoomService {
  def addRoom(room: Room): Future[Int] = roomRepository.save(room)
  def removeRoom(roomId: Int): Future[Int] = roomRepository.delete(roomId)
  def listRooms(): Future[Seq[Room]] = roomRepository.findAll()
}
