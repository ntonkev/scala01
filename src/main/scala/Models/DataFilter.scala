package Models

import Common.OperandType.OperandType

/**
 * Created by nikolatonkev on 14-12-22.
 */
case class DataFilter[L, R] (
  LeftOperand: L,
  LeftOperandType: OperandType,
  RightOperand: R,
  RightOperandType: OperandType
)
