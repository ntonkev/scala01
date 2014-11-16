import DAL.{DataSrcType, BaseAnalyser}
import RedBlackTree.RBTree
import RedBlackTree.RBTree

object Main extends App {

  //val rbTree: RBTree[Comparable[Int], Int]  = new RBTree[Comparable[Int],Int]()

  val pgSqlMap = Map("database.url" -> "jdbc:postgresql://ec2-54-221-223-92.compute-1.amazonaws.com:5432/db7k8198l73h6l",
                    "database.username" -> "aypkpqlvwdznkk",
                    "database.password" -> "blItMMzvKwWjEFI1ItcWhc-uix",
                    "database.ssl" -> "true",
                    "database.sslfactory" -> "org.postgresql.ssl.NonValidatingFactory")

  val mySqlMap = Map("database.url" -> "")

  val pgSqlAnalyser =  BaseAnalyser(DataSrcType.dstPostgresql, pgSqlMap)
  val entities = pgSqlAnalyser.getEntities()
  for(e <- entities){
    println(e.Type  + "\t" + e.Schema + "\t" + e.Name)
  }

  println("\n")
  var entityItems = pgSqlAnalyser.getEntityItems("user")
  for(e <- entityItems){
    println(e.OrderIndex + "\t" + e.ColumnName  + "\t" + e.ColumnType + "\t" + e.ColumnLenght + "\t" + e.DefaultValue + "\t" + e.IsNullable + "\t"  + e.ColumnPrecision + "\t" + e.ColumnScale)
  }

  val mySqlAnalyser =  BaseAnalyser(DataSrcType.dstMySQL, mySqlMap)
  println("\n")

}