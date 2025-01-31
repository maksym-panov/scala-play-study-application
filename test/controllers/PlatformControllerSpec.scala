package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

// https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
class PlatformControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "PlatformController GET" should {

    "return health status from a new instance of controller" in {
      val controller = new PlatformController(stubControllerComponents())
      val health = controller.health().apply(FakeRequest(GET, "/api/study/v0/platform/health"))

      status(health) mustBe OK
      contentType(health) mustBe Some("application/json")
      contentAsString(health) must include ("{ \"status\": \"HEALTHY\" }")
    }

    "return health status from the application" in {
      val controller = inject[PlatformController]
      val health = controller.health().apply(FakeRequest(GET, "/api/study/v0/platform/health"))

      status(health) mustBe OK
      contentType(health) mustBe Some("application/json")
      contentAsString(health) must include ("{ \"status\": \"HEALTHY\" }")
    }

    "return health status from the router" in {
      val request = FakeRequest(GET, "/api/study/v0/platform/health")
      val health = route(app, request).get

      status(health) mustBe OK
      contentType(health) mustBe Some("application/json")
      contentAsString(health) must include ("{ \"status\": \"HEALTHY\" }")
    }
  }
}
