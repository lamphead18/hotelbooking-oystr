package com.hotel.booking.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.hotel.booking.domain.Reservation
import com.hotel.booking.services.ReservationService
import akka.http.scaladsl.model.StatusCodes
import spray.json._
import java.time.LocalDateTime
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import scala.concurrent.ExecutionContext

class ReservationRoutes(reservationService: ReservationService)(implicit ec: ExecutionContext) extends JsonSupport {
  val route: Route =
    pathPrefix("reservations") {
      concat(
        pathEnd {
          post {
            entity(as[Reservation]) { reservation =>
              onComplete(reservationService.bookReservation(reservation)) {
                case scala.util.Success(Right(savedReservation)) => complete(StatusCodes.Created, savedReservation)
                case scala.util.Success(Left(errorMessage))      => complete(StatusCodes.BadRequest, errorMessage)
                case scala.util.Failure(ex)                     => complete(StatusCodes.InternalServerError, ex.getMessage)
              }
            }
          }
        },
        path(Segment) { date =>
          get {
            val parsedDate = LocalDateTime.parse(date)
            onComplete(reservationService.getOccupancy(parsedDate)) {
              case scala.util.Success(reservations) => complete(reservations)
              case scala.util.Failure(ex)           => complete(StatusCodes.InternalServerError, ex.getMessage)
            }
          }
        }
      )
    }
}
