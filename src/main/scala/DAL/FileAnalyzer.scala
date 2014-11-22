package DAL

/**
 * Created by NTonkev on 11/21/2014.
 */

object DataSourceFileTypes extends Enumeration {
  type DataSourceFileTypes = Value
  val dsftFlatFile, dsftJson  = Value
}
import DataSourceFileTypes._

//trait FileAnalyzer

object FileAnalyzer {
  def GetCurrentDirectory = new java.io.File( "." ).getCanonicalPath

  def GetFileName(connStrSettings: Map[String, String]): Tuple2[String, String] = {
    val name = connStrSettings.exists({case(key, _) => key == "file.name"}) match {
      case true => connStrSettings("file.name")
      case false => connStrSettings.exists({case(key, _) => key == "file.url"}) match {
        case true => connStrSettings("file.url").substring(connStrSettings("file.url").lastIndexOf("\\")+1)
        case false => ""
      }
    }
    return (name, name.substring(0, name.lastIndexOf(".")))
  }

  def GetFilePath(connStrSettings: Map[String, String]): String = {
    return connStrSettings.exists({case(key, _) => key == "file.url"}) match {
      case true => connStrSettings("file.url")
      case false => ""
    }
  }

}