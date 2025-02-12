package dto.common

import play.api.libs.json.{Format, JsResult, JsString, JsSuccess, JsValue}

enum ErrorType {
  case InvalidRequestBodyErr
  case EntityNotFoundErr
}


object ErrorType {
  implicit val errorTypeFormat: Format[ErrorType] = new Format[ErrorType] {
    def writes(errorType: ErrorType): JsValue = JsString(errorType.toString)
    def reads(json: JsValue): JsResult[ErrorType] = json.validate[String].flatMap { v =>
      JsSuccess(ErrorType valueOf v)
    }
  }
}