import java.math.BigInteger

import DAL.{MySqlAnalyzer, PostgreSqlAnalyzer, DataSrcType, BaseAnalyzer}
import RedBlackTree.RBTree
import RedBlackTree.RBTree

object Main extends App {



//  import scala.reflect._
//  val ct = classTag[String]
//  println(ct)

//  val x: String = "5"
//  import scala.reflect.ClassTag
//  def f[T](v: T)(implicit ev: ClassTag[T]) = ev
//  //println(f(0.5)) // returns the string "Any"

//  val vList: List[Any] = List(5, "Moroz")
//  for (v <- vList){
//    println(v.getClass())
//    v match {
//      case v:Int => println("Int")
//      case v:String => println("string")
//    }
//  }

  //val rbTree: RBTree[Comparable[Int], Int]  = new RBTree[Comparable[Int],Int]()

  val pgSqlMap = Map("database.url" -> "jdbc:postgresql://ec2-54-221-223-92.compute-1.amazonaws.com:5432/db7k8198l73h6l",
                    "database.username" -> "aypkpqlvwdznkk",
                    "database.password" -> "blItMMzvKwWjEFI1ItcWhc-uix",
                    "database.ssl" -> "true",
                    "database.sslfactory" -> "org.postgresql.ssl.NonValidatingFactory")

  val mySqlMap = Map("database.url" -> "jdbc:mysql://localhost/",
                      "database.name" -> "test",
                      "database.username" -> "root",
                      "database.password" -> "")

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
  println("mySQL...")
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


}