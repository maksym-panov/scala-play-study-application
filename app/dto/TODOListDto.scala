package dto

import model.TODO
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat

final case class TODOListDto(count: Long, items: Set[TODODto]) extends DTO {
  override def toJson: JsValue = Json.toJson(this)
}

object TODOListDto {
  implicit val todoListDtoJson: OFormat[TODOListDto] = Json.format[TODOListDto]

  def fromTodoSet(todos: Set[TODO]): TODOListDto = {
    val items = todos.map(todo => TODODto.fromTodo(todo))
    TODOListDto(items.size, items)
  }

}
