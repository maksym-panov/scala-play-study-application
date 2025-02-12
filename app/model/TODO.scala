package model

import play.api.libs.json.{Json, OFormat}
import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api.*

case class TODO(id: Long, title: String, body: String) 

class TODOS(tag: Tag) extends Table[TODO](tag, "todos") {
  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def title: Rep[String] = column[String]("title")
  def body: Rep[String] = column[String]("body")

  def * : ProvenShape[TODO] = (id, title, body) <> (TODO.apply, TODO.unapply)
}
