package com.hotel.booking.infrastructure

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future

object DatabaseConfig {
  val db = Database.forConfig("postgres")

  def run[T](query: DBIO[T]): Future[T] = db.run(query)
}
