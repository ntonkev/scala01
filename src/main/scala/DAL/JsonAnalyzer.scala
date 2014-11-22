package DAL

import java.util

import Models.{DataEntityItem, DataEntity}
import scala.collection.mutable
import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import Database.dynamicSession

import scala.util.parsing.json.JSON

/**
 * Created by NTonkev on 11/21/2014.
 */
class JsonAnalyzer (connStrSettings: Map[String, String]) extends BaseAnalyzer(DataSrcType.dstFlatFile, connStrSettings) with DataTypeValidator {

  val names = FileAnalyzer.GetFileName(connStrSettings)
  def fname = names._1
  def fshortname = names._2
  def fpath = FileAnalyzer.GetCurrentDirectory + "//Data/Seeds//"
  def furl = FileAnalyzer.GetFilePath(connStrSettings)

  implicit override def GetDataBase(): Database = {
    return null
  }

  //def GetMySqlDataBase() = GetDataBase()

  def getEntities(): Seq[DataEntity]  = {
    return Seq(new DataEntity("File", "", fname))
  }

  def getEntityItems(entity: String): Seq[DataEntityItem]  = {
    val content = scala.io.Source.fromFile(fpath + furl).getLines().mkString
    val json: Option[Any] = JSON.parseFull(content)
    val firstItem = json.get.asInstanceOf[List[Any]].head
    println(firstItem)
    return null
  }

  def createEntity(sqlStr: String): Boolean = {
    return true
  }

  def createEntity(entity: String, entityItems: Seq[DataEntityItem]): Boolean ={
    return true
  }
}
