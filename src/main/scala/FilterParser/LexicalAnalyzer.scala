package FilterParser

import Common.LexemTypes
import Common.LexemTypes.LexemTypes
import Common.{Lexem, LexemTypes, LexemParser}

import scala.util.matching.Regex

/**
 * Created by nikolatonkev on 14-12-23.
 */
class LexicalAnalyzer extends LexemParser {
  var run: Int = 0
  var Source: String = ""
  var ValueInteger: Int = 0
  var Error: String = ""
  var ASCIIProc: Array[ASCIITableProc] = new Array[ASCIITableProc](0)
  var Identifiers: Array[Boolean] = new Array[Boolean](0)

  val NullProc: ASCIITableProc = () => LexemTypes.ltNull

  val DotProc: ASCIITableProc = () => {
    run += 1
    LexemTypes.ltDot
  }

  val SpaceProc: ASCIITableProc = () => {
    while(1.asInstanceOf[Char] to 32.asInstanceOf[Char] contains(Source(run))) run += 1
    LexemTypes.ltSpace
  }

  val NumberProc: ASCIITableProc = () => {
    val StartIdentifier: Int = run

    while(Source(run).isDigit) run += 1
    try
    {
      val EndIdentifier: Int = run
      val Identifier = Source.substring(StartIdentifier, EndIdentifier)
      ValueInteger = Identifier.toInt
    }
    catch{
      case e: Exception => ValueInteger = -1
    }
    LexemTypes.ltValueInt
  }

  val IdentProc: ASCIITableProc = () => {
    val StartIdentifier: Int = run
    while(Identifiers(Source(run).toInt)) run += 1
    val EndIdentifier: Int = run
    val Identifier = Source.substring(StartIdentifier, EndIdentifier).trim.toUpperCase

    Identifier match {
      case "AND" => LexemTypes.ltKeyWordAnd
      case "OR" => LexemTypes.ltKeyWordOr
      case "NOT" => LexemTypes.ltKeyWordNot
      case _ => LexemTypes.ltUnknown
    }
  }

  val RoundCloseProc: ASCIITableProc = () => {
    run += 1
    LexemTypes.ltRoundClose
  }

  val RoundOpenProc: ASCIITableProc = () => {
    run += 1
    LexemTypes.ltRoundOpen
  }

  val UnknownProc: ASCIITableProc = () => LexemTypes.ltUnknown

  def UndoLexem: Unit = {
    run -= 1
  }

  def SetIdentifiers(idntfrs: Array[Boolean]): Array[Boolean] = {
    if (idntfrs.length < 256) {
      val n = idntfrs.length;
      val pattern = new Regex("[0-9]|[a-zA-Z0-9]")
      //[0-9][a-zA-Z0-9]
      val result = pattern findFirstIn n.asInstanceOf[Char].toString() match {
        case None => false
        case _ => true
      }

      val temp = idntfrs :+ result
      return SetIdentifiers(temp)
    }
    else {
      return idntfrs
    }
  }

  def SetASCIIProc(procs: Array[ASCIITableProc]): Array[ASCIITableProc] = {
    if (procs.length < 256) {
      val n = procs.length;
      val result = n.asInstanceOf[Char] match {
        case '.' => DotProc
        case ')' => RoundCloseProc
        case '(' => RoundOpenProc
        case a if(1.asInstanceOf[Char] to 32.asInstanceOf[Char] contains(a)) => SpaceProc
        case b if('0' to '9' contains(b)) => NumberProc
        case c if(('a' to 'z' contains(c)) || ('A' to 'Z' contains(c))) => IdentProc
        case _ => UnknownProc
      }

      val temp: Array[ASCIITableProc] = procs :+ result.asInstanceOf[ASCIITableProc]
      return SetASCIIProc(temp)
    }
    else {
      return procs
    }

  }

  def MakeMethodTables(): Unit = {
    Identifiers = SetIdentifiers(Identifiers)
    ASCIIProc = SetASCIIProc(ASCIIProc)
  }

  def GetLexems(source: String): Array[Lexem] = {
    Source = source
    var index: Int = 0
    var lexemType: LexemTypes = LexemTypes.ltUnknown
    var LexemList: Array[Lexem] = new Array[Lexem](0)
    if(source.trim.isEmpty) {
      Error = "Expression is empty"
      return null
    }

    MakeMethodTables()
    do {
      index = Source(run).asInstanceOf[Char].toInt
      val delegate = ASCIIProc(index)
      lexemType = delegate()

      Error = lexemType match {
        case LexemTypes.ltUnknown => "Unknow lexem type"
        case LexemTypes.ltNull => "Empty lexem"
        case _ => ""
      }

      if(!Error.equals("")) {
        return null
      }

      if(!lexemType.equals(LexemTypes.ltSpace)){
        val condIndex: Int = if (lexemType.equals(LexemTypes.ltValueInt)) ValueInteger else -1
        if(lexemType.equals(LexemTypes.ltValueInt) && ValueInteger.equals(-1)) {
          Error =  "Error converting to Integer"
          return null
        }
        val lexem = new Lexem(lexemType, condIndex)
        LexemList = LexemList :+ lexem
      }

    } while(!lexemType.equals(LexemTypes.ltDot))

    return LexemList
  }

  def GetError(): String = {
    return Error
  }
}
