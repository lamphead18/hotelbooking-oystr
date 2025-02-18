package api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import repository._
import services._
import factory._
import models._
import scala.io.StdIn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.concurrent.ExecutionContext

object HotelBookingAPI extends App {
  implicit val system: ActorSystem = ActorSystem("hotel-booking")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  val roomRepo = new RoomRepository()
  val factory = new DefaultReservationFactory()
  val reservationService = new ReservationService(factory)
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  val route = concat(
    path("addRoom") {
      post {
        parameter("id", "capacity".as[Int]) { (id, capacity) =>
          roomRepo.addRoom(Room(id, capacity))
          complete(StatusCodes.OK, s"Quarto $id adicionado!")
        }
      }
    },
    path("bookRoom") {
      post {
        parameter("guest", "email", "roomId", "startDate", "endDate") { (name, email, roomId, startDate, endDate) =>
          val parsedStartDate = LocalDateTime.parse(startDate, formatter)
          val parsedEndDate = LocalDateTime.parse(endDate, formatter)

          roomRepo.getRoom(roomId) match {
            case Some(room) =>
              reservationService.bookRoom(Guest(name, email), room, parsedStartDate, parsedEndDate) match {
                case Some(_) => complete(StatusCodes.OK, "Reserva confirmada!")
                case None    => complete(StatusCodes.Conflict, "Quarto não disponível!")
              }
            case None => complete(StatusCodes.NotFound, "Quarto não encontrado!")
          }
        }
      }
    },
    pathEndOrSingleSlash {
      complete(StatusCodes.OK, "Hotel Booking API is running!")
    }
  )

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  println("Server online on http://localhost:8080/. Press ENTER to stop.")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind())(ec).onComplete(_ => system.terminate())(ec)
}
