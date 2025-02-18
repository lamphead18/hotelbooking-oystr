package com.hotel.booking.infrastructure

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.Materializer
import com.hotel.booking.api.{RoomRoutes, ReservationRoutes}
import com.hotel.booking.repository.{RoomRepository, ReservationRepository}
import com.hotel.booking.services.{RoomService, RoomServiceImpl, ReservationService, ReservationServiceImpl}
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn

object Server {
  implicit val system: ActorSystem = ActorSystem("hotel-booking-system")
  implicit val materializer: Materializer = Materializer(system)
  implicit val executionContext: scala.concurrent.ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]): Unit = {
    val roomRepo = new RoomRepository()
    val reservationRepo = new ReservationRepository()

    val roomService: RoomService = new RoomServiceImpl(roomRepo)
    val reservationService: ReservationService = new ReservationServiceImpl(reservationRepo)

    val routes = concat(
      new RoomRoutes(roomService).route,
      new ReservationRoutes(reservationService).route
    )

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes)
    println("Server online at http://localhost:8080/\nPress RETURN to stop...")

    StdIn.readLine()
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }
}
