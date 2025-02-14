package e2e

import controllers.PlatformController
import dto.HealthResponseDto
import dto.common.AbstractResponseDto
import org.scalatestplus.play.*
import org.scalatestplus.play.guice.*
import play.api.test.*
import play.api.test.Helpers.*

// https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
class PlatformControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "PlatformController GET" should {

    "return health status from a new instance of controller" in {
      val controller = new PlatformController(stubControllerComponents())
      val health     = controller.health().apply(FakeRequest(GET, "/api/study/v0/public/platform/health"))

      status(health) mustBe OK
      contentType(health) mustBe Some("application/json")
      contentAsJson(health) mustEqual AbstractResponseDto(HealthResponseDto.ofHealthy).toJson
    }

    "return health status from the application" in {
      val controller = inject[PlatformController]
      val health     = controller.health().apply(FakeRequest(GET, "/api/study/v0/public/platform/health"))

      status(health) mustBe OK
      contentType(health) mustBe Some("application/json")
      contentAsJson(health) mustEqual AbstractResponseDto(HealthResponseDto.ofHealthy).toJson
    }

    "return health status from the router" in {
      val request = FakeRequest(GET, "/api/study/v0/public/platform/health")
      val health  = route(app, request).get

      status(health) mustBe OK
      contentType(health) mustBe Some("application/json")
      contentAsJson(health) mustEqual AbstractResponseDto(HealthResponseDto.ofHealthy).toJson
    }
  }
}
