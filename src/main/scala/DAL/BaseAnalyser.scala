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


abstract class BaseAnalyser(dataSrcType: DataSrcType, val connStrSettings: Map[String, String]) {
  def getEntities(): Seq[DataEntity]
  def getEntityItems(entity: String): Seq[DataEntityItem]

}


object BaseAnalyser {
  def createPostgreSqlAnalyser(connStrSettings: Map[String, String]): PostgreSqlAnalyser = {
    return new PostgreSqlAnalyser(connStrSettings)

  }

  def createMySqlAnalyser(connStrSettings: Map[String, String]): MySqlAnalyser = {
    return new MySqlAnalyser(connStrSettings)

  }

  def apply(dataSrcType: DataSrcType, connStrSettings: Map[String, String]) = dataSrcType match {
    case DataSrcType.dstPostgresql => createPostgreSqlAnalyser(connStrSettings)
    case DataSrcType.dstMySQL => createMySqlAnalyser(connStrSettings)
  }
}