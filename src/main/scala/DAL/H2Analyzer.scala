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

    return Database.forURL("jdbc:h2:mem:testdb;;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
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

  def createEntity(slqStr: String):Boolean = {
    if(slqStr.isEmpty) return false
    try{
      val db = GetH2DataBase()
      db.withSession { implicit session =>
        Q.updateNA(slqStr).execute
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

  def createEntity(entity: String, entityItems: Seq[DataEntityItem]):Boolean = {
    try{
      if(entityItems == null || entityItems.length == 0) return false
      val sb = new StringBuilder()
      val db = GetH2DataBase()
      db.withSession { implicit session =>
        sb.append(String.format("create table %s ( " , entity))
        for(e <- entityItems)
        {
          val ctype: String = getEntityType(e)
          sb.append(String.format(" %s %s ", e.ColumnName, ctype))
        }
        val sqlStr = sb.mkString
        val qry = sqlStr.substring(0, sqlStr.lastIndexOf(",")) + ")"
        println(qry)
        Q.updateNA(qry).execute
//        Q.updateNA("create table Coffee ("+
//          "name varchar not null, "+
//          "sup_id int not null, "+
//          "price double not null, "+
//          "sales int not null, "+
//          "total int not null )").execute
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

  def insertData(params: List[Any], sqlStr: String): Boolean = {
    try
    {
      val db = this.GetDataBase()
      db.withSession { implicit session => {
        val query = Q.update[List[Any]](sqlStr)
        val result = query(params).first
        return result != 0
        }
      }
    }
    catch {
      case e: Exception => {
        println("Error creating entity: " + e.fillInStackTrace())
        return false
      }
    }
  }


  protected def getNullState(e: DataEntityItem): String = e.IsNullable match {
    case true => " null"
    case false => "not null"
    case _ => "not null"
  }

  protected def getPrecisionState(e: DataEntityItem): Int = e.ColumnPrecision != 0 match {
    case true => e.ColumnPrecision
    case false => 12
  }

  protected def getScaleState(e: DataEntityItem): Int = e.ColumnScale != 0 match {
    case true => e.ColumnScale
    case false => 3
  }

  protected def getEntityType(e: DataEntityItem): String = {
    val nullState: String = getNullState(e)
    val precision: Int = getPrecisionState(e)
    val scale: Int = getScaleState(e)
    e.ColumnType match {
      case "Integer" => String.format("int %s,", nullState)
      case "String" => String.format("varchar %s,", nullState)
      case "Decimal" => String.format("decimal(%s, %s) %s,", precision.toString, scale.toString, nullState)
      case "Double" => String.format("double %s,", nullState)
      case "Currency" => String.format("double %s,", nullState)
      case "Date" => String.format("date %s,", nullState)
      case "DateTime" => String.format("date %s,", nullState)
      case "GUID" => String.format("uuid %s,", nullState)
    }
  }



}
