package me.maxih.aquaticcommands.framework.util

import me.maxih.aquaticcommands.framework.CommandException
import org.bukkit.command.{BlockCommandSender, CommandSender}
import org.bukkit.entity.Player

/**
  * Created by Maxi H. on 11.08.2018
  */
object Implicits {

  implicit class RichCommandSender(self: CommandSender) {
    def player: Option[Player] = self match {
      case self: Player => Some(self)
      case _ => None
    }

    def playerOrNull: Player = player.orNull

    def playerOrError: Player = player.getOrElse(throw CommandException("A player is required for this command."))

    def blockSender: Option[BlockCommandSender] = self match {
      case self: BlockCommandSender => Some(self)
      case _ => None
    }
  }

  implicit class RichOption[A](self: Option[A]) {
    def mapOrElse[B](map: A => B, default: => B): B = self.map(map).getOrElse(default)
  }

}
