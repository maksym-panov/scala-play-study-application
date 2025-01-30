package controllers

import model.TODO
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.collection.mutable.ListBuffer

@Singleton
class TODOController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  implicit private val todoListJson: OFormat[TODO] = Json.format[TODO]

  private val db: ListBuffer[TODO] = ListBuffer[TODO]()

  def getAll(): Action[AnyContent] = Action {
    Ok(Json toJson db) as "application/json"
  }

  def getById(id: Long): Action[AnyContent] = Action {
    db find (_.id == id) match {
      case Some(value) => Ok(Json toJson value) as "application/json"
      case None => NotFound
    }
  }

  def create(): Action[AnyContent] = Action { implicit request =>
    val jsonBody = request.body.asJson
    jsonBody flatMap (Json.fromJson(_).asOpt) match {
      case Some(value) =>
        db addOne value
        Created
      case None => BadRequest
    }
  }

  def delete(id: Long): Action[AnyContent] = Action {
    db find (_.id == id) match {
      case Some(value) => db remove (db indexOf value)
    }
    Ok
  }

}
