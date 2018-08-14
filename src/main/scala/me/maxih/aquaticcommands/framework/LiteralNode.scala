package me.maxih.aquaticcommands.framework

import me.maxih.aquaticcommands.framework.util.Comparer
import org.bukkit.command.CommandSender

/**
  * Created by Maxi H. on 27.07.2018
  */
class LiteralNode(override val name: String, override val executable: Boolean = false) extends CommandNode {
  override def applicable(sender: CommandSender, argument: String): Boolean =
    Comparer.partialMatch(name, argument)

  override def complete(sender: CommandSender, argument: String): Seq[String] = Seq(name)
}
