package Models

/**
 * Created by nikolatonkev on 14-11-15.
 */
case class DataEntityItem (
  ColumnName: String,
  OrderIndex: Int,
  DefaultValue: String,
  IsNullable: Boolean,
  ColumnType: String,
  ColumnLenght: Int,
  ColumnPrecision: Int,
  ColumnScale: Int)
