package controllers

import com.google.inject.Inject
import com.google.inject.Singleton
import dto.common.AbstractResponseDto
import dto.common.ErrorResponseDto
import dto.common.ErrorType
import dto.common.ErrorType.EntityNotFoundErr
import dto.common.MessageResponseDto
import dto.CreateTODODto
import dto.TODODto
import dto.TODOListDto
import model.TODO
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.BaseController
import play.api.mvc.ControllerComponents
import play.api.Logging
import service.TODOService

@Singleton
class TODOController @Inject() (
    val todoService: TODOService,
    val controllerComponents: ControllerComponents
) extends BaseController
    with Logging {

  def getAll: Action[AnyContent] = Action { request =>
    logger.info(s"getAll: from ${request.remoteAddress}")
    val todosDto: TODOListDto = TODOListDto.fromTodoSet(todoService.findAllTodos)
    val responseDto           = AbstractResponseDto[TODOListDto](todosDto)
    Ok(responseDto.toJson).as(JSON)
  }

  def getById(id: Long): Action[AnyContent] = Action { request =>
    logger.info(s"getById: requested ID - $id, from ${request.remoteAddress}")
    todoService.findTodoByIdOption(id) match {
      case Some(value) =>
        val responseDto = AbstractResponseDto[TODODto](TODODto.fromTodo(value))
        Ok(responseDto.toJson).as(JSON)
      case None =>
        val error = ErrorResponseDto(s"TODO with id $id not found", ErrorType.EntityNotFoundErr)
        NotFound(error.toJson).as(JSON)
    }
  }

  def create: Action[CreateTODODto] = Action(parse.json[CreateTODODto]) { request =>
    val body = request.body
    logger.info(s"create: with body - $body")
    val todoDto     = TODODto.fromTodo(todoService.createNewTodo(body))
    val responseDto = AbstractResponseDto[TODODto](todoDto)
    Created(responseDto.toJson).as(JSON)
  }

  def delete(id: Long): Action[AnyContent] = Action {
    logger.info(s"delete: requested ID - $id")
    val todosUpdated = todoService.deleteTodoById(id)
    todosUpdated match {
      case 1 => Ok(MessageResponseDto.ofSuccess.toJson).as(JSON)
      case _ =>
        val response = ErrorResponseDto(s"TODO with id $id not found", EntityNotFoundErr)
        NotFound(response.toJson).as(JSON)
    }
  }

}
