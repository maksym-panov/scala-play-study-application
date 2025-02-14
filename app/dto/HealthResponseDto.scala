package dto

import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class HealthResponseDto(status: String) extends DTO {
  override def toJson: JsValue = Json.toJson(this)
}

object HealthResponseDto {
  def ofHealthy   = HealthResponseDto("HEALTHY")
  def ofUnhealthy = HealthResponseDto("UNHEALTHY")

  implicit val healthResponseDtoToJson: OFormat[HealthResponseDto] = Json.format[HealthResponseDto]
}
