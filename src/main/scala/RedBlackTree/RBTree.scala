package RedBlackTree

import java.util.Comparator

import Scaners.ColumnTree

//trait Comparator[K]  extends Comparable[K] {
//  def compare(that: K): Int
//  def compareTo(that: K): Int
//}

/**
 * Created by nikolatonkev on 14-11-13.
 */



//class TestComparable[K, V] extends java.util.Comparator[K] {
//
//   def compare(a: K, b: K) = a match {
//    case _: Int => a.asInstanceOf[Int].compareTo(b.asInstanceOf[Int])
//    case _: Long => a.asInstanceOf[Long].compareTo(b.asInstanceOf[Long])
//    case _: Short => a.asInstanceOf[Short].compareTo(b.asInstanceOf[Short])
//    case _: Double => a.asInstanceOf[Double].compareTo(b.asInstanceOf[Double])
//  }
//
//
//  private def get(node: RBNode[K, V], key: K): V = {
//    while(node != null){
//      val cmp = compare(key, node.key)
//
//      if(cmp < 0){
//        node == node.left
//      }
//      else if(cmp > 0){
//        node == node.right
//      }
//      else{
//        return node.value
//      }
//    }
//
//    return null.asInstanceOf[V]
//  }
//
//}


class RBTree[K, V] extends ColumnTree[K, V] with Comparator[K] {

  def compare(a: K, b: K) = a match {
    case _: Int => a.asInstanceOf[Int].compareTo(b.asInstanceOf[Int])
    case _: Long => a.asInstanceOf[Long].compareTo(b.asInstanceOf[Long])
    case _: Short => a.asInstanceOf[Short].compareTo(b.asInstanceOf[Short])
    case _: Double => a.asInstanceOf[Double].compareTo(b.asInstanceOf[Double])
  }

  private final val RED: Boolean = true
  private final val BLACK: Boolean = false

  var root: RBNode[K, V] = null

//  override def compare(o1: K, o2: K) = {
//    if (o1 == null || o2 == null) {
//      throw new NullPointerException("Comparing null values is not supported!");
//    }
//    o1.compareTo(o2);
//  }

  /*************************** Search **************************/

  override def get(key: K): V = {
    return get(root, key)
  }

  private def get(rbnode: RBNode[K, V], key: K): V = {
     var node = rbnode
     while(node != null){
        val cmp = compare(key, node.key)

        if(cmp < 0){
          node = node.left
        }
        else if(cmp > 0){
          node = node.right
        }
        else{
          return node.value
        }
     }

    return null.asInstanceOf[V]
  }

  /*************************************************************/



  /*************************** Insert **************************/

  override def put(key: K, value: V): Unit = {
    root = put(root, key, value)
    root.color = BLACK
  }

  private def put(nodeEx: RBNode[K, V], key: K, value: V): RBNode[K, V] = {
    var node: RBNode[K,V] = nodeEx
    if (node == null) return new RBNode(key, value, RED, 1)
    val cmp: Int  = compare(key, node.key)

    if(cmp < 0){
      node.left  = put(node.left,  key, value)
    }
    else if(cmp > 0){
      node.right = put(node.right, key, value)
    }
    else{
      node.value = value.asInstanceOf[V]
    }


    // fix-up any right-leaning links
    if (isRed(node.right) && !isRed(node.left)){
      node = rotateLeft(node)
    }

    if (isRed(node.left)  &&  isRed(node.left.left)){
      node = rotateRight(node)
    }

    if (isRed(node.left)  &&  isRed(node.right)){
      flipColors(node)
    }

    node.weight = size(node.left) + size(node.right) + 1;

    return node;
  }

  /*************************************************************/



  /************************** Helpers **************************/

  // number of node in subtree rooted at x; 0 if x is null
  private def size(node: RBNode[K, V]): Int = {
    if (node == null) return 0
    return node.weight;
  }

  // return number of key-value pairs in this symbol table
  def size(): Int = {
    return size(root);
  }

  // is this symbol table empty?
  def isEmpty(): Boolean = {
    return root == null;
  }

  def contains(key: K) {
    return (get(key) != null);
  }

  //Checking node is it read to determine direction
  private def isRed(node: RBNode[K, V]): Boolean = {
    if (node == null) return false
    return (node.color == RED);
  }

  // make a left-leaning link lean to the right
  private def rotateRight(node: RBNode[K, V]): RBNode[K, V] = {
    // assert (h != null) && isRed(h.left);
    val nodeTemp: RBNode[K, V] = node.left;
    node.left = nodeTemp.right;
    nodeTemp.right = node;
    nodeTemp.color = nodeTemp.right.color;
    nodeTemp.right.color = RED;
    nodeTemp.weight = node.weight;
    node.weight = size(node.left) + size(node.right) + 1;
    return nodeTemp;
  }

  // make a right-leaning link lean to the left
  private def rotateLeft(node: RBNode[K, V]): RBNode[K, V] = {
    // assert (h != null) && isRed(h.right);
    val tempNode: RBNode[K, V] = node.right;
    node.right = tempNode.left;
    tempNode.left = node;
    tempNode.color = tempNode.left.color;
    tempNode.left.color = RED;
    tempNode.weight = node.weight;
    node.weight = size(node.left) + size(node.right) + 1;
    return tempNode;
  }

  // flip the colors of a node and its two children
  private def flipColors(node: RBNode[K, V]) {
    // h must have opposite color of its two children
    // assert (h != null) && (h.left != null) && (h.right != null);
    // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
    //     || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
    node.color = !node.color;
    node.left.color = !node.left.color;
    node.right.color = !node.right.color;
  }



  /*************************************************************/
}
