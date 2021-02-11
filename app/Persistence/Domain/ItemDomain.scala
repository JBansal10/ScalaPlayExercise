package Persistence.Domain
import play.api.data.Form
import play.api.data.Forms._
import slick.jdbc.MySQLProfile.api._

import scala.collection.mutable.ArrayBuffer

case class Item (id: Int, iName: String, desc: String, price: BigDecimal, maker: String, warranty: String, seller: String) {

  override def toString: String = s"ID: $id, Item Name: $iName, Description: $desc, Price $price, Manufacturer: $maker, Warranty: $warranty, Seller: $seller"
}

case class Items (tag: Tag) extends Table[Item] (tag, "items") {
  def id = column[Int]("ITEM_ID", O.PrimaryKey, O.AutoInc)
  def iName = column[String]("ITEM_NAME")
  def desc = column[String]("DESC")
  def price = column[BigDecimal]("PRICE")
  def maker = column[String]("MAKER")
  def warranty = column[String]("WARRANTY")
  def seller = column[String]("SELLER")
  def * = (id, iName, desc, price, maker, warranty, seller) <> (Item.tupled, Item.unapply)
}

object ItemForm {

  val updateItemForm = Form(
    mapping(
      "id" -> number,
      "iName" -> nonEmptyText,
      "desc" -> nonEmptyText,
      "price" -> bigDecimal,
      "maker" -> nonEmptyText,
      "warranty" -> nonEmptyText,
      "seller" -> nonEmptyText
    )(Item.apply)(Item.unapply)
  )

  val itemBuffer = ArrayBuffer (
    Item(1, "Minecraft", "Game", 15.99, "Mojang", "1 year", "Microsoft"),
    Item(2, "GTA", "Another game", 8.99, "Rockstar", "30 Day", "Asda")
  )

}