package DAL

import Models.{DataEntityItem, DataEntity}
import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import Database.dynamicSession

trait MySqlProfile {
  val profile = scala.slick.driver.MySQLDriver
}

/**
 * Created by nikolatonkev on 14-11-15.
 */
class MySqlAnalyzer(connStrSettings: Map[String, String]) extends BaseAnalyzer(DataSrcType.dstMySQL, connStrSettings)  with ImplicitDataEntity{
  val dbname: String = connStrSettings("database.name")

  implicit override def GetDataBase(): Database = {
    val url = connStrSettings("database.url") + dbname
    val username = connStrSettings("database.username")
    val password = connStrSettings("database.password")

    return Database.forURL(url, username, password)
  }

  def GetMySqlDataBase() = GetDataBase()

  def getEntities(): Seq[DataEntity]  = {
    val db = GetMySqlDataBase()
    db.withDynSession {
      Q.queryNA[DataEntity]("SELECT (CASE table_type WHEN 'BASE TABLE' THEN 'Table' ELSE 'View' END) AS 'Type', '' AS 'Schema', table_name AS 'Name' FROM information_schema.tables WHERE (table_type = 'base table' OR table_type = 'view')AND table_schema='" + dbname + "'").list
    }
  }

  def getEntityItems(entity: String): Seq[DataEntityItem]  = {
    val db = GetMySqlDataBase()
    db.withDynSession {
      Q.queryNA[DataEntityItem]("SELECT COLUMN_NAME AS 'Column', ORDINAL_POSITION AS 'OrderIndex', COLUMN_DEFAULT AS 'DefaultValue', IS_NULLABLE AS 'Nullable', DATA_TYPE AS 'Type', CHARACTER_MAXIMUM_LENGTH AS 'Lenght', NUMERIC_PRECISION AS 'Precision', NUMERIC_SCALE AS 'Scale' FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + entity + "'AND table_schema = '" + dbname + "'").list
    }
  }
}
