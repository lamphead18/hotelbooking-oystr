package com.hotel.booking.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.hotel.booking.domain.Room
import com.hotel.booking.services.RoomService
import akka.http.scaladsl.model.StatusCodes
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import scala.concurrent.ExecutionContext

class RoomRoutes(roomService: RoomService)(implicit ec: ExecutionContext) extends JsonSupport {
  val route: Route =
    pathPrefix("rooms") {
      concat(
        pathEnd {
          get {
            onComplete(roomService.listRooms()) {
              case scala.util.Success(rooms) => complete(rooms)
              case scala.util.Failure(ex)    => complete(StatusCodes.InternalServerError, ex.getMessage)
            }
          } ~
            post {
              entity(as[Room]) { room =>
                onComplete(roomService.addRoom(room)) {
                  case scala.util.Success(_) => complete(StatusCodes.Created, "Room added")
                  case scala.util.Failure(ex) => complete(StatusCodes.InternalServerError, ex.getMessage)
                }
              }
            }
        },
        path(IntNumber) { id =>
          delete {
            onComplete(roomService.removeRoom(id)) {
              case scala.util.Success(_) => complete(StatusCodes.OK, "Room removed")
              case scala.util.Failure(ex) => complete(StatusCodes.InternalServerError, ex.getMessage)
            }
          }
        }
      )
    }
}
