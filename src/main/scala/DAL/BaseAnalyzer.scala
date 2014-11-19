package DAL

/**
 * Created by nikolatonkev on 14-11-15.
 */

object DataSrcType extends Enumeration {
  type DataSrcType = Value
  val dstMSSqlServer, dstMySQL, dstPostgresql, dstOracle, dstMongoDB, dstFlatFile, dstJsonFile = Value
}
import DataSrcType._
import Models.DataEntity
import Models.DataEntityItem
import scala.slick.jdbc.GetResult

abstract class BaseAnalyzer(dataSrcType: DataSrcType, val connStrSettings: Map[String, String]) extends ImplicitSetListAny {
  implicit val getDataEntityResult = GetResult( r => DataEntity(r.nextString, r.nextString, r.nextString))
  implicit val getDataEntityItemResult = GetResult( r => DataEntityItem(r.nextString, r.nextInt, r.nextString, r.nextBoolean, r.nextString, r.nextInt, r.nextInt, r.nextInt))

  def getEntities(): Seq[DataEntity]
  def getEntityItems(entity: String): Seq[DataEntityItem]

}


object BaseAnalyzer {
  def createPostgreSqlAnalyzer(connStrSettings: Map[String, String]): PostgreSqlAnalyzer = {
    return new PostgreSqlAnalyzer(connStrSettings)

  }

  def createMySqlAnalyzer(connStrSettings: Map[String, String]): MySqlAnalyzer = {
    return new MySqlAnalyzer(connStrSettings)

  }

  def apply(dataSrcType: DataSrcType, connStrSettings: Map[String, String]) = dataSrcType match {
    case DataSrcType.dstPostgresql => createPostgreSqlAnalyzer(connStrSettings)
    case DataSrcType.dstMySQL => createMySqlAnalyzer(connStrSettings)
  }
}