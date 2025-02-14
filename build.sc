import $ivy.`com.lihaoyi::mill-contrib-playlib:`
import mill._
import mill.playlib._

object scalaplaystudyapplication extends PlayModule with SingleModule {

  def scalaVersion = "2.13.16"
  def playVersion  = "3.0.6"
  def twirlVersion = "2.0.1"

  object test extends PlayTests
}
