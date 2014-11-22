package DAL

import scala.util.matching.Regex

/**
 * Created by NTonkev on 11/19/2014.
 */
trait DataTypeValidator {

  def GetDataType(value: String): String = {
    val IntRegEx = """(-|\d+)""".r
    val NumericRegEx = """((-|\+)?[0-9]+(\.[0-9]+)?)+""".r
    val DoubleRegEx = """-|\d+(\.\d*)?""".r
    val DateTimeRegEx = """(\d{2})-([a-zA-Z]{3})-(\d{4})""".r
    val mdyyyyRegEx = """^([1-9]|1[0-2])[/.-]([1-9]|1[0-9]|2[0-9]|3[0-1])[/.-](19|20)\d\d$""".r
    val mmddyyyyRegEx = """^(0[1-9]|1[0-2])[/.-](0[1-9]|1[0-9]|2[0-9]|3[0-1])[/.-](19|20)\d\d$""".r
    val yyyymmddRegEx = """^((?:19|20)\d\d)[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$""".r
    val yyyymmddHHMMSSRegEx = """^((?:19|20)\d\d)[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[ T].*""".r
    val ddmyyyyRegEx = """^(0[1-9]|[12][0-9]|3[01])[- /.]([1-9]|1[0-2])[- /.](19|20)\d\d$""".r
    val ddmmyyyyRegEx = """^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[0-2])[- /.](19|20)\d\d$""".r
    val CurrencyRegEx = """(\$[0-9]+(?:\.[0-9][0-9])?)(?![\d])""".r
    val GuidRegEx = """^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$""".r

    value match {
      case IntRegEx(a) => "Integer"
      case NumericRegEx(a) => "Decimal"
      case DoubleRegEx(a) => "Double"
      case CurrencyRegEx(a) => "Currency"
      case DateTimeRegEx(day, month, year)  => "DateTime"
      case mdyyyyRegEx(month, day, year) => "Date"
      case mmddyyyyRegEx(month, day, year) => "Date"
      case yyyymmddRegEx(year, month, day) => "Date"
      case yyyymmddHHMMSSRegEx(year, mont, day) => "DateTime"
      case ddmyyyyRegEx(day, month, year) => "Date"
      case ddmmyyyyRegEx(day, month, year) => "Date"
      case GuidRegEx() => "GUID"
      case _ => "String"
    }
  }
}