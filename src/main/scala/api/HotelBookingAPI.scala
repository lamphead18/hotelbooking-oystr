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
import slick.jdbc.PostgresProfile.api._
import scala.io.StdIn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Failure}

object HotelBookingAPI extends App {
  implicit val system: ActorSystem = ActorSystem("hotel-booking")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  val db = Database.forConfig("slick.db")
  val roomRepo = new RoomRepository(db)
  val reservationRepo = new ReservationRepository(db)
  val factory = new DefaultReservationFactory()
  val reservationService = new ReservationService(factory, reservationRepo)
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  val route = concat(
    path("addRoom") {
      post {
        parameter("id", "capacity".as[Int]) { (id, capacity) =>
          onComplete(roomRepo.addRoom(Room(id, capacity))) {
            case _ => complete(StatusCodes.OK, s"Quarto $id adicionado!")
          }
        }
      }
    },
    path("bookRoom") {
      post {
        parameter("guest", "email", "roomId", "startDate", "endDate") { (name, email, roomId, startDate, endDate) =>
          val parsedStartDate = LocalDateTime.parse(startDate, formatter)
          val parsedEndDate = LocalDateTime.parse(endDate, formatter)

          onComplete(roomRepo.getRoom(roomId)) {
            case Success(Some(room)) =>
              onComplete(reservationService.bookRoom(Guest(name, email), room, parsedStartDate, parsedEndDate)) {
                case Success(Some(_)) => complete(StatusCodes.OK, "Reserva confirmada!")
                case Success(None)    => complete(StatusCodes.Conflict, "Quarto não disponível!")
                case Failure(ex)      => complete(StatusCodes.InternalServerError, s"Erro interno: ${ex.getMessage}")
              }
            case Success(None) => complete(StatusCodes.NotFound, "Quarto não encontrado!")
            case Failure(ex)   => complete(StatusCodes.InternalServerError, s"Erro interno: ${ex.getMessage}")
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
