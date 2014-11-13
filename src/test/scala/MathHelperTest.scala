/**
 * Created by nikolatonkev on 14-10-28.
 */

import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec


class MathHelperTest extends FlatSpec with MockFactory {

  "summary of two integers" should "have result 11" in {
    val m = new MathHelper()
    val x: Int = 6
    val y: Int = 5
    val result: Int = m.add(x, y)
    assert(result === 11)
  }
}


/*
object ArithmeticSpec extends Specification {
  "Arithmetic" should {
    "summary of two integers" in {
      val m = new MathHelper()
      val x: Int = 6
      val y: Int = 5
      val result: Int = m.add(x, y)
      result mustEqual 11
    }
  }
}
*/