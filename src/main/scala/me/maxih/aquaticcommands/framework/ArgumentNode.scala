package me.maxih.aquaticcommands.framework

import org.bukkit.command.CommandSender

/**
  * Created by Maxi H. on 27.07.2018
  */
class ArgumentNode(override val name: String, val parser: Parser[_], override val executable: Boolean = false) extends CommandNode {
  override val positions: Int = parser.positions

  override def complete(sender: CommandSender, argument: String): Seq[String] = parser.complete(sender, argument)

  override def applicable(sender: CommandSender, argument: String): Boolean = parser.applicable(sender, argument)
}
