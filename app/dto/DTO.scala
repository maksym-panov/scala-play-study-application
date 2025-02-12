package dto

import play.api.libs.json.{JsValue, Json}

trait DTO {
  def toJson: JsValue
}
