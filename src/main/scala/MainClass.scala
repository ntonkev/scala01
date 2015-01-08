import java.math.BigInteger
import java.util.{Dictionary, UUID}

import Common.{Evaluator, Operator, Operators}
import Common.Operators._
import DAL._
import Models.{DataEntity, DataEntityItem}
import RedBlackTree.{RBNode, RBTree}
import Scaners.{BaseColumn, BaseColumnTree, ColumnTree}

import scala.collection.mutable

//import RedBlackTree.RBTree
//import RedBlackTree.RBTree
import FilterParser._

import scala.collection.immutable.TreeMap
import scala.util.matching.Regex

object Main extends App with Evaluator {

//  val rbTree = new RBTree[Int,Double]()
//  for(n <- 1 to 10){
//    rbTree.put(n, n  /  10.5)
//  }
//  val aaa = rbTree.get(5)
//  //rbTree.
//  println(aaa)

  //val root = rbTree.root = new RBNode[Int, Int](1,2, true, 1)

  val h2SqlMap = Map("database.url" -> "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
                     "database.name" -> "ffsdata")

  val pgSqlMap = Map("database.url" -> "jdbc:postgresql://ec2-54-221-223-92.compute-1.amazonaws.com:5432/db7k8198l73h6l",
                    "database.username" -> "aypkpqlvwdznkk",
                    "database.password" -> "blItMMzvKwWjEFI1ItcWhc-uix",
                    "database.ssl" -> "true",
                    "database.sslfactory" -> "org.postgresql.ssl.NonValidatingFactory")

  val mySqlMap = Map("database.url" -> "jdbc:mysql://localhost/",
                      "database.name" -> "test",
                      "database.username" -> "root",
                      "database.password" -> "")

  val flatFileMap = Map("file.url" -> "flatfile.csv",
    //"file.name" -> "inv-details.csv",
    "file.has.header" -> "true",
    "file.delimiter" -> ",")


  val jsonFileMap = Map("file.url" -> "jsondata.json")

  val cassandraMap = Map("database.url" -> "54.148.145.149", "database.name" -> "demodb")

/*
  println("Postgresql...")
  val pgSqlAnalyzer =  BaseAnalyzer(DataSrcType.dstPostgresql, pgSqlMap)
  val pgEntities = pgSqlAnalyzer.getEntities()
  for(e <- pgEntities){
    println(e.Type  + "\t" + e.Schema + "\t" + e.Name)
  }

  println("\n")
  var pgentityItems = pgSqlAnalyzer.getEntityItems("entity")
  for(e <- pgentityItems){
    println(e.OrderIndex + "\t" + e.ColumnName  + "\t" + e.ColumnType + "\t" + e.ColumnLenght + "\t" + e.DefaultValue + "\t" + e.IsNullable + "\t"  + e.ColumnPrecision + "\t" + e.ColumnScale)
  }

  println("\n")
  val params = List("desy", "Moroz")
  val strOne: String = """SELECT * /* family_name, given_name, gender */ FROM auth.userinfo WHERE given_name = ? OR family_name = ?"""
  val models = pgSqlAnalyzer.executeQuery(params, strOne)
  for(m <- models){
    println(m)
  }
  val strTwo: String = """SELECT family_name, given_name, gender FROM auth.userinfo"""
  for(m <- pgSqlAnalyzer.executeQuery(null, strTwo)){
    println(m)
  }


  println("\n")
  println("\n")
  printlnSQL...")
  val mySqlAnalyzer =  BaseAnalyzer(DataSrcType.dstMySQL, mySqlMap)
  var sqlThree: String = """SELECT (CASE table_type WHEN 'BASE TABLE' THEN 'Table' ELSE 'View' END) AS 'Type', '' AS 'Schema', table_name AS 'Name' FROM information_schema.tables WHERE (table_type = 'base table' OR table_type = 'view')AND table_schema= ? """
  val params3 = List("test")
  for(m <- mySqlAnalyzer.executeQuery(params3, sqlThree)){
    println(m)
  }


  val mySqlEntities = mySqlAnalyzer.getEntities()
  for(e <- mySqlEntities){
    println(e.Type  + "\t" + e.Schema + "\t" + e.Name)
  }

  println("\n")
  var mySqlentityItems = mySqlAnalyzer.getEntityItems("vwpersons")
  for(e <- mySqlentityItems){
    println(e.OrderIndex + "\t" + e.ColumnName  + "\t" + e.ColumnType + "\t" + e.ColumnLenght + "\t" + e.DefaultValue + "\t" + e.IsNullable + "\t"  + e.ColumnPrecision + "\t" + e.ColumnScale)
  }
  */

//  println("Flat file...")
//  val ffAnalyser = BaseAnalyzer(DataSrcType.dstFlatFile, flatFileMap)
//  val ffEntity = ffAnalyser.getEntities()
//  for(e <- ffEntity){
//    println(e.Type  + "\t" + e.Schema + "\t" + e.Name)
//  }
//  val ffEntityItems = ffAnalyser.getEntityItems()
//  for(e <- ffEntityItems){
//    println(e.OrderIndex + "\t" + e.ColumnName  + "\t\t\t\t\t\t\t" + e.ColumnType + "\t" + e.ColumnLenght + "\t" + e.DefaultValue + "\t" + e.IsNullable + "\t"  + e.ColumnPrecision + "\t" + e.ColumnScale)
//  }

//  println("\n\n\n")
//  println("JSON file...")
//  val jsonAnalyser = BaseAnalyzer(DataSrcType.dstJsonFile, jsonFileMap)
//  val jsonEntity = jsonAnalyser.getEntities()
//  for(e <- jsonEntity){
//    println(e.Type  + "\t" + e.Schema + "\t" + e.Name)
//  }
//  println("\n\n\n")
//  val jsonEntityItems = jsonAnalyser.getEntityItems()
//  for(e <- ffEntity){
//    println(e.OrderIndex + "\t" + e.ColumnName  + "\t\t\t\t\t\t\t" + e.ColumnType + "\t" + e.ColumnLenght + "\t" + e.DefaultValue + "\t" + e.IsNullable + "\t"  + e.ColumnPrecision + "\t" + e.ColumnScale)
//  }

//  println("H2...")
//  val entityName = ffAnalyser.asInstanceOf[FlatFileAnalyzer].fshortname
//  val h2SqlAnalyzer =  BaseAnalyzer(DataSrcType.dstH2, h2SqlMap)
//  //val success = h2SqlAnalyzer.createEntity(entityName, ffEntityItems)
//  val success = h2SqlAnalyzer.createEntity("create table Coffee (name varchar not null, sup_id int not null, price double not null, sales int not null, total int not null )")
//  println("Table created: " + success.toString)
//
//  val h2params = List("Lavazza", 23, 3.49, 0.49, 3.35)
//  var h2InsertQry: String = """Insert Into Coffee(name, sup_id, price, sales, total) values(?, ?, ?, ?, ?)"""
//  h2SqlAnalyzer.asInstanceOf[H2Analyzer].insertData(h2params, h2InsertQry)
//
//  val h2Qry: String = String.format("""SELECT * FROM %s""", "Coffee")
//  for(m <- h2SqlAnalyzer.executeQuery(null, h2Qry)){
//    println(m)
//  }

//  println("\nCassandra...")
//  val cassAnalyser = BaseAnalyzer(DataSrcType.dstCassandra, cassandraMap)

//  val s0 = cassAnalyser.createEntity("""CREATE KEYSPACE demodb WITH replication = {'class':'SimpleStrategy', 'replication_factor':3}""")
//  if(s0) println("Keyspace demodb created successfully")
//  val s1 = cassAnalyser.createEntity("""CREATE TABLE demodb.person (id uuid PRIMARY KEY, firstname text, lastname text, age int)""")
//  if(s1) println("Table person in keyspace demodb created successfully")
  //val cassEntities = cassAnalyser.getEntities()

//  val cassParams = List(UUID.fromString("E9C0DBDD-DD8B-4A6C-AF91-DF38CF365D69"), "Nikola", "Tonkev", 36)
////  val cassParams = List(23, "Nikola", "Tonkev", 36)
//  val cassInsertStr: String = """INSERT INTO demodb.person(id, firstname, lastname, age) VALUES(?, ?, ?, ?)"""
//  val result = cassAnalyser.asInstanceOf[CassandraAnalyzer].execQuery(cassParams.asInstanceOf[List[Object]], cassInsertStr)
//  println(result)

  (1,5)
  (2,1)
  (3,4)
  (4,3)
  (6,2)
  (8,2)
  (9,1)

  val employees = List((1, "Hanry", 3456),
         (2, "George", 3454),
         (3, "Allen", 5678),
         (4, "Steaven", 3212),
         (5, "Peater", null),
         (6, "Patricia", 1234),
         (7, "Hilton", null),
         (8, "Pierre", 1234),
         (9, "Susane", 3454))

  val departments = List((1, "Sales", 3454),
         (2, "Marketing", 1234),
         (3, "Design", 3212),
         (4, "R&D", 5678),
         (5, "Human resources", 3456))

  val datasources = Map[String, DataEntity]("employees" -> new DataEntity("Table", "", "Employee"),
                                            "departments" -> new DataEntity("Table", "", "Departments"))

  val indexers = Map[String, Array[Int]]("employees" -> Array[Int](1,2,3,4,5,6,7,8,9),
    "departments" -> Array[Int](1,2,3,4,5,6))

  val columns = Map[String, DataEntityItem]("employees.employeeid" -> new DataEntityItem("employeeid", 1, "", false, "Integer", 0, 0, 0),
                                            "employees.name" -> new DataEntityItem("name", 2, "", true, "String", 50, 0, 0),
                                            "employees.deptNumber" -> new DataEntityItem("deptNumber", 3, "", true, "Integer", 0, 0, 0),
                                            "departments.departmentid" -> new DataEntityItem("departmentid", 4, "", false, "Integer", 0, 0, 0),
                                            "departments.deptName" -> new DataEntityItem("deptName", 5, "", true, "String", 50, 0, 0),
                                            "departments.deptNumber" -> new DataEntityItem("deptNumber", 6, "", true, "Integer", 0, 0, 0))

  //1 Inner Join, 2 Left Join, 3 Right Join, 4 Cross Join
  case class Join( Index: Int, JoinType: Int, LeftEntity: String, RightEntity: String, Expression: String)
  val joins = List[Join](new Join(1, 1, "employees", "departments", "0."))

  case class JoinCondition(JoinIndex: Int, ConditionIndex: Int, LeftOperand: String, RightOperand: String, Operator: Operators)
  val joinConditions = List[JoinCondition](new JoinCondition(1, 0, "employees.deptNumber", "departments.deptNumber", Operators.optIsEqual))

  case class Condition( leftOperandIndex: Int, rightOperandIndex: Int, operator: Operator)

  val c1: ColumnTree[Int, Int] = new RBTree[Int, Int]()
  val c2: ColumnTree[Int, String] = new RBTree[Int, String]()
  val c3: ColumnTree[Int, Int] = new RBTree[Int, Int]()
  for(n <- 0 to employees.length - 1){
    val e = employees(n)
    val index = e._1.asInstanceOf[Int]
    c1.put(index, e._1.asInstanceOf[Int])
    c2.put(index, e._2.asInstanceOf[String])
    c3.put(index, e._3.asInstanceOf[Int])
  }

  val c4: ColumnTree[Int, Int] = new RBTree[Int, Int]()
  val c5: ColumnTree[Int, String] = new RBTree[Int, String]()
  val c6: ColumnTree[Int, Int] = new RBTree[Int, Int]()
  for(n <- 0 to departments.length - 1){
    val d = departments(n)
    val index = d._1.asInstanceOf[Int]
    c4.put(index, d._1.asInstanceOf[Int])
    c5.put(index, d._2.asInstanceOf[String])
    c6.put(index, d._3.asInstanceOf[Int])
  }

  val columnsMap = Map[Int, Any](1 -> c1, 2 -> c2, 3 -> c3, 4 -> c4, 5 -> c5, 6 -> c6)

  def getOperandIndex(operand: String): Int = {
    return columns(operand).OrderIndex
  }

  def getIndexer(entityName: String): Array[Int] = {
    return indexers(entityName)
  }

  def SetExpressionConditions(l: Int, r: Int, bcndtns: Array[Boolean], conditions: Array[Condition]): Array[Boolean] = {
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


  for(join <- joins){
    val joinIndex = join.Index
    val jc =  joinConditions.filter(j => j.JoinIndex.equals(joinIndex))
    val conditions = for(c <- jc) yield new Condition(getOperandIndex(c.LeftOperand), getOperandIndex(c.RightOperand), new Operator(Operators.optIsEqual))
    val leftIndexer = getIndexer(join.LeftEntity)
    val rightIndexer = getIndexer(join.RightEntity)
    val parser = new Parser(join.Expression)
    //var ba = new Seq[Boolean]

    for(l <- leftIndexer) {
      for(r <- rightIndexer) {
        val ba = SetExpressionConditions(l, r, new Array[Boolean](0), conditions.toArray)
        if(parser.ExecuteExpression(ba)) {
          //Go delete that key from the index tree

          println(c1.get(l), c2.get(l), c3.get(l), c4.get(r), c5.get(r), c6.get(r))
        }
      }
    }

    //println(conditions.toList.getClass)

    //val boolArray = new Array[Boolean](jc.length)
  }


//  def Scan[T](s1: List[Field[Option[T]]], s2: List[Field[Option[T]]]) : Unit  = {
//    //var result = Map[Int, Int]
//    for(a <- s1) {
//      for(b <- s2) {
//        if(a.Value == b.Value) println(a, b) //result(0) = a.Index
//      }
//    }
//  }
//  Scan[Int](c3, c6)
//
//  val o = new Operator[Int]()
//  val r = o.apply(5, 5,Operators.optIsEqual)
//  println(r)
//
//  def IsEqual(lo: Int, ro: Int): Boolean = lo.equals(ro)
//  val ev: evaluate[Int] = IsEqual
//  println(ev.apply(5,5))

//  def Scan(): Unit = {
//
//  }


  //val relation = Scan(c3, c6)
  //println(relation)
  //println(65)
//  val pattern = new Regex("[0-9]|[a-zA-Z0-9]")
//  for(n <- 0 to 255){
//    val c = n.asInstanceOf[Char]
//      println("%@ %s", c, pattern findFirstIn c.toString())
//  }

  //true, false, false
//  val source = "not(not(Not 0) or (1)."
//  val p = new Parser(source)
//  val result = p.ValidateExpression()
//  val result2 = p.ExecuteExpression(Array(false, false))
//  val error = p.GetError()
//  if(error.equals("")){
//    println(result, result2)
//  }
//  else {
//    println(error)
//  }


/*
A.c1, A.C2, A.C3
B.C1, B.C2, B.C3
C.C1, C.C3

SELECT A.*, B.C2, C.C2
  FROM A
  JOIN A.C1 = B.C1   (**)
  JOIN C.C3 = A.C3   (*)

  Buffer (*) = Hash Trie(C.RowIndex, A.RowIndex)
  (**)Hash Trie(A.RowIndex)

  T[G] WHERE G is T
   Childs[G]
*/
}

//A,B, C, D
//
//JOIN A C
//JOIN C D

/*

CREATE KEYSPACE demodb WITH replication = {'class':'SimpleStrategy', 'replication_factor':3};

CREATE TABLE demodb.person (id int PRIMARY KEY, firstname text, lastname text, age int)
CREATE TABLE demodb.Addresses (id int PRIMARY KEY, personid uuid, address text, postalcode text)

SELECT column_name FROM system.schema_columnfamilies WHERE keyspace_name = 'demodb' AND columnfamily_name = Y
select columnfamily_name from system.schema_columnfamilies where keyspace_name='demodb'

select 'Table' AS Type, columnfamily_name from system.schema_columnfamilies where keyspace_name='demodb'

INSERT INTO demodb.person(id, firstname, lastname, age) VALUES(?, ?, ?, ?)

*/