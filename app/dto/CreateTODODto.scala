package dto

import play.api.libs.json.{JsValue, Json, OFormat}

final case class CreateTODODto(title: String, body: String) extends DTO {
  override def toJson: JsValue = Json toJson this
}

object CreateTODODto {
  implicit val createTodoDtoJson: OFormat[CreateTODODto] = Json.format[CreateTODODto]
}
