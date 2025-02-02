package model

import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api.*

case class TODO(id: Long, title: String, body: String)

class TODOS(tag: Tag) extends Table[TODO](tag, "TODO") {
  def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def title: Rep[String] = column[String]("TITLE")
  def body: Rep[String] = column[String]("BODY")

  def * : ProvenShape[TODO] = (id, title, body) <> (TODO.apply, TODO.unapply)
}

