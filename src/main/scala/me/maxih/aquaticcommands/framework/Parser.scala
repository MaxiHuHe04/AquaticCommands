package me.maxih.aquaticcommands.framework

import org.bukkit.command.CommandSender

/**
  * Created by Maxi H. on 27.07.2018
  */
trait Parser[T] {
  val positions = 1

  def applicable(sender: CommandSender, argument: String): Boolean = true

  @throws[ParseException]
  def parse(sender: CommandSender, argument: String): T

  def complete(sender: CommandSender, argument: String): Seq[String] = Seq()

}
