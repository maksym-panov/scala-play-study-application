package controllers

import com.google.inject.Inject
import com.google.inject.Singleton
import dto.common.AbstractResponseDto
import dto.HealthResponseDto
import play.api.mvc.*

@Singleton
class PlatformController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {
  def health(): Action[AnyContent] = Action { _ =>
    val responseDto = AbstractResponseDto[HealthResponseDto](HealthResponseDto.ofHealthy)
    Ok(responseDto.toJson).as(JSON)
  }
}
