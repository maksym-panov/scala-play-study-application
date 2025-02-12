package dto.common

import dto.DTO
import play.api.libs.json.{Format, JsObject, JsResult, JsValue, Json, OFormat}

import scala.reflect.ClassTag

case class AbstractResponseDto[T](payload: T)(implicit ct: ClassTag[T], jsonFormat: Format[T]) extends DTO {
  private val payloadType: String = ct.runtimeClass.getName

  override def toJson: JsValue = Json toJson this
}

object AbstractResponseDto {
  implicit def abstractResponseDtoToJson[T: Format](implicit ct: ClassTag[T]): OFormat[AbstractResponseDto[T]] =
    new OFormat[AbstractResponseDto[T]] {
      override def writes(dto: AbstractResponseDto[T]): JsObject = Json.obj(
        "payloadType" -> dto.payloadType,
        "payload" -> Json.toJson(dto.payload)
      )

      override def reads(json: JsValue): JsResult[AbstractResponseDto[T]] = for {
        payload <- (json \ "payload").validate[T]
      } yield AbstractResponseDto(payload)
    }
}
