package Models

/**
 * Created by nikolatonkev on 14-11-15.
 */
case class DataEntityItem (
  ColumnName: String,
  OrderIndex: Integer,
  DefaultValue: String,
  IsNullable: Boolean,
  ColumnType: String,
  ColumnLenght: Integer,
  ColumnPrecision: Integer,
  ColumnScale: Integer)
