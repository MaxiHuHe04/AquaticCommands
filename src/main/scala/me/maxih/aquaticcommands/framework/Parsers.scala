package me.maxih.aquaticcommands.framework

import me.maxih.aquaticcommands.framework.util.{Comparer, Vec3}
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.util.Try

/**
  * Created by Maxi H. on 27.07.2018
  */
object Parsers {

  object Vec3Parser extends Parser[Vec3] {
    override val positions = 3

    override def applicable(sender: CommandSender, argument: String): Boolean = {
      if (argument.isEmpty) return true

      for (coordinate <- argument.split(' ')) if (coordinate.startsWith("~")) {
        if (coordinate.length > 1 && Try(coordinate.substring(1).toDouble).isFailure) return false
      } else {
        if (coordinate.length > 0 && Try(coordinate.toDouble).isFailure) return false
      }

      true
    }

    override def parse(sender: CommandSender, argument: String): Vec3 = {
      val coordinates = argument.split(' ')
      if (coordinates.length != 3) throw new ParseException("Incorrect argument for command", argument)

      val coords = new ListBuffer[Vec3.Coordinate]
      for (coordinate <- coordinates) {
        if (coordinate.startsWith("~")) {
          if (coordinate.length == 1) coords += Vec3.Coordinate(0, relative = true)
          else coords += Vec3.Coordinate(Try(coordinate.substring(1).toDouble).getOrElse(throw new ParseException("Expected double", argument)), relative = true)
        } else {
          coords += Vec3.Coordinate(Try(coordinate.toDouble).getOrElse(throw new ParseException("Expected double", argument)))
        }
      }

      Vec3(coords.head, coords(1), coords(2))
    }

    override def complete(sender: CommandSender, argument: String): Seq[String] = {
      if (argument.matches("~?")) Seq("~ ~ ~", "~ ~", "~")
      else if (argument.matches("((-?\\d+(.\\d+)?)|(~(-?\\d+(.\\d+)?)?)) ~?")) Seq("~ ~", "~")
      else if (argument.matches("((-?\\d+(.\\d+)?)|(~(-?\\d+(.\\d+)?)?)) ((-?\\d+(.\\d+)?)|(~(-?\\d+(.\\d+)?)?)) ~?")) Seq("~")
      else Seq()
    }

  }

  object PlayerParser extends Parser[Player] {

    override def parse(sender: CommandSender, argument: String): Player = {
      val player = Bukkit.getPlayer(argument)
      if (player == null) throw new ParseException("Player not found", argument)
      player
    }

    override def complete(sender: CommandSender, argument: String): Seq[String] =
      Bukkit.getOnlinePlayers.asScala.map(_.getName).filter(Comparer.partialMatch(_, argument, matchCase = false)).toSeq

  }

  class IntParser(negativeAllowed: Boolean = false) extends Parser[Int] {
    override def applicable(sender: CommandSender, argument: String): Boolean = argument.forall(char => char.isDigit || (negativeAllowed && char == '-'))

    override def parse(sender: CommandSender, argument: String): Int = Try(argument.toInt).getOrElse(throw new ParseException("Expected int", argument))
  }

  class LongParser(negativeAllowed: Boolean = false) extends Parser[Long] {
    override def applicable(sender: CommandSender, argument: String): Boolean = argument.forall(char => char.isDigit || (negativeAllowed && char == '-'))

    override def parse(sender: CommandSender, argument: String): Long = Try(argument.toLong).getOrElse(throw new ParseException("Expected long", argument))
  }

  class DoubleParser(negativeAllowed: Boolean = false) extends Parser[Double] {
    override def applicable(sender: CommandSender, argument: String): Boolean = argument.isEmpty || (Try(argument.toDouble).isSuccess && (negativeAllowed || !argument.contains('-')))

    override def parse(sender: CommandSender, argument: String): Double = Try(argument.toDouble).getOrElse(throw new ParseException("Expected double", argument))
  }

  class FloatParser(negativeAllowed: Boolean = false) extends Parser[Float] {
    override def applicable(sender: CommandSender, argument: String): Boolean = argument.isEmpty || (Try(argument.toFloat).isSuccess && (negativeAllowed || !argument.contains('-')))

    override def parse(sender: CommandSender, argument: String): Float = Try(argument.toFloat).getOrElse(throw new ParseException("Expected float", argument))
  }

}
