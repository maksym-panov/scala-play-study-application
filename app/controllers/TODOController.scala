package controllers

import com.google.inject.{Inject, Singleton}
import model.TODO
import play.api.Logging
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import scala.collection.mutable.ListBuffer

@Singleton
class TODOController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with Logging {

  implicit private val todoListJson: OFormat[TODO] = Json.format[TODO]

  private val db: ListBuffer[TODO] = ListBuffer[TODO]()

  def getAll: Action[AnyContent] = Action { request =>
    logger.info(s"getAll: from ${request.remoteAddress}")
    Ok(Json toJson db) as JSON
  }

  def getById(id: Long): Action[AnyContent] = Action { request =>
    logger.info(s"getById: requested ID - $id, from ${request.remoteAddress}")
    db.find(_.id == id)
      .map(todo => Ok(Json toJson todo).as(JSON))
      .getOrElse(
        NotFound(
          Json.obj("error" -> s"TODO with id $id not found" )
        )
      )
  }

  def create: Action[TODO] = Action(parse.json[TODO]) { request =>
    val todo = request.body
    logger.info(s"create: with body - $todo")
    db += todo
    Created(Json.obj("message" -> "TODO created", "todo" -> Json.toJson(todo)))
  }

  def delete(id: Long): Action[AnyContent] = Action {
    logger.info(s"delete: requested ID - $id")
    db.find(_.id == id) match {
      case Some(todo) =>
        db -= todo
        Ok(Json.obj("message" -> s"TODO with id $id deleted"))
      case None => NotFound(Json.obj("error" -> s"TODO with id $id not found"))
    }
  }

}
