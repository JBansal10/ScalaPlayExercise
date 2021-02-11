package controllers

import Persistence.DAO.ItemDAO
import Persistence.Domain.{Item, ItemForm}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.Results.{BadRequest, Redirect}

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import play.api.mvc._
import play.mvc.{Action, Controller}

import scala.concurrent.{Future, TimeoutException}
import scala.util.{Failure, Success}

@Singleton
class UpdateController @Inject()(val messagesApi: MessagesApi, environment: play.api.Environment, cc: ControllerComponents) extends Controller with I18nSupport {

  def updateList = Action.async { implicit request =>
    ItemForm.updateItemForm.bindFromRequest.fold ({ formWithErrors =>
        BadRequest(views.html.list(formWithErrors))
    }, {
      updater => updateFunc(updater)
    })
  }

  def updateFunc(item: Persistence.Domain.Item): Unit = {
    ItemDAO.update(item).onComplete {
        case Success(value) =>
          print(value)
        case Failure(error) =>
          error.printStackTrace()
      }

  }

//
//  val itemUpdate = Action(parse.form(itemForm)) { implicit request =>
//    val item = request.body
//    val updateItem  = models.Item(item.iName, item.age)
//    val id       = models.User.create(updateItem)
//    Redirect(routes.Application.home(id))
//  }


}
