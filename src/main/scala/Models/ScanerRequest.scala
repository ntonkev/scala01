package Models

/**
 * Created by nikolatonkev on 15-01-10.
 */
case class ScanerRequest (connectionStrings: List[Map[String, String]], joins: List[Join], joinConditions: List[JoinCondition])