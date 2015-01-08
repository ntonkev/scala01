package FilterParser

import Common.LexemTypes._
import Common.{Lexem, LexemTypes, LexemParser}
import collection.immutable._
import scala.util.matching.Regex

/**
 * Created by nikolatonkev on 14-12-22.
 */
class Parser(expression: String) extends LexemParser{
  val Source: String = expression
  var Error: String = ""
  //var LexemList: Array[Lexem] = new Array[Lexem](0)

  val analyzer = new LexicalAnalyzer()
  val lexems = analyzer.GetLexems(Source)
  Error = analyzer.GetError()
  //if(!Error.equals("")) return false


  def GetError(): String = {
    return Error
  }

  def ValidateExpression(): Boolean ={
//    val analyzer = new LexicalAnalyzer()
//    val lexems = analyzer.GetLexems(source)
//    Error = analyzer.GetError()
//    if(!Error.equals("")) return false

    val interpreter = new ExpressionInterpreter(lexems)
    val result = interpreter.Verify()
    Error = interpreter.GetError()
    if(!Error.equals("")) return false

    return result
  }

  def ExecuteExpression(conditions: Array[Boolean]): Boolean ={
//    val analyzer = new LexicalAnalyzer()
//    val lexems = analyzer.GetLexems(source)
//    Error = analyzer.GetError()
//    if(!Error.equals("")) return false

    val interpreter = new ExpressionInterpreter(lexems)
    val result = interpreter.Execute(conditions)
    Error = interpreter.GetError()
    if(!Error.equals("")) return false

    return result
  }

}
