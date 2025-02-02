package controllers

import com.google.inject.{Inject, Singleton}
import service.TODOService
import model.TODO
import play.api.Logging
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

@Singleton
class TODOController @Inject()(
                                val todoService: TODOService,
                                val controllerComponents: ControllerComponents
                              ) extends BaseController with Logging {

  implicit private val todoListJson: OFormat[TODO] = Json.format[TODO]

  def getAll: Action[AnyContent] = Action { request =>
    logger.info(s"getAll: from ${request.remoteAddress}")
    Ok(Json toJson todoService.findAllTodos) as JSON
  }

  def getById(id: Long): Action[AnyContent] = Action { request =>
    logger.info(s"getById: requested ID - $id, from ${request.remoteAddress}")
    val todo = todoService findTodoByIdOption id
    todo.map(todo => Ok(Json toJson todo).as(JSON))
      .getOrElse(
        NotFound(
          Json.obj("error" -> s"TODO with id $id not found" )
        )
      )
  }

  def create: Action[TODO] = Action(parse.json[TODO]) { request =>
    val body = request.body
    logger.info(s"create: with body - $body")
    val todo = todoService createNewTodo body
    Created(Json.obj("message" -> "TODO created", "todo" -> Json.toJson(todo)))
  }

  def delete(id: Long): Action[AnyContent] = Action {
    logger.info(s"delete: requested ID - $id")
    todoService deleteTodoById id
    Ok(Json.obj("message" -> s"TODO with id $id deleted"))
  }

}
