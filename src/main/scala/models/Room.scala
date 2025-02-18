package models

case class Room(id: String, capacity: Int, var isAvailable: Boolean = true)