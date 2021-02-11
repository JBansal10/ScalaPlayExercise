package controllers

import Persistence.DAO.ItemDAO
import Persistence.Domain.{Item, ItemForm}
import play.api.data.Form
import play.api.mvc.Results.{BadRequest, Redirect}

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import play.api.mvc._
import play.mvc.Action

import scala.concurrent.{Future, TimeoutException}
import scala.util.{Failure, Success}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
//  def index = Action {
//    Ok(views.html.index())
//  }

//  def index = Action {
//  Ok(views.html.index(items: Seq[Item], "Potato work please"))
//}
//  val formUpdater = Item.createItemForm.bindFromRequest
//  formValidationResult.fold({ formWithErrors =>
//    BadRequest(views.html.list(Item.items, formWithErrors))
//  })

//  val deleteList = Action.async { implicit request =>
//    id
//    ItemDAO.delete()
//  }

val index = Action.async { implicit request => ItemDAO.readAll() map(idunnoYeah => Ok(views.html.index(idunnoYeah)("Potato work please")))

}
  def delete(id: Int) = Action { implicit request =>

    ItemDAO.delete(id).onComplete {
      case Success(1) =>
        println("Delete worked like magic!")
      case Success(0) =>
        println("Delete didn't work like magic, make Piers get it to work like magic")
      case Failure(error: Error) =>
          error.printStackTrace()
    }
    Redirect("/")
  }
}
