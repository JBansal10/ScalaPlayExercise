package Persistence.DAO

import Persistence.Domain.{Item, Items}
import slick.jdbc.MySQLProfile.backend.Database
import slick.lifted.TableQuery

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.MySQLProfile.api._

object ItemDAO {

  lazy val db = Database.forConfig("mysqlDB")
  lazy val itemTable: TableQuery[Items] = TableQuery[Items]

  // THIS WILL READ EVERYTHING
  def readAll(): Future[Seq[Item]] = db.run(itemTable.result)

  def update(item: Item): Future[String] = {
    db.run(itemTable.insertOrUpdate(item)).map(res => "Item successfully added").recover {
      case ex: Exception =>
        ex.getCause.getMessage
    }
  }

  def delete(id: Int): Future[Int] = db.run(itemTable.filter(_.id === id).delete)

}