package DAL

import Models.{DataEntityItem, DataEntity}
import scala.slick.jdbc.GetResult

/**
 * Created by nikolatonkev on 14-11-18.
 */
trait ImplicitDataEntity {
  implicit val getDataEntityResult = GetResult( r => DataEntity(r.nextString, r.nextString, r.nextString))
  implicit val getDataEntityItemResult = GetResult( r => DataEntityItem(r.nextString, r.nextInt, r.nextString, r.nextBoolean, r.nextString, r.nextInt, r.nextInt, r.nextInt))}
