package controllers

import com.google.inject.{Inject, Singleton}
import play.api.mvc._

@Singleton
class PlatformController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def health(): Action[AnyContent] = Action { _ =>
    Ok[String]("{ \"status\": \"HEALTHY\" }").as("application/json")
  }
}
