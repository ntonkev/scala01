package Common

/**
 * Created by nikolatonkev on 14-11-21.
 */
object DataTypes extends Enumeration {
  type DataTypes = Value
  val dtString, dtBoolean, dtInteger, dtLong, dtShort, dtByte, dtDouble, dtFloat, dtDecimal, dtBigDecimal, dtDate, dtDateTime, dtTime, dtTimestamp, dtUUID, dtCurrency = Value
}
import DataTypes._

object Operators extends  Enumeration {
  type Operators = Value
  val optIsEqual, optIsDiffrent, optIsSmaller, optIsSmallerOrEqual, optIsBigger, optIsBiggerOrEqual, optIsBetween, optStartsWith, optEndsWith, optContains = Value
}
import Operators._

object OperandType extends Enumeration {
  type OperandType = Value
  val opTypeDataField, opTypeValue = Value
}
import OperandType._




/***************************************** Filter Parser *************************************/


object LexemTypes extends Enumeration {
  type LexemTypes = Value
  //Key words
  val ltKeyWordAnd = Value(14)
  val ltKeyWordOr = Value(15)
  val ltKeyWordNot = Value(16)
  //Identifier
  val ltIdentifier = Value(25)
  //Constant
  val ltValueInt = Value(26)
  //Symbols
  val ltRoundOpen = Value(33)
  val ltRoundClose = Value(34)
  //Empty Symbols
  val ltSpace = Value(49)
  val ltNull = Value(50)
  val ltDot = Value(51)
  val ltUnknown = Value(53)
}
import LexemTypes._

case class Lexem ( LexemType: LexemTypes, ConditionIndex: Int )

trait LexemParser {
  type ASCIITableProc = () => LexemTypes
}

class Operator(operatorType: Operators) extends Function2[Any, Any, Boolean]{
  val optrType = operatorType
  def IsEqual(lo: Any, ro: Any): Boolean = {
    if(lo != null && ro != null){
      return lo.equals(ro)
    }
    else
    {
      return false
    }
  }

  def IsBigger(lo: Any, ro: Any): Boolean = {
    return lo.getClass() match {
      case x => if(x.isInstance(Int)) { lo.asInstanceOf[Int] > ro.asInstanceOf[Int] } else false
      case _ => false

    }
  }

  def apply(lo: Any, ro: Any) = {
    var result: Boolean = optrType match {
      case Operators.optIsEqual => IsEqual(lo, ro)
      case Operators.optIsBigger => IsBigger(lo, ro)
      case _ => false
    }
    result
  }
}

trait Evaluator {
  type evaluate[G] = (G, G) => Boolean
}

