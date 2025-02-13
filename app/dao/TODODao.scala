package dao

import com.google.inject.{Inject, Singleton}
import model.{TODO, TODOS}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TODODao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  locally(ec)

  import profile.api._

  private val todos = TableQuery[TODOS]

  def findTodoById(id: Long): Future[Option[TODO]] = {
    db run todos.filter(_.id === id).result.headOption.transactionally
  }

  def findAllTodos: Future[Seq[TODO]] = {
    db run todos.result.transactionally
  }

  def createTodo(todo: TODO): Future[TODO] = {
    val insertQuery = (todos returning todos.map(i => i)) += todo
    db run insertQuery.transactionally
  }

  def deleteTodo(id: Long): Future[Int] = {
    val todo = todos filter (_.id === id)
    db run todo.delete.transactionally
  }
  
}
