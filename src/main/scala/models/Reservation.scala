package models

import java.time.LocalDateTime

case class Reservation(guestName: String, guestEmail: String, roomId: String, startDate: LocalDateTime, endDate: LocalDateTime)
