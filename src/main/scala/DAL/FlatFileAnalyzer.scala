package DAL

import Models.{DataEntityItem, DataEntity}
import scala.collection.parallel.immutable._
import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import Database.dynamicSession

import scala.util.Try

/**
 * Created by NTonkev on 11/19/2014.
 */
class FlatFileAnalyzer(connStrSettings: Map[String, String]) extends BaseAnalyzer(DataSrcType.dstFlatFile, connStrSettings) with DataTypeValidator {
  val ffhasheader: Boolean = FlatFileHasHeader()
  val ffdelimiter: Char = GetFlatFileDelimiter()

  val names = FileAnalyzer.GetFileName(connStrSettings)
  def fname = names._1
  def fshortname = names._2
  def fpath = FileAnalyzer.GetCurrentDirectory + "//Data/Seeds//"
  def furl = FileAnalyzer.GetFilePath(connStrSettings)

  protected def FlatFileHasHeader(): Boolean = {
    return connStrSettings.exists({case(key, _) => key == "file.has.header"}) match {
      case true => Try(connStrSettings("file.has.header").toBoolean).getOrElse(false)
      case false => false
    }
  }

  protected def GetFlatFileDelimiter(): Char = {
    return connStrSettings.exists({case(key, _) => key == "file.delimiter"}) match {
      case true => connStrSettings("file.delimiter").trim().charAt(0)
      case false => ','
    }
  }

  protected def GetHeader(firstLine: String): List[String] = {
    val cols = firstLine.split(ffdelimiter)
    return ffhasheader match {
      case true => cols.toList
      case false =>
        val result =  new Array[String](cols.length)
        for(n <- 0  to cols.length - 1){
          result(n) = "Column" + (n + 1).toString
        }
        result.toList
    }
  }

  implicit override def GetDataBase(): Database = {
    return null
  }

  //def GetMySqlDataBase() = GetDataBase()

  def getEntities(): Seq[DataEntity]  = {
    return Seq(new DataEntity("File", "", fname))
  }

  def getEntityItems(entity: String): Seq[DataEntityItem]  = {
    val linesToTake = ffhasheader match { case true => 2 case false => 1 }
    val lines = scala.io.Source.fromFile(fpath + furl).getLines().take(linesToTake)
    val firstLine: String = lines.take(1).mkString
    val dataLine = (ffhasheader match { case true => lines.take(1).mkString case false => firstLine }).split(ffdelimiter)
    val headers = GetHeader(firstLine)
    val columns = new Array[DataEntityItem](dataLine.length)
    for(n <- 0 to dataLine.length - 1){
      columns(n) = new DataEntityItem(headers(n), n + 1, "", true, GetDataType(dataLine(n)), 0, 0, 0)
    }
    return columns.toSeq
  }

  def createEntity(sqlStr: String): Boolean = {
    return true
  }

  def createEntity(entity: String, entityItems: Seq[DataEntityItem]): Boolean ={
    return true
  }
}