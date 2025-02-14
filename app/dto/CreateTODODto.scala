package dto

import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat

final case class CreateTODODto(title: String, body: String) extends DTO {
  override def toJson: JsValue = Json.toJson(this)
}

object CreateTODODto {
  implicit val createTodoDtoJson: OFormat[CreateTODODto] = Json.format[CreateTODODto]
}
