package Scaners

/**
 * Created by nikolatonkev on 14-12-31.
 */

trait BaseColumn

abstract class ColumnTree[K <: Any, V <: Any] extends BaseColumn {
  def get(key: K): V = { null.asInstanceOf[V] }
  def put(key: K, value: V): Unit
}
