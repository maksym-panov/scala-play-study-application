package dto.common

import scala.reflect.ClassTag

import dto.DTO
import play.api.libs.json.Format
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat

final case class AbstractResponseDto[T](payload: T)(implicit ct: ClassTag[T], jsonFormat: Format[T]) extends DTO {
  locally(ct)
  locally(jsonFormat)

  private val payloadType: String = ct.runtimeClass.getName

  override def toJson: JsValue = Json.toJson(this)
}

object AbstractResponseDto {
  implicit def abstractResponseDtoToJson[T: Format](implicit ct: ClassTag[T]): OFormat[AbstractResponseDto[T]] =
    new OFormat[AbstractResponseDto[T]] {
      locally(ct)

      override def writes(dto: AbstractResponseDto[T]): JsObject = Json.obj(
        "payloadType" -> dto.payloadType,
        "payload"     -> Json.toJson(dto.payload)
      )

      override def reads(json: JsValue): JsResult[AbstractResponseDto[T]] = for {
        payload <- (json \ "payload").validate[T]
      } yield AbstractResponseDto(payload)
    }
}
