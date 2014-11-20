package DAL

/**
 * Created by NTonkev on 11/19/2014.
 */
trait DataTypeValidator {
  //def GetDataType(value: String): String
  def GetDataType(value: String): String = {
    val IntRegEx = "(\\d+)".r
    val NumericRegEx = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+".r //"([0-9]*)\\.[0]".r
    val DoubleRegEx = "\\d+(\\.\\d*)?".r
    //val TimeRegEx = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$".r
    val MoneyRegEx = "^\\$?[0-9]+(,[0-9]{3})*(\\.[0-9]{2})?$ ".r
    value match  {
      case IntRegEx(num) => "Integer"
      case NumericRegEx(num) => "Decimal"
      case DoubleRegEx(num) => "Double"
      case MoneyRegEx(num) => "Money"
      //case TimeRegEx(num) => "Time"
      case _ => "String"
    }
  }
}

//object DataTypeValidator{
//}
