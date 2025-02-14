package dto.common

import dto.DTO
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat

final case class MessageResponseDto(msg: String) extends DTO {
  override def toJson: JsValue = Json.toJson(this)
}

object MessageResponseDto {
  def ofSuccess: MessageResponseDto = MessageResponseDto("SUCCESS")

  implicit val messageResponseDtoToJson: OFormat[MessageResponseDto] = Json.format[MessageResponseDto]

}
