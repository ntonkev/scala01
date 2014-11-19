package DAL

import scala.slick.jdbc.GetResult

/**
 * Created by nikolatonkev on 14-11-18.
 */
trait ImplicitAnyRefResult {
  implicit val getAnyRefResult = GetResult[Map[String,Any]] ( prs =>
    (1 to prs.numColumns).map(_ =>
      prs.rs.getMetaData.getColumnName(prs.currentPos+1) -> prs.nextString
    ).toMap
  )

}
