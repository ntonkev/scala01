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

  implicit override def GetDataBase(): Database = {
//    val url = connStrSettings("database.url")// + dbname
//    val driver = "org.h2.Driver"

    return null //Database.forURL("jdbc:h2:mem:testdb;;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
  }

  def GetCassandraCluster(): Cluster = {
    try
    {
      //val client = new SimpleClient("192.168.0.104:9142")
      val cluster = Cluster.builder().addContactPoint("/192.168.0.104:9042").build()
      //log(cluster.getMetadata())
      val session = cluster.connect()
      return cluster
    }
    catch {
      case e: Exception => {
        println("Error connectin to the cluster 192.168.9.104: " + e.fillInStackTrace())
        return null
      }
    }
  }

  def getEntities(): Seq[DataEntity]  = {
    val client = GetCassandraCluster()
   return null
  }

  def getEntityItems(entity: String): Seq[DataEntityItem]  = {
    return null
  }

  def createEntity(slqStr: String):Boolean = {
    if (slqStr.isEmpty) return false
    return true
  }

  def createEntity(entity: String, entityItems: Seq[DataEntityItem]):Boolean = {
    return true
  }

  }
