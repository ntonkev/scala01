package FilterParser

import Common.LexemTypes
import Common.LexemTypes.LexemTypes
import Common.{LexemTypes, Lexem}

/**
 * Created by nikolatonkev on 14-12-23.
 */
class ExpressionInterpreter(lexems: Array[Lexem]) {

  var run: Int = 0
  var Error: String = ""
  var LexemList: Array[Lexem] = lexems //new Array[Lexem](0)
  var Conditions: Array[Boolean] = null

  def GetLexem(): Lexem = {
    val lexem = if(LexemList.length > run){
      LexemList(run)
    }
    else
    {
      return null
    }

    if(lexem != null) {
      run += 1
    }
    else {
      Error = "Unexpected end of the script !!!"
    }

    return lexem
  }

  def UndoLexem(): Unit = {
    run -= 1
  }

  def FindConditionByIndex(index: Int): Boolean = {
    if(Conditions == None) return false

    try
    {
      return Conditions(index)
    }
    catch{
      case e: Exception => {
        Error = e.getMessage
        return false
      }
    }
  }

  def GetError(): String = {
    return Error
  }

  def GenerateConditions(): Array[Boolean] = {
    var array = new Array[Boolean](0)
    for(n <- 0 to (LexemList.length - 1)){
      val lexem = LexemList(n)
      if(lexem.LexemType.equals(LexemTypes.ltValueInt)){
        array = array :+ true
      }
    }
    return array
  }

  def Verify(): Boolean = {
    val conditions = GenerateConditions()
    return Execute(conditions)
  }

  def Execute(conditions: Array[Boolean]): Boolean = {
    Conditions = conditions
    val success = DoExpression()

    if(!Error.equals("")) return false
    val lexem = GetLexem()
    if(lexem == null) return false
    if(!lexem.LexemType.equals(LexemTypes.ltDot)) {
      Error = "Unexpected end of the expression, missing '.'"
      return false
    }
    return success
  }

  //<expression> ::= <term> | <term> or <expression>
  def DoExpression(): Boolean = {
    var tempSuccess: Boolean = false
    val success = DoTerm()

    if(!Error.equals("")) return false
    val lexem = GetLexem()
    if(!Error.equals("")) return false
    if(lexem.LexemType.equals(LexemTypes.ltKeyWordOr)){
      tempSuccess = DoExpression()
      return (success | tempSuccess)
    }
    else{
      UndoLexem()
      return success
    }
  }


  //<term> ::= <multiplier> | <multiplier> and <term>
  def DoTerm(): Boolean = {
    val success = DoMultiplier()
    var tempSuccess: Boolean = false

    if(!Error.equals("")) return false
    val lexem = GetLexem()
    if(!Error.equals("")) return false
    if(lexem.LexemType.equals(LexemTypes.ltKeyWordAnd)){
      tempSuccess = DoTerm()
      return (success & tempSuccess)
    }
    else{
      UndoLexem()
      return success
    }
  }


  //<multiplier> ::= <variable> | (<expression>) | not <multiplier>
  def DoMultiplier(): Boolean = {
    val lexem = GetLexem()
    if(!Error.equals("")) return false

    return lexem.LexemType match {
      case LexemTypes.ltValueInt => FindConditionByIndex(lexem.ConditionIndex)
      case LexemTypes.ltRoundOpen => {
        val tempSuccess = DoExpression()
        if(!Error.equals("")) return false
        val lexem = GetLexem()
        if(!Error.equals("")) return false
        if(!lexem.LexemType.equals(LexemTypes.ltRoundClose)) {
          Error = "Misssing ')' to closing the expression"
          return false
        }
        tempSuccess
      }
      case LexemTypes.ltKeyWordNot => !DoMultiplier()
      case _ => return false
    }
  }
}
