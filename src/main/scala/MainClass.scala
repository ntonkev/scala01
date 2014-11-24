import java.math.BigInteger

import DAL._
import Models.DataEntityItem
import RedBlackTree.RBTree
import RedBlackTree.RBTree

object Main extends App {

  //val rbTree: RBTree[Comparable[Int], Int]  = new RBTree[Comparable[Int],Int]()

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

  println("Flat file...")
  val ffAnalyser = BaseAnalyzer(DataSrcType.dstFlatFile, flatFileMap)
  val ffEntity = ffAnalyser.getEntities()
//  for(e <- ffEntity){
//    println(e.Type  + "\t" + e.Schema + "\t" + e.Name)
//  }
  val ffEntityItems = ffAnalyser.getEntityItems()
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

  println("H2...")
  val entityName = ffAnalyser.asInstanceOf[FlatFileAnalyzer].fshortname
  val h2SqlAnalyzer =  BaseAnalyzer(DataSrcType.dstH2, h2SqlMap)
  //val success = h2SqlAnalyzer.createEntity(entityName, ffEntityItems)
  val success = h2SqlAnalyzer.createEntity("create table Coffee (name varchar not null, sup_id int not null, price double not null, sales int not null, total int not null )")
  println("Table created: " + success.toString)

  val h2params = List("Lavazza", 23, 3.49, 0.49, 3.35)
  var h2InsertQry: String = """Insert Into Coffee(name, sup_id, price, sales, total) values(?, ?, ?, ?, ?)"""
  h2SqlAnalyzer.asInstanceOf[H2Analyzer].insertData(h2params, h2InsertQry)

  val h2Qry: String = String.format("""SELECT * FROM %s""", "Coffee")
  for(m <- h2SqlAnalyzer.executeQuery(null, h2Qry)){
    println(m)
  }

  println("\nCassandra...")
  val cassAnalyser = BaseAnalyzer(DataSrcType.dstCassandra, null)
  val casdra = cassAnalyser.asInstanceOf[CassandraAnalyzer].GetCassandraCluster()

}