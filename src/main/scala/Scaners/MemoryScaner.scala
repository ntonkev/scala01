package Scaners

import java.util.Calendar

import Common.{Operator, Operators}
import DAL.{FlatFileAnalyzer, DataSrcType, BaseAnalyzer}
import FilterParser.Parser
import Models._
import RedBlackTree.RBTree

/**
 * Created by nikolatonkev on 14-12-19.
 */
class MemoryScaner {

 val datasources = collection.mutable.Map.empty[String, DataEntity]
 val columns = collection.mutable.Map.empty[String, DataEntityItem]
 val columnsMap = collection.mutable.Map.empty[String, BaseColumn] //Map[Int, Any](1 -> c1, 2 -> c2, 3 -> c3, 4 -> c4, 5 -> c5, 6 -> c6)
 val indexers = collection.mutable.Map.empty[String, Array[Int]]

// val joins = List[Join](new Join(1, 1, "employees", "departments", "0."))
// val joinConditions = List[JoinCondition](new JoinCondition(1, 0, "employees.departmentId", "departments.departmentId", Operators.optIsEqual))

 var joins = List.empty[Join] //(new Join(1, 1, "table_one", "table_two", "0."))
 var joinConditions = List.empty[JoinCondition]//(new JoinCondition(1, 0, "table_one.country", "table_two.country", Operators.optIsEqual))

 def initializeColumnsData(entityName: String, data: Seq[Map[String, Any]]) = {
    var counter: Int = 1
    for(row <- data){
     for(field <- row.keys){
      val fieldName = entityName + "." + field
      val column = columnsMap(field).asInstanceOf[ColumnTree[Any, Any]]
      column.put(counter, row(field))
     }
     counter += 1
    }
 }

// def this(connectionStrings: List[Map[String, String]]) {
def this(request: ScanerRequest) {
  this
 joins = request.joins
 joinConditions = request.joinConditions

  val st = Calendar.getInstance().getTime()
  for(m <- request.connectionStrings){
   val analyzer = BaseAnalyzer(DataSrcType.dstFlatFile, m)
   val entities = analyzer.getEntities()
   val entityName = entities(0).Name.replace(".csv", "")
   val entity = entities(0)
   datasources += entityName -> entity

   val entityItems = analyzer.getEntityItems()
   val data = analyzer.asInstanceOf[FlatFileAnalyzer].getData(entityName, entityItems)

   val array = 1.until(data.length + 1).toArray
   indexers += entityName -> array

   for(e <- entityItems){
    val key = entityName + "." + e.ColumnName
    columns += key -> DataEntityItem(key, columns.size, e.DefaultValue, e.IsNullable, e.ColumnType, e.ColumnLenght, e.ColumnPrecision, e.ColumnScale)
    val column = e.ColumnType match {
     case "Integer" => new RBTree[Int, Int]()
     case "String" => new RBTree[Int, String]()
     case _ => new RBTree[Int, String]
    }
    columnsMap += key -> column.asInstanceOf[BaseColumn]
   }

   initializeColumnsData(entityName, data)

  }
  val ft = Calendar.getInstance().getTime()
  println("Init and columns population time: ", ft.getTime - st.getTime)
 }

 def getOperandIndex(operand: String): Int = {
  return columns(operand).OrderIndex
 }

def getIndexer(entityName: String): Array[Int] = {
  return indexers(entityName)
}

 def SetExpressionConditions(l: Int, r: Int, bcndtns: Array[Boolean], conditions: Array[EvaluatedCondition]): Array[Boolean] = {
  if (bcndtns.length < conditions.length) {
   val c = conditions(bcndtns.length)
   val lc = columnsMap(c.leftOperandIndex)
   val rc = columnsMap(c.rightOperandIndex)
   val lv = lc.asInstanceOf[ColumnTree[Any, Any]].get(l)
   val rv = rc.asInstanceOf[ColumnTree[Any, Any]].get(r)
   val result = c.operator.apply(lv, rv)

   val temp = bcndtns :+ result
   return SetExpressionConditions(l, r, temp, conditions)
  }
  else {
   return bcndtns
  }
 }

 def getMaxIndexer(): Array[Int] = {
  val lst = indexers.toList sortBy {_._2.length}
  return lst.last._2
 }

 def getResultTree(): RBTree[Int, Map[String, Int]] = {
  val biggestIndexer = getMaxIndexer()
  return null
 }


 def scan() = {
 val st = Calendar.getInstance().getTime()
 for(join <- joins){
  //var counter = 1
//  var map = collection.mutable.Map.empty[String, Tuple2[Int, Int]]
  val joinIndex = join.Index
  val jc =  joinConditions.filter(j => j.JoinIndex.equals(joinIndex))
  val conditions = for(c <- jc) yield new EvaluatedCondition(c.LeftOperand, c.RightOperand, new Operator(Operators.optIsEqual))
  val leftIndexer = getIndexer(join.LeftEntity)
  val rightIndexer = getIndexer(join.RightEntity)
  val parser = new Parser(join.Expression)
  //var ba = new Seq[Boolean]

  for(l <- leftIndexer) {
   for(r <- rightIndexer) {
    val ba = SetExpressionConditions(l, r, new Array[Boolean](0), conditions.toArray)
    if(parser.ExecuteExpression(ba)) {
     //reasult here
    }
   }
  }

  //println(conditions.toList.getClass)

  //val boolArray = new Array[Boolean](jc.length)
 }

 val ft = Calendar.getInstance().getTime()
 println("Qry exec time: ", ft.getTime - st.getTime)
}

 // var datasources: Map[String, DataEntity] = null
// var indexers: Map[String, Array[Int]] = null
// var columns: Map[String, DataEntityItem] = null

 /*
 * A Join B   (1)
 * A Join C   (2)
 *
 * which one has the more data
 * Table B has the more data
 * Left Operand became Table B
 * Create Tree with keys table B indexes and Null Values @
 * Reasult for J(1) The tree From step @ with values Tuple2
 * Perform join 2
 * loop trough result and get != then null values
 * Get the tupleValue for table A and scan table C
 * If there is a match extend Tuple2 to Tuple 3
 * Final reasult scan and get all the keys with value  ==   Tuple3
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * */



}
