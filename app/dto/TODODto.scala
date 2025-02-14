package dto

import model.TODO
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat

final case class TODODto(id: Long, title: String, body: String) extends DTO {
  override def toJson: JsValue = Json.toJson(this)
}

object TODODto {
  implicit val todoDtoJson: OFormat[TODODto] = Json.format[TODODto]

  def fromTodo(todo: TODO): TODODto = TODODto(todo.id, todo.title, todo.body)
}
