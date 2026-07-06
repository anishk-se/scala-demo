package scala.udemy.learn.implicits

import scala.udemy.learn.typeclass.HtmlEnrichment._

/*
 - implicitly[T] asks the compiler to find and return the implicit value of type T from the current
   implicit scope.
 - Current implicit scope means: Compiler will search
  - ✓ Local implicit values
  - ✓ Imported implicit values
  - ✓ Companion object
  - ✓ Companion object of given Type
 */
object Implicitly extends App {

  def htmlBoilerPlate[T](content: T)(implicit serializer: HTMLSerializer[T]): String = {
    s"<html><body> ${content.toHtml(serializer)} </body></html>"
  }

  def htmlSugar[T : HTMLSerializer](content: T): String = {
    // we do not have (serializer) available here to use
    s"<html><body> ${content.toHtml} </body></html>"
  }

  case class Permission(mask: String)
  implicit val defaultPermission: Permission = Permission("0744")

  // some other part of code
  val standardPermission = implicitly[Permission]

  def htmlBetterSugar[T : HTMLSerializer](content: T): String = {
    val serializer = implicitly[HTMLSerializer[T]]
    s"<html><body> ${content.toHtml(serializer)} </body></html>"
  }

}
