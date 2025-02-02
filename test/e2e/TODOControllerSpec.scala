package e2e

import model.TODO
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.http.Status.{CREATED, OK}
import play.api.libs.json.{Json, OFormat}
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Injecting}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.util.Random

class TODOControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  implicit val todoFormat: OFormat[TODO] = Json.format[TODO]

  private val random: Random = new Random

  "TODOController" should {

    "create new TODOs" in {
      val todo = TODO(random.nextLong(), "Test TODO", "Body")
      val request = FakeRequest(POST, "/api/study/v0/user/todo/")
        .withBody(Json toJson todo)

      val response = route(app, request).get

      status(response) mustBe CREATED
      contentType(response) mustBe Some("application/json")
      contentAsJson(response) mustBe Json.obj("message" -> "TODO created", "todo" -> Json.toJson(todo))
    }

    "list all saved TODOs" in {
      val todos = (1 to 3) map (i => TODO(random.nextLong, s"TODO$i", s"body$i"))
      todos foreach { todo =>
        val request = FakeRequest(POST, "/api/study/v0/user/todo")
          .withBody(Json toJson todo)
        Await.ready(route(app, request).get, 2.seconds)
      }

      val request = FakeRequest(GET, "/api/study/v0/user/todo")
      val response = route(app, request).get

      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")
      contentAsJson(response) mustBe (Json toJson todos)
    }

    "find saved TODO by ID" in {
      val todo = TODO(random.nextLong, "test", "body")

      val addRequest = FakeRequest(POST, "/api/study/v0/user/todo")
        .withBody(Json toJson todo)
      Await.ready(route(app, addRequest).get, 2.seconds)

      val request = FakeRequest(GET, s"/api/study/v0/user/todo/${todo.id}")
      val response = route(app, request).get

      status(response) mustBe OK
      contentType(response) mustBe Some(JSON)
      contentAsJson(response) mustBe (Json toJson todo)
    }

    "return not found error if TODO does not exist" in {
      val nonExistingId = random.nextLong
      val request = FakeRequest(GET, s"/api/study/v0/user/todo/$nonExistingId")
      val response = route(app, request).get

      status(response) mustBe NOT_FOUND
      contentType(response) mustBe Some(JSON)
      contentAsJson(response) mustBe Json.obj("error" -> s"TODO with id $nonExistingId not found")
    }

    "delete existing TODO" in {
      val todo = TODO(random.nextLong, "Test Delete", "Test delete body")

      val createRequest = FakeRequest(POST, "/api/study/v0/user/todo")
        .withBody(Json toJson todo)
      val createResponse = route(app, createRequest).get
      status(createResponse) mustBe CREATED

      val deleteRequest = FakeRequest(DELETE, s"/api/study/v0/user/todo/${todo.id}")
      val deleteResponse = route(app, deleteRequest).get
      status(deleteResponse) mustBe OK

      val updatedTODOs = route(app, FakeRequest(GET, "/api/study/v0/user/todo")).get
      status(updatedTODOs) mustBe OK
      contentType(updatedTODOs) mustBe Some(JSON)
      contentAsJson(updatedTODOs) mustBe (Json toJson Nil)
    }

    "return not found if client tries to remove non-existing TODO" in {
      val nonExistingId = random.nextLong
      val response = route(app, FakeRequest(DELETE, s"/api/study/v0/user/todo/$nonExistingId")).get
      status(response) mustBe NOT_FOUND
      contentAsJson(response) mustBe Json.obj("error" -> s"TODO with id $nonExistingId not found")
    }

  }

}
