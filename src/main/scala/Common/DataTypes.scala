package Common

/**
 * Created by nikolatonkev on 14-11-21.
 */
object DataTypes extends Enumeration {
  type DataTypes = Value
  val dtString, dtBoolean, dtInteger, dtLong, dtShort, dtByte, dtDouble, dtFloat, dtDecimal, dtBigDecimal, dtDate, dtDateTime, dtTime, dtTimestamp, dtUUID, dtCurrency = Value
}
import DataTypes._

