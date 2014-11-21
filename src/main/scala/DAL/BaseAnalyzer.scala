package DAL

/**
 * Created by nikolatonkev on 14-11-15.
 */

import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{StaticQuery => Q}
import Models.DataEntity
import Models.DataEntityItem

object DataTypes extends Enumeration {
type DataTypes = Value
val dtString, dtBoolean, dtInteger, dtLong, dtShort, dtByte, dtDouble, dtFloat, dtDecimal, dtBigDecimal, dtdDate, dtTime, dtTimestamp  = Value
}
import DataTypes._

object DataSrcType extends Enumeration {
  type DataSrcType = Value
  val dstH2, dstMSSqlServer, dstMySQL, dstPostgresql, dstOracle, dstMongoDB, dstFlatFile, dstJsonFile  = Value
}
import DataSrcType._


abstract class BaseAnalyzer(dataSrcType: DataSrcType, val connStrSettings: Map[String, String]) extends ImplicitSetListAny with ImplicitAnyRefResult {

  def getEntities(): Seq[DataEntity]
  def getEntityItems(entity: String = ""): Seq[DataEntityItem]
  def createEntity(entity: String, entityItems: Seq[DataEntityItem]): Boolean

  protected def GetDataBase(): Database = {
    return dataSrcType match {
      case DataSrcType.dstPostgresql => this.asInstanceOf[PostgreSqlAnalyzer].GetPostgresDataBase()
      case DataSrcType.dstMySQL => this.asInstanceOf[MySqlAnalyzer].GetMySqlDataBase()
      //TODO: Throw exception
      //case _ => new Exception("No data source type specifyed")
    }
  }

  protected def runQryWithoutParams(sqlStr: String): Seq[Object] ={
    val db = this.GetDataBase()
    db.withSession { implicit session => {
      Q.queryNA[AnyRef](sqlStr).list      }
    }
  }

  protected def runQryWithParams(params: List[Any], sqlStr: String): Seq[Object] = {
    val db = this.GetDataBase()
    db.withSession { implicit session => {
      val result = Q.query[List[Any] , AnyRef](sqlStr)
      result(params).list                }
    }
  }

  def executeQuery(params: List[Any], sqlStr: String): Seq[Object] = {
    return params match{
      case null => runQryWithoutParams(sqlStr)
      case _ => runQryWithParams(params, sqlStr)
    }
  }
}


object BaseAnalyzer {
  def createH2Analyzer(connStrSettings: Map[String, String]): H2Analyzer = {
    return new H2Analyzer(connStrSettings)
  }

  def createFlatFileAnalyzer(connStrSettings: Map[String, String]): FlatFileAnalyzer = {
    return new FlatFileAnalyzer(connStrSettings)
  }

  def createJsonAnalyzer(connStrSettings: Map[String, String]): JsonAnalyzer = {
    return new JsonAnalyzer(connStrSettings)
  }

  def createPostgreSqlAnalyzer(connStrSettings: Map[String, String]): PostgreSqlAnalyzer = {
    return new PostgreSqlAnalyzer(connStrSettings)
  }

  def createMySqlAnalyzer(connStrSettings: Map[String, String]): MySqlAnalyzer = {
    return new MySqlAnalyzer(connStrSettings)
  }

  def apply(dataSrcType: DataSrcType, connStrSettings: Map[String, String]) = dataSrcType match {
    case DataSrcType.dstH2 => createH2Analyzer(connStrSettings)
    case DataSrcType.dstPostgresql => createPostgreSqlAnalyzer(connStrSettings)
    case DataSrcType.dstMySQL => createMySqlAnalyzer(connStrSettings)
    case DataSrcType.dstFlatFile => createFlatFileAnalyzer(connStrSettings)
    case DataSrcType.dstJsonFile => createJsonAnalyzer(connStrSettings)
  }
}