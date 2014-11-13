/**
 * Created by nikolatonkev on 14-11-05.
 */

import scala.collection._

case class Person (val name: String, val family: String, val age: Int)

class ListHelper {

  def ExtendList(l1: List[Person], l2: List[Person]): List[Person] = {
    return l1:::(l2)
  }

  def LoopPersons(persons: List[Person]): Unit = {
    Console.println("\n")
    for(p <- persons){
      Console.println(p)
    }
  }

}
