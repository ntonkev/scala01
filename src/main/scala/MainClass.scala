import java.math.BigInteger
import java.util.UUID

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

  val cassandraMap = Map("database.url" -> "localhost", "database.name" -> "demodb")

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

  println("\nCassandra...")
  val cassAnalyser = BaseAnalyzer(DataSrcType.dstCassandra, cassandraMap)

//  val s0 = cassAnalyser.createEntity("""CREATE KEYSPACE demodb WITH replication = {'class':'SimpleStrategy', 'replication_factor':3}""")
//  if(s0) println("Keyspace demodb created successfully")
//  val s1 = cassAnalyser.createEntity("""CREATE TABLE demodb.person (id uuid PRIMARY KEY, firstname text, lastname text, age int)""")
//  if(s1) println("Table person in keyspace demodb created successfully")
  //val cassEntities = cassAnalyser.getEntities()

  //val cassParams = List("E9C0DBDD-DD8B-4A6C-AF91-DF38CF365D69", "Nikola", "Tonkev", 36)
  val cassParams = List(23, "Nikola", "Tonkev", 36)
  val cassInsertStr: String = """INSERT INTO demodb.person(id, firstname, lastname, age) VALUES(?, ?, ?, ?)"""
  val result = cassAnalyser.asInstanceOf[CassandraAnalyzer].execQuery(cassParams.asInstanceOf[List[Object]], cassInsertStr)
  println(result)
}


/*

CREATE KEYSPACE demodb WITH replication = {'class':'SimpleStrategy', 'replication_factor':3};

CREATE TABLE demodb.person (id int PRIMARY KEY, firstname text, lastname text, age int)
CREATE TABLE demodb.Addresses (id int PRIMARY KEY, personid uuid, address text, postalcode text)

SELECT column_name FROM system.schema_columnfamilies WHERE keyspace_name = 'demodb' AND columnfamily_name = Y
select columnfamily_name from system.schema_columnfamilies where keyspace_name='demodb'

select 'Table' AS Type, columnfamily_name from system.schema_columnfamilies where keyspace_name='demodb'

INSERT INTO demodb.person(id, firstname, lastname, age) VALUES(?, ?, ?, ?)

*/