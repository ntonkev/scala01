package RedBlackTree

import java.util.Comparator

/**
 * Created by nikolatonkev on 14-11-13.
 */
class RBNode[K, V](_key: K, _value: V, _color: Boolean, _weight: Int) {
  var key: K = _key
  var value: V = _value
  var left: RBNode[K, V] = null
  var right: RBNode[K, V] = null
  var color: Boolean = _color
  var weight: Int = _weight

  //def value(_value: V) = { value = Some(_value)}
}
