import DAL.{PostgreSqlAnalyzer, DataSrcType, BaseAnalyzer}
import RedBlackTree.RBTree
import RedBlackTree.RBTree

object Main extends App {

  import scala.reflect._
  val ct = classTag[String]
  println(ct)


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
  val models = pgSqlAnalyzer.asInstanceOf[PostgreSqlAnalyzer].runQryWithParams()
  for(m <- models){
    println(m)
  }

  /*
  val pgEntities = pgSqlAnalyzer.getEntities()
  for(e <- pgEntities){
    println(e.Type  + "\t" + e.Schema + "\t" + e.Name)
  }

  println("\n")
  var pgEntityItems = pgSqlAnalyzer.getEntityItems("user")
  for(e <- pgEntityItems){
    println(e.OrderIndex + "\t" + e.ColumnName  + "\t" + e.ColumnType + "\t" + e.ColumnLenght + "\t" + e.DefaultValue + "\t" + e.IsNullable + "\t"  + e.ColumnPrecision + "\t" + e.ColumnScale)
  }

  println("\n")
  println("\n")
  println("\n")
  println("\n")
  println("\n")
  println("mySQL...")
  val mySqlAnalyzer =  BaseAnalyzer(DataSrcType.dstMySQL, mySqlMap)
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

}