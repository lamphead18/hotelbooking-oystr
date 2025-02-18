package com.hotel.booking.domain

import java.time.LocalDateTime

case class Reservation(id: Option[Int], roomId: Int, guestName: String, startTime: LocalDateTime, endTime: LocalDateTime)
