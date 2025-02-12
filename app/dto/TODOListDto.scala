package dto

import model.TODO
import play.api.libs.json.{JsValue, Json, OFormat}


case class TODOListDto(count: Long, items: Set[TODODto]) extends DTO {
  override def toJson: JsValue = Json toJson this
}

object TODOListDto {
  implicit val todoListDtoJson: OFormat[TODOListDto] = Json.format[TODOListDto]
  
  def fromTodoSet(todos: Set[TODO]) = {
    val items = todos map (todo => TODODto fromTodo todo)
    TODOListDto(items.size, items)
  }
    
}
