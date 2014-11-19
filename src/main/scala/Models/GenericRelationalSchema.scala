package Models

case class GenericRelationalSchema (
  dataSource: String,
  tables: List[GenericRelationalTable]
)

case class GenericRelationalTable (
  schema: String,
  name: String,
  columns: List[GenericRelationalColumn]
)

case class GenericRelationalColumn (
  name: String,
  dataType: GenericDataType,
  nullable: Boolean
)

class GenericDataType extends Enumeration {
  type GenericDataType = Value
  val Numeric, Monetary, Character, DateTime, Boolean, Enum, Geometric, Text, UUID = Value
}