package DAL

import Models.{DataEntityItem, DataEntity}
import scala.slick.jdbc.StaticQuery
import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{StaticQuery => Q}

import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.Cluster
import com.datastax.driver.core.ResultSetFuture
import com.datastax.driver.core.Session
import com.datastax.driver.core.Metadata
import scala.collection.JavaConversions._


/**
 * Created by nikolatonkev on 14-11-23.
 */

//trait H2Profile {
//  val profile = scala.slick.driver.
//}

class CassandraAnalyzer(connStrSettings: Map[String, String]) extends BaseAnalyzer(DataSrcType.dstMySQL, connStrSettings)  with ImplicitDataEntity{

  val node = connStrSettings("database.url")
  val keyspace = connStrSettings("database.name")

  private val cluster = Cluster.builder().addContactPoint(node).build()
  //log(cluster.getMetadata())
  val session = cluster.connect(keyspace)

  implicit override def GetDataBase(): Database = {
    return null //Database.forURL("jdbc:h2:mem:testdb;;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
  }


  def getEntities(): Seq[DataEntity]  = {
    //val client = GetCassandraCluster()
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

  }
