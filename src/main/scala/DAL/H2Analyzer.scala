package DAL

import Models.{DataEntityItem, DataEntity}

import scala.slick.jdbc.StaticQuery
import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{StaticQuery => Q}

/**
 * Created by NTonkev on 11/21/2014.
 */

trait H2Profile {
  val profile = scala.slick.driver.H2Driver
}

class H2Analyzer(connStrSettings: Map[String, String]) extends BaseAnalyzer(DataSrcType.dstMySQL, connStrSettings)  with ImplicitDataEntity{
  val dbname: String = connStrSettings("database.name")

  implicit override def GetDataBase(): Database = {
    val url = connStrSettings("database.url")// + dbname
    val driver = "org.h2.Driver"

    return Database.forURL("jdbc:h2:mem:testdb", driver = "org.h2.Driver")
  }

  def GetH2DataBase() = GetDataBase()

  def getEntities(): Seq[DataEntity]  = {
//    val db = GetMySqlDataBase()
//    db.withDynSession {
//      Q.queryNA[DataEntity]("SELECT (CASE table_type WHEN 'BASE TABLE' THEN 'Table' ELSE 'View' END) AS 'Type', '' AS 'Schema', table_name AS 'Name' FROM information_schema.tables WHERE (table_type = 'base table' OR table_type = 'view')AND table_schema='" + dbname + "'").list
//    }
    return null
  }

  def getEntityItems(entity: String): Seq[DataEntityItem]  = {
//    val db = GetMySqlDataBase()
//    db.withDynSession {
//      Q.queryNA[DataEntityItem]("SELECT COLUMN_NAME AS 'Column', ORDINAL_POSITION AS 'OrderIndex', COLUMN_DEFAULT AS 'DefaultValue', IS_NULLABLE AS 'Nullable', DATA_TYPE AS 'Type', CHARACTER_MAXIMUM_LENGTH AS 'Lenght', NUMERIC_PRECISION AS 'Precision', NUMERIC_SCALE AS 'Scale' FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + entity + "'AND table_schema = '" + dbname + "'").list
//    }
    return null
  }

  def createEntity(entity: String, entityItems: Seq[DataEntityItem]):Boolean = {
    try{
      val db = GetH2DataBase()
      db.withSession { implicit session =>
        Q.updateNA("create table Coffee ("+
          "name varchar not null, "+
          "sup_id int not null, "+
          "price double not null, "+
          "sales int not null, "+
          "total int not null )").execute
      }
      return true
    }
    catch {
      case e: Exception => {
        println("Error creating entity: " + e.fillInStackTrace())
        return false
      }
    }
  }

}
