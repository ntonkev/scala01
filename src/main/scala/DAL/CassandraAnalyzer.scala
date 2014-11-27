package DAL

import Models.{DataEntityItem, DataEntity}
import scala.slick.jdbc.StaticQuery
import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{StaticQuery => Q}

import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core._
import scala.collection.JavaConversions._


/**
 * Created by nikolatonkev on 14-11-23.
 */

//trait H2Profile {
//  val profile = scala.slick.driver.
//}

class CassandraAnalyzer(connStrSettings: Map[String, String]) extends BaseAnalyzer(DataSrcType.dstMySQL, connStrSettings)  with ImplicitDataEntity{

  val node = connStrSettings.exists({case(key, _) => key == "database.url"}) match {
    case true => connStrSettings("database.url")
    case false => null
  }
  val keyspace = connStrSettings.exists({case(key, _) => key == "database.name"}) match {
    case true => connStrSettings("database.name")
    case false => null
  }

  private val cluster = if(node != null) Cluster.builder().addContactPoint(node).build() else null
  //log(cluster.getMetadata())
  val session = if(keyspace != null) cluster.connect(keyspace) else cluster.connect()

  implicit override def GetDataBase(): Database = {
    return null //Database.forURL("jdbc:h2:mem:testdb;;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
  }


  def getEntities(): Seq[DataEntity]  = {
    val entities = session.execute("""select columnfamily_name from system.schema_columnfamilies where keyspace_name='demodb'""")
      var result = new Array[DataEntity](entities.size)
      for(row <- entities ) {
        result(result.length - 1) = new DataEntity("Table", "demodb", row.getString("columnfamily_name"))
        println(row.getString("columnfamily_name"))
      }
    //println(result)
   return null
  }

  def getEntityItems(entity: String): Seq[DataEntityItem]  = {
    return null
  }

  def createEntity(slqStr: String):Boolean = {
    if (slqStr.isEmpty) return false
    session.execute(slqStr)
    return true
  }

  def createEntity(entity: String, entityItems: Seq[DataEntityItem]):Boolean = {
    return true
  }

  def execQuery(params: List[Object], sqlStr: String): Boolean = {
    //val ps: PreparedStatement  = session.prepare(sqlStr)
    //val batch: BatchStatement = new BatchStatement();
    //for(p <- params){ ps.bind(p) }
    //batch.add(ps.bind())
    //val boundStatement: BoundStatement  = new BoundStatement(statement);
    //session.execute(batch)
    session.execute(sqlStr, params)

    return true
//    return params match{
//      case null => null //runQryWithoutParams(sqlStr)
//      case _ => null //runQryWithParams(params, sqlStr)
//    }
  }

  override def executeQuery(params: List[Any], sqlStr: String): Seq[Object] = {
    return params match{
      case null => null //runQryWithoutParams(sqlStr)
      case _ => null //runQryWithParams(params, sqlStr)
    }
  }

  }
