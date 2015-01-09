package Models

import Common.Operators._

/**
 * Created by nikolatonkev on 15-01-08.
 */
case class JoinCondition(JoinIndex: Int, ConditionIndex: Int, LeftOperand: String, RightOperand: String, Operator: Operators)
