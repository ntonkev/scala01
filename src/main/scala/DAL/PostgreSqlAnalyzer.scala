package DAL

import org.joda.time.DateTime
import java.sql.PreparedStatement
import java.util.Properties
import Models.{DataEntityItem, DataEntity}

import scala.slick.collection.heterogenous.Zero.+
import scala.slick.lifted.Query

//import scala.slick.driver.PostgresDriver.simple._
import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{StaticQuery => Q, PositionedResult, GetResult}
import Q.interpolation
import Database.dynamicSession


trait PostgresqlProfile {
  val profile = scala.slick.driver.PostgresDriver
}

/**
 * Created by nikolatonkev on 14-11-15.
 */
class PostgreSqlAnalyzer(connStrSettings: Map[String, String]) extends BaseAnalyzer(DataSrcType.dstPostgresql, connStrSettings) {

  implicit val getAnyRefResult = GetResult[Map[String,Any]] ( prs =>
    (1 to prs.numColumns).map(_ =>
      prs.rs.getMetaData.getColumnName(prs.currentPos+1) -> prs.nextString
    ).toMap
  )


  implicit def GetDataBase(): Database = {
    val url = connStrSettings("database.url")
    val username = connStrSettings("database.username")
    val password = connStrSettings("database.password")
    val ssl = connStrSettings("database.ssl")
    val sslfactory = connStrSettings("database.sslfactory")

    val props = new Properties
    props.setProperty("ssl", ssl)
    props.setProperty("sslfactory", sslfactory)

    val db =  Database.forURL(url, username, password, props)
//    val msg: String = (db == null) match {
//      case true => "Connection to Postgresql failed..."
//      case false => "Connection to Postgresql is successfull..."
//    }
//    println(msg)
    return db
  }

  def getEntities(): Seq[DataEntity]  = {
    val db = GetDataBase()
    db.withDynSession {
      Q.queryNA[DataEntity]("SELECT (case table_type when 'BASE TABLE' THEN 'Table' ELSE 'View' END) AS Type, table_schema AS Schema, table_name AS Name FROM INFORMATION_SCHEMA.tables  WHERE NOT (table_schema in ('pg_catalog', 'information_schema' )) ORDER BY table_schema, table_type, table_name").list
    }
  }

  def getEntityItems(entity: String): Seq[DataEntityItem]  = {
    val db = GetDataBase()
    db.withDynSession {
      Q.queryNA[DataEntityItem]("SELECT column_name AS ColumnName, ordinal_position AS OrderIndex, column_default AS DefaultValue, (CASE is_nullable WHEN 'YES' THEN true ELSE false END) AS Nullable, data_type AS ColumnType, character_maximum_length AS ColumnLenght, numeric_precision AS ColumnPrecision, numeric_scale AS ColumnScale FROM information_schema.columns WHERE table_name   = '" + entity + "'").list
    }
  }

  def runQry(): Seq[Object] ={
    val db = GetDataBase()
    db.withDynSession {
      //    def allData() = sql"SELECT family_name, given_name, gender FROM auth.userinfo".as[Object]
      Q.queryNA[AnyRef]("""SELECT family_name, given_name, gender FROM auth.userinfo""").list
    }
  }

}
