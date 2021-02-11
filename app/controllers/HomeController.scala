package controllers

import Persistence.DAO.ItemDAO
import Persistence.Domain.{Item, ItemForm}
import play.api.data.Form
import play.api.i18n.I18nSupport
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
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport{

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

//  val updatePage = Action.async { implicit request => ItemForm.updateItemForm map(idunnoYeah => Ok(views.html.list(idunnoYeah)("Potato work please")))

  def updateList() = Action { implicit request =>
    ItemForm.updateItemForm.bindFromRequest.fold ({ formsWithError =>
      BadRequest(views.html.list(formsWithError))
    }, {
      updater => updateFunc(updater)
        Redirect("/")
    })
  }

  def updateFunc(item: Item): Unit = {
    ItemDAO.update(item).onComplete {
      case Success(value) =>
        print(value)
      case Failure(error) =>
        error.printStackTrace()
    }

  }


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
