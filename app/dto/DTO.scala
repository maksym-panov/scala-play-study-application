package dto

import play.api.libs.json.JsValue

trait DTO {
  def toJson: JsValue
}
