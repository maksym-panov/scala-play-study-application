package dto.common

import dto.DTO
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat

final case class ErrorResponseDto(error: String, errorType: ErrorType) extends DTO {
  override def toJson: JsValue = Json.toJson(this)
}

object ErrorResponseDto {
  implicit val errorResponseToJson: OFormat[ErrorResponseDto] = Json.format[ErrorResponseDto]
}
