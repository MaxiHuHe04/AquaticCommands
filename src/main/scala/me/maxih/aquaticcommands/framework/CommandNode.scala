package me.maxih.aquaticcommands.framework

import org.bukkit.command.CommandSender

import scala.collection.mutable.ListBuffer

/**
  * Created by Maxi H. on 27.07.2018
  */
trait CommandNode {
  val name: String
  val children: ListBuffer[CommandNode] = new ListBuffer()
  val executable: Boolean
  val positions = 1

  def add(child: CommandNode*): this.type = {
    children ++= child
    this
  }

  def applicable(sender: CommandSender, argument: String): Boolean

  def complete(sender: CommandSender, argument: String): Seq[String]
}
