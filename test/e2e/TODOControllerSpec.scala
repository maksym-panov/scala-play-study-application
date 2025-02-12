package e2e

import dto.common.ErrorType.EntityNotFoundErr
import dto.common.{AbstractResponseDto, ErrorResponseDto}
import dto.{CreateTODODto, TODODto, TODOListDto}
import model.TODO
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json, OFormat}
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Injecting}

import scala.util.Random

class TODOControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  implicit val todoFormat: OFormat[TODO] = Json.format[TODO]

  private val random: Random = new Random

  private def createTodoViaRequest(dto: CreateTODODto): Option[TODODto] = {
    val request = FakeRequest(POST, "/api/study/v0/user/todo/")
      .withBody(dto.toJson)
    contentAsJson(route(app, request).get).asOpt[AbstractResponseDto[TODODto]]
      .map(_.payload)
  }

  "TODOController" should {

    "create new TODOs" in {
      val dto = CreateTODODto("Test TODO", "Body")
      createTodoViaRequest(dto) match {
        case Some(value) =>
          value.id must be > 0L
          value.title mustBe dto.title
          value.body mustBe dto.body
        case None => fail("Invalid JSON response - None")
      }
    }

    "list all saved TODOs" in {
      val dtos = (1 to 3) map (i => CreateTODODto(s"TODO$i", s"body$i"))
      val createdIds = dtos.map(dto => FakeRequest(POST, "/api/study/v0/user/todo").withBody(Json toJson dto))
        .map(request => contentAsJson(route(app, request).get))
        .map(opt => opt.asOpt[AbstractResponseDto[TODODto]])
        .map(opt => opt.map(_.payload.id))
        .filter(_.isDefined)
        .map(_.get)

      createdIds.length mustEqual 3

      val request = FakeRequest(GET, "/api/study/v0/user/todo")
      val response = route(app, request).get

      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")

      val todoListOpt: Option[TODOListDto] = contentAsJson(response)
        .asOpt[AbstractResponseDto[TODOListDto]]
        .map(_.payload)

      todoListOpt match {
        case Some(TODOListDto(_, items)) =>
          val matchingTodos = items.map(_.id)
            .filter(createdIds contains _)
          matchingTodos.size mustEqual 3
        case None => fail("Response JSON is not a valid list of TODOs")
      }
    }

    "find saved TODO by ID" in {
      val dto = CreateTODODto("test", "body")

      val todoId: Long = createTodoViaRequest(dto).map(_.id).getOrElse(-1)
      todoId mustNot be < 0L

      val request = FakeRequest(GET, s"/api/study/v0/user/todo/$todoId")
      val response = route(app, request).get

      status(response) mustBe OK
      contentType(response) mustBe Some(JSON)

      val todoOpt: Option[TODODto] = contentAsJson(response)
        .asOpt[AbstractResponseDto[TODODto]]
        .map(_.payload)

      todoOpt match {
        case Some(value) =>
          value.id mustEqual todoId
          value.title mustEqual dto.title
          value.body mustEqual dto.body
        case None => fail("Response JSON is not a valid list of TODOs")
      }
    }

    "return not found error if TODO does not exist" in {
      val nonExistingId = random.nextLong
      val request = FakeRequest(GET, s"/api/study/v0/user/todo/$nonExistingId")
      val response = route(app, request).get

      status(response) mustBe NOT_FOUND
      contentType(response) mustBe Some(JSON)
      contentAsJson(response) mustBe ErrorResponseDto(s"TODO with id $nonExistingId not found", EntityNotFoundErr).toJson
    }

    "delete existing TODO" in {
      val dto = CreateTODODto("Test Delete", "Test delete body")
      val todoId: Long = createTodoViaRequest(dto).map(_.id).getOrElse(-1)
      todoId mustNot be < 0L

      val deleteRequest = FakeRequest(DELETE, s"/api/study/v0/user/todo/$todoId")
      val deleteResponse = route(app, deleteRequest).get
      status(deleteResponse) mustBe OK

      val getRequest = FakeRequest(GET, s"/api/study/v0/user/todo/$todoId")
      val getResponse: Option[ErrorResponseDto] = contentAsJson(route(app, getRequest).get)
        .asOpt[ErrorResponseDto]
      getResponse match {
        case Some(value) => value mustEqual ErrorResponseDto(s"TODO with id $todoId not found", EntityNotFoundErr)
        case None => fail("Unexpected response")
      }
    }

    "return not found if client tries to remove non-existing TODO" in {
      val nonExistingId = random.nextLong
      val request = FakeRequest(DELETE, s"/api/study/v0/user/todo/$nonExistingId")
      val response = route(app, request).get
      status(response) mustBe NOT_FOUND
      contentAsJson(response) mustEqual ErrorResponseDto(s"TODO with id $nonExistingId not found", EntityNotFoundErr).toJson
    }

  }

}
