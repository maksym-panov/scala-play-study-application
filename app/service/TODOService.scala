package service

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits._

import com.google.inject.Inject
import com.google.inject.Singleton
import dao.TODODao
import dto.CreateTODODto
import model.TODO

@Singleton
class TODOService @Inject() (val todoDao: TODODao) {

  def findAllTodos: Set[TODO] = {
    val todosFuture = todoDao.findAllTodos.map(_.toSet)
    Await.result(todosFuture, 2.seconds)
  }

  def findTodoByIdOption(id: Long): Option[TODO] = {
    Await.result(todoDao.findTodoById(id), 2.second)
  }

  def createNewTodo(dto: CreateTODODto): TODO = {
    Await.result(todoDao.createTodo(TODO(0, dto.title, dto.body)), 2.seconds)
  }

  def deleteTodoById(id: Long): Int = {
    Await.result(todoDao.deleteTodo(id), 2.seconds)
  }

}
