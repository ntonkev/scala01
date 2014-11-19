package DAL

import scala.slick.jdbc.{PositionedParameters, SetParameter}

/**
 * Created by nikolatonkev on 14-11-18.
 */
trait ImplicitSetListAny {
  implicit object SetListAny extends SetParameter[List[Any]] {
    def apply(vList: List[Any], pp: PositionedParameters) {
      for (v <- vList)
        v match {
          case v: Int => pp.setInt(v.asInstanceOf[Int])
          case v: Option[Int] => pp.setIntOption(v.asInstanceOf[Option[Int]])
          case v: Long => pp.setLong(v.asInstanceOf[Long])
          case v: Option[Long] => pp.setLongOption(v.asInstanceOf[Option[Long]])
          case v: Boolean => pp.setBoolean(v.asInstanceOf[Boolean])
          case v: Option[Boolean] => pp.setBooleanOption(v.asInstanceOf[Option[Boolean]])
          case v: Byte => pp.setByte(v.asInstanceOf[Byte])
          case v: Option[Byte] => pp.setByteOption(v.asInstanceOf[Option[Byte]])
          case v: Double => pp.setDouble(v.asInstanceOf[Double])
          case v: Option[Double] => pp.setDoubleOption(v.asInstanceOf[Option[Double]])
          case v: Float => pp.setFloat(v.asInstanceOf[Float])
          case v: Option[Float] => pp.setFloatOption(v.asInstanceOf[Option[Float]])
          case v: Short => pp.setShort(v.asInstanceOf[Short])
          case v: Option[Short] => pp.setShortOption(v.asInstanceOf[Option[Short]])
          case v: BigDecimal => pp.setBigDecimal(v.asInstanceOf[BigDecimal])
          case v: Option[BigDecimal] => pp.setBigDecimalOption(v.asInstanceOf[Option[BigDecimal]])
          case v: String => pp.setString(v.asInstanceOf[String])
          case v: Option[String] => pp.setStringOption(v.asInstanceOf[Option[String]])
          case v: java.sql.Date => pp.setDate(v.asInstanceOf[java.sql.Date])
          case v: java.sql.Time => pp.setTime(v.asInstanceOf[java.sql.Time])
          case v: java.sql.Timestamp => pp.setTimestamp(v.asInstanceOf[java.sql.Timestamp])
        }
    }
  }
}
