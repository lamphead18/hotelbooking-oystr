package models

import java.time.LocalDateTime

case class Reservation(guest: Guest, room: Room, startDate: LocalDateTime, endDate: LocalDateTime)