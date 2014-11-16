package DAL

import Models.{DataEntityItem, DataEntity}

/**
 * Created by nikolatonkev on 14-11-15.
 */
class MySqlAnalyser(connStrSettings: Map[String, String]) extends BaseAnalyser(DataSrcType.dstMySQL, connStrSettings){

  def getEntities(): Seq[DataEntity]  = {
    return null
  }

  def getEntityItems(entity: String): Seq[DataEntityItem]  = {
    return null
  }
}
